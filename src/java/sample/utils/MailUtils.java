package sample.utils;

import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import sample.shopping.Clothes;
import sample.shopping.Cart;
import java.util.Map;

/**
 *
 * @author Hieu Phi Trinh
 */
public class MailUtils {

    private static final String SENDER_EMAIL = "phitestmail753@gmail.com";
    private static final String SENDER_PASSWORD = "ldervqpexxoiigyt";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    public static void sendMail(String customerEmail, String customerName, Cart cart) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.trust", SMTP_HOST);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        Message message = prepareMessage(session, SENDER_EMAIL, customerEmail, customerName, cart);
        try {
            Transport.send(message);
            System.out.println("Message sent successfully");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String customerEmail, String customerName, Cart cart) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(customerEmail));
            message.setSubject("Order Confirmation");

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<h1>Order Confirmation</h1>")
                    .append("<p>Dear ").append(customerName).append(",</p>")
                    .append("<p>Your order has been confirmed and will be shipped to you shortly!</p>")
                    .append("<table border='1' style='width:100%; border-collapse: collapse;'>")
                    .append("<tr><th>Item</th><th>Quantity</th><th>Price</th><th>Total ($)</th></tr>");

            double totalPrice = 0;

            for (Map.Entry<String, Clothes> entry : cart.getCart().entrySet()) {
                Clothes clothes = entry.getValue();
                double itemTotal = clothes.getPrice() * clothes.getQuantity();
                totalPrice += itemTotal;

                htmlContent.append("<tr>")
                        .append("<td>").append(clothes.getClothesName()).append("</td>")
                        .append("<td>").append(clothes.getQuantity()).append("</td>")
                        .append("<td>").append(clothes.getPrice()).append("</td>")
                        .append("<td>").append(itemTotal).append("</td>")
                        .append("</tr>");
            }

            htmlContent.append("</table>")
                    .append("<p><strong>Total Price: ").append(totalPrice).append("$</strong></p>");

            message.setContent(htmlContent.toString(), "text/html; charset=UTF-8");
            return message;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

}
