//package edu.utec.webpft.service.imp;
//
//import edu.utec.webpft.dtos.EmailDto;
//import edu.utec.webpft.service.EmailService;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//@Service
//@RequiredArgsConstructor
//public class EmailServiceImp implements EmailService {
//
//    private final JavaMailSender javaMailSender;
//
//    private final TemplateEngine templateEngine;
//
//    @Override
//    public void sendEmail(EmailDto email) throws MessagingException {
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//            helper.setTo(email.getTo());
//            helper.setSubject(email.getSubject());
//
//            Context context = new Context();
//            context.setVariable("body", email.getBody());
//            String html = templateEngine.process("email", context);
//
//            helper.setText(html, true);
//
//            javaMailSender.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
