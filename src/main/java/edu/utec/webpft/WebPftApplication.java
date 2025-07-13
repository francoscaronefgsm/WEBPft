package edu.utec.webpft;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.utec.webpft.entidades.*;
import edu.utec.webpft.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@SpringBootApplication
@RequiredArgsConstructor
public class WebPftApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final DepartmentRepository departmentRepository;

    private final LocalityRepository localityRepository;

    private final ItrRepository itrRepository;

    private final ClaimRepository claimRepository;
    private final EventRepository eventRepository;
    private final AttendanceRepository attendanceRepository;
    private final JustificationRepository justificationRepository;
    private final ModalidadEventoRepository modalidadEventoRepository;
    private final EstadoEventoRepository estadoEventoRepository;
    private final TipoEventoRepository tipoEventoRepository;
    private final EstadoJustificacionRepository estadoJustificacionRepository;
    private final TipoReclamoRepository tipoReclamoRepository;
    private final EstadoReclamoRepository estadoReclamoRepository;
    private final TipoConstanciaRepository tipoConstanciaRepository;
    private final EstadoConstanciaRepository estadoConstanciaRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebPftApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Rol adminRole = Rol.builder().nombre("ROLE_ADMIN").build();
        Rol studentRole = Rol.builder().nombre("ROLE_STUDENT").build();
        Rol teacherRole = Rol.builder().nombre("ROLE_TEACHER").build();

        if(roleRepository.findByNombre("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(adminRole);
        }
        if(roleRepository.findByNombre("ROLE_TEACHER").isEmpty()) {
            roleRepository.save(teacherRole);
        }
        if(roleRepository.findByNombre("ROLE_STUDENT").isEmpty()) {
            roleRepository.save(studentRole);
        }

        try {
            // Leer el archivo JSON generado
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, String>> localidades = mapper.readValue(
                    new File("resultado_ordenado.json"),
                    new TypeReference<List<Map<String, String>>>() {}
            );

            // Map para agrupar localidades por departamento
            Map<String, List<String>> departamentos = new LinkedHashMap<>();

            for (Map<String, String> localidad : localidades) {
                String departamento = localidad.get("Departamento");
                String localidadNombre = localidad.get("Localidad");

                // Agrupar localidades por departamento
                departamentos.computeIfAbsent(departamento, k -> new ArrayList<>()).add(localidadNombre);
            }

            // Crear departamentos y localidades en base a los datos del JSON
            List<Departamento> departments = new ArrayList<>();
            List<Localidad> localities = new ArrayList<>();

            departamentos.forEach((departamento, localidadesList) -> {
                Departamento department = Departamento.builder().nombre(departamento).build();
                departments.add(department);

                localidadesList.forEach(localidad -> {
                    Localidad locality = Localidad.builder()
                            .nombre(localidad)
                            .departamento(department)
                            .build();
                    localities.add(locality);
                });
            });

            // Guardar los datos en la base de datos
            departmentRepository.saveAll(departments);
            localityRepository.saveAll(localities);

            System.out.println("Datos cargados exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
//
        try {
            // Leer el archivo JSON desde la carpeta resources
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("departamentos_localidades.json");

            if (inputStream == null) {
                throw new FileNotFoundException("El archivo departamentos_localidades.json no se encuentra en resources.");
            }

            // Leer los datos del JSON
            List<Map<String, String>> localidades = mapper.readValue(
                    inputStream,
                    new TypeReference<List<Map<String, String>>>() {}
            );

            // Map para agrupar localidades por departamento
            Map<String, List<String>> departamentos = new LinkedHashMap<>();

            for (Map<String, String> localidad : localidades) {
                String departamento = localidad.get("Departamento");
                String localidadNombre = localidad.get("Localidad");

                // Agrupar localidades por departamento
                departamentos.computeIfAbsent(departamento, k -> new ArrayList<>()).add(localidadNombre);
            }

            // Crear departamentos y localidades en base a los datos del JSON
            List<Departamento> departments = new ArrayList<>();
            List<Localidad> localities = new ArrayList<>();

            departamentos.forEach((departamento, localidadesList) -> {
                Departamento department = Departamento.builder().nombre(departamento).build();
                departments.add(department);

                localidadesList.forEach(localidad -> {
                    Localidad locality = Localidad.builder()
                            .nombre(localidad)
                            .departamento(department)
                            .build();
                    localities.add(locality);
                });
            });

            // Guardar los datos en la base de datos
            departmentRepository.saveAll(departments);
            localityRepository.saveAll(localities);

            System.out.println("Datos cargados exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }

//

        Itr itrLaPaz = Itr.builder().nombre("ITR Sur Oeste - La Paz").localidad(localityRepository.getById(271l)).build();
        Itr itrFrayBentos = Itr.builder().nombre("ITR Sur Oeste - Fray Bentos").localidad(localityRepository.getById(907l)).build();
        Itr itrMercedes = Itr.builder().nombre("ITR Sur Oeste - Mercedes").localidad(localityRepository.getById(1289l)).build();
        Itr itrNuevaHelvecia = Itr.builder().nombre("ITR Sur Oeste - Nueva Helvecia").localidad(localityRepository.getById(285l)).build();
        Itr itrPaysandu = Itr.builder().nombre("ITR Sur Oeste - Paysandú").localidad(localityRepository.getById(815l)).build();

        Itr itrMinas = Itr.builder().nombre("ITR Este - Minas").localidad(localityRepository.getById(647l)).build();
        Itr itrMaldonado = Itr.builder().nombre("ITR Este - Maldonado").localidad(localityRepository.getById(769l)).build();

        Itr itrSanJose = Itr.builder().nombre("ITR Centro-Sur - San José").localidad(localityRepository.getById(1188l)).build();
        Itr itrDurazno = Itr.builder().nombre("ITR Centro-Sur - Durazno").localidad(localityRepository.getById(403l)).build();

        Itr itrMelo = Itr.builder().nombre("ITR Norte - Melo").localidad(localityRepository.getById(230l)).build();
        Itr itrRivera = Itr.builder().nombre("ITR Norte - Rivera").localidad(localityRepository.getById(954l)).build();

        List<Itr> itrs = Arrays.asList(itrLaPaz, itrFrayBentos, itrMercedes, itrNuevaHelvecia, itrPaysandu, itrMinas, itrMelo, itrRivera, itrMaldonado, itrSanJose, itrDurazno);
        itrRepository.saveAll(itrs);
//
        Usuario admin = Usuario.builder()
                .nombreUsuario("admin")
                .contrasena(passwordEncoder.encode("admin"))
                .primerNombre("admin")
                .segundoNombre("admin")
                .primerApellido("admin")
                .segundoApellido("admin")
                .documento(12345678)
                .fechaNacimiento(LocalDate.of(2001, 3, 30))
                .correoPersonal("admin@gmail.com")
                .telefono(123456789)
                .departamento(departmentRepository.getById(12l))
                .localidad(localityRepository.getById(907l))
                .correoInstitucional("admin@utec.edu.uy")
                .itr(itrFrayBentos)
                .roles(Arrays.asList(adminRole))
                .activo(true)
                .build();

        userRepository.save(admin);

        Estudiante student = Estudiante.builder()
                .nombreUsuario("student")
                .contrasena(passwordEncoder.encode("student"))
                .primerNombre("Jorge")
                .segundoNombre("Luis")
                .primerApellido("Costa")
                .segundoApellido("Perez")
                .documento(41235517)
                .fechaNacimiento(LocalDate.of(2000, 2, 10))
                .correoPersonal("jorge.costa@gmail.com")
                .telefono(994321221)
                .departamento(departmentRepository.getById(12l))
                .localidad(localityRepository.getById(907l))
                .itr(itrFrayBentos)
                .roles(Arrays.asList(studentRole))
                .activo(true)
                .correoInstitucional("jorge.costa@estudiantes.utec.edu.uy")
                .genero("Masculino")
                .generacion(2023)
                .build();
        userRepository.save(student);

        Tutor teacher = Tutor.builder()
                .nombreUsuario("teacher")
                .contrasena(passwordEncoder.encode("teacher"))
                .primerNombre("Maria")
                .segundoNombre("Claudia")
                .primerApellido("Silva")
                .segundoApellido("Lopez")
                .documento(41245514)
                .fechaNacimiento(LocalDate.of(1990, 7, 12))
                .correoPersonal("maria.silva@gmail.com")
                .telefono(994441221)
                .departamento(departmentRepository.getById(12l))
                .localidad(localityRepository.getById(907l))
                .itr(itrFrayBentos)
                .roles(Arrays.asList(teacherRole))
                .activo(true)
                .correoInstitucional("maria.silva@utec.edu.uy")
                .genero("Femenino")
                .rolDocente("Teacher")
                .area("LTI")
                .build();
        userRepository.save(teacher);


        List<Usuario> teachers = new ArrayList<>();
        teachers.add(teacher);


        TipoConstancia tipoConstanciaTransporte = TipoConstancia.builder().descripcion("Constancia de Transporte").build();
        TipoConstancia tipoConstanciaExamen = TipoConstancia.builder().descripcion("Constancia de examen").build();
        TipoConstancia tipoConstanciaPruebaFinal = TipoConstancia.builder().descripcion("Constancia de prueba de final").build();
        TipoConstancia tipoConstanciaJornadaExterna = TipoConstancia.builder().descripcion("Constancia de jornada de externa").build();
        List<TipoConstancia> tiposConstancia = new ArrayList<>();
        tiposConstancia.add(tipoConstanciaTransporte);
        tiposConstancia.add(tipoConstanciaExamen);
        tiposConstancia.add(tipoConstanciaPruebaFinal);
        tiposConstancia.add(tipoConstanciaJornadaExterna);
        tipoConstanciaRepository.saveAll(tiposConstancia);

        EstadoConstancia estadoConstanciaIngresado = EstadoConstancia.builder().descripcion("Ingresado").build();
        EstadoConstancia estadoConstanciaEnProceso = EstadoConstancia.builder().descripcion("En proceso").build();
        EstadoConstancia estadoConstanciaFinalizado = EstadoConstancia.builder().descripcion("Finalizado").build();
        List<EstadoConstancia> estadosConstancia = new ArrayList<>();
        estadosConstancia.add(estadoConstanciaIngresado);
        estadosConstancia.add(estadoConstanciaEnProceso);
        estadosConstancia.add(estadoConstanciaFinalizado);
        estadoConstanciaRepository.saveAll(estadosConstancia);



        ModalidadEvento modalidadEventoPresencial = ModalidadEvento.builder().descripcion("Presencial").build();
        ModalidadEvento modalidadEventoVirtual = ModalidadEvento.builder().descripcion("Virtual").build();
        ModalidadEvento modalidadEventoSemipresencial = ModalidadEvento.builder().descripcion("Semipresencial").build();
        List<ModalidadEvento> modalidades = new ArrayList<>();
        modalidades.add(modalidadEventoPresencial);
        modalidades.add(modalidadEventoVirtual);
        modalidades.add(modalidadEventoSemipresencial);
        modalidadEventoRepository.saveAll(modalidades);


        TipoEvento tipoEventoJornadaPrescencial = TipoEvento.builder().descripcion("Jornada prescencial").build();
        TipoEvento tipoEventoExamen = TipoEvento.builder().descripcion("Examen").build();
        TipoEvento tipoEventoDefensaProyecto = TipoEvento.builder().descripcion("Defensa de proyecto").build();
        TipoEvento tipoEventoPruebaFinal = TipoEvento.builder().descripcion("Prueba final").build();
        TipoEvento tipoEventoVme = TipoEvento.builder().descripcion("Convocatoria VME").build();
        TipoEvento tipoEventoOptativa = TipoEvento.builder().descripcion("Convocatoria optativa").build();
        TipoEvento tipoEventoOtro = TipoEvento.builder().descripcion("Otro").build();
        List<TipoEvento> tiposEvento = new ArrayList<>();
        tiposEvento.add(tipoEventoJornadaPrescencial);
        tiposEvento.add(tipoEventoExamen);
        tiposEvento.add(tipoEventoOptativa);
        tiposEvento.add(tipoEventoVme);
        tiposEvento.add(tipoEventoOtro);
        tiposEvento.add(tipoEventoPruebaFinal);
        tiposEvento.add(tipoEventoDefensaProyecto);
        tipoEventoRepository.saveAll(tiposEvento);

        TipoReclamo tipoReclamoConvocatoria = TipoReclamo.builder().descripcion("Convocatoria VME").build();
        TipoReclamo tipoReclamoActividadApe = TipoReclamo.builder().descripcion("Actividad APE").build();
        TipoReclamo tipoReclamoOptativa = TipoReclamo.builder().descripcion("Optativa").build();
        List<TipoReclamo> tiposReclamo = new ArrayList<>();
        tiposReclamo.add(tipoReclamoConvocatoria);
        tiposReclamo.add(tipoReclamoActividadApe);
        tiposReclamo.add(tipoReclamoOptativa);
        tipoReclamoRepository.saveAll(tiposReclamo);

        EstadoReclamo estadoPendienteReclamo = EstadoReclamo.builder().descripcion("Pendiente").build();
        EstadoReclamo estadoEnProcesoReclamo = EstadoReclamo.builder().descripcion("En Proceso").build();
        EstadoReclamo estadoFinalizadoReclamo = EstadoReclamo.builder().descripcion("Finalizado").build();
        List<EstadoReclamo> estadosReclamo = new ArrayList<>();
        estadosReclamo.add(estadoPendienteReclamo);
        estadosReclamo.add(estadoEnProcesoReclamo);
        estadosReclamo.add(estadoFinalizadoReclamo);
        estadoReclamoRepository.saveAll(estadosReclamo);

        EstadoEvento estadoEventoCorriente = EstadoEvento.builder().descripcion("Corriente").build();
        EstadoEvento estadoEventoFinalizado = EstadoEvento.builder().descripcion("Finalizado").build();
        EstadoEvento estadoEventoFuturo = EstadoEvento.builder().descripcion("Futuro").build();
        List<EstadoEvento> estadosEvento = new ArrayList<>();
        estadosEvento.add(estadoEventoCorriente);
        estadosEvento.add(estadoEventoFinalizado);
        estadosEvento.add(estadoEventoFuturo);
        estadoEventoRepository.saveAll(estadosEvento);

        Evento bantotal = Evento.builder().tipoEvento(tipoEventoVme).itr(itrFrayBentos).modalidad(modalidadEventoVirtual).ubicacion("Sala de Conferencias").fechaFin(LocalDateTime.of(2024, Month.DECEMBER, 17, 12, 0)).fechaInicio(LocalDateTime.of(2024, Month.DECEMBER, 17, 9, 0)).estadoEvento(estadoEventoCorriente).titulo("Bantotal").tutores(teachers).build();
        Evento cigras =	Evento.builder().tipoEvento(tipoEventoOptativa).itr(itrFrayBentos).modalidad(modalidadEventoPresencial).ubicacion("Virtual").fechaFin(LocalDateTime.of(2024, Month.DECEMBER, 25, 12, 0)).fechaInicio(LocalDateTime.of(2024, Month.DECEMBER, 17, 9, 0)).estadoEvento(estadoEventoFuturo).titulo("CIGRAS").tutores(teachers).build();

        List<Evento> events = Arrays.asList(bantotal,cigras);
        eventRepository.saveAll(events);

        Asistencia convocatoria = Asistencia.builder().evento(bantotal).estado("Asistencia").estudiante(student).calificacion(4).build();
        attendanceRepository.save(convocatoria);

        EstadoJustificacion estadoJustificacion = EstadoJustificacion.builder().descripcion("Ingresado").build();
        estadoJustificacionRepository.save(estadoJustificacion);

        Justificacion justificacion = Justificacion.builder().evento(bantotal).estadoJustificacion(estadoJustificacion).estudiante(student).informacion("Ausencia por turno Medico").fecha(LocalDateTime.of(2024, Month.DECEMBER, 17, 9, 0)).build();
        justificationRepository.save(justificacion);
    }
}

