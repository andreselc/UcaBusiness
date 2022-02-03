package logica.usuario;

import android.os.AsyncTask;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EnviarCorreo extends AsyncTask<String, Integer, Void> {
    Properties prop = System.getProperties();
    private String destino, sujeto, contenido;

    public EnviarCorreo(String destino, String sujeto, String contenido){
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.trust","smtp.gmail.com");
        this.destino=destino;
        this.sujeto=sujeto;
        this.contenido=contenido;
        //prop.put("mail.smtp.ssl.trust", "*");
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            crearSesion();
            System.out.println("Email supuestamente enviado");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void crearSesion() throws MessagingException {
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ucabussines@gmail.com", "ucabussines123!");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("ucabusiness@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
        message.setSubject(sujeto);
        message.setText(contenido);

        System.out.println("a punto de enviar mensaje");
        Transport trans = session.getTransport("smtp");
        trans.connect();
        trans.send(message);
        trans.close();

        System.out.println("post send");

    }

}
