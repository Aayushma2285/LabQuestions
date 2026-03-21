package labQuestions.swingGUI;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.Properties;

public class EmailAttachSend extends JFrame {
    JTextField toField, subjectField;
    JTextArea messageArea;
    JButton attachBtn, sendBtn, viewBtn;
    File attachmentFile = null;
    JTable table;

    public EmailAttachSend(){
        setTitle("Swing Email Sender");
        setSize(700, 500);
        setLayout(new FlowLayout());

        add(new JLabel("To:"));
        toField = new JTextField(30);
        add(toField);

        add(new JLabel("Subject:"));
        subjectField = new JTextField(30);
        add(subjectField);

        add(new JLabel("Message:"));
        messageArea = new JTextArea(5, 30);
        add(new JScrollPane(messageArea));

        attachBtn = new JButton("Attach File");
        sendBtn = new JButton("Send Email");
        viewBtn = new JButton("View Sent Emails");

        add(attachBtn);
        add(sendBtn);
        add(viewBtn);

        table = new JTable();
        add(new JScrollPane(table));

        attachBtn.addActionListener(e -> chooseFile());
        sendBtn.addActionListener(e -> sendEmail());
        viewBtn.addActionListener(e -> loadEmails());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void chooseFile(){
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            attachmentFile = chooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "File Attached");
        }
    }

    void sendEmail(){
        String to = toField.getText();
        String subject = subjectField.getText();
        String message = messageArea.getText();

        final String from = "your_email@gmail.com";
        final String password = "your_app_password";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            msg.setSubject(subject);

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(message);
            multipart.addBodyPart(textPart);

            if (attachmentFile != null) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(attachmentFile);
                multipart.addBodyPart(attachPart);
            }

            msg.setContent(multipart);

            Transport.send(msg);

            saveToDatabase(to, subject, message);

            JOptionPane.showMessageDialog(this, "Email Sent");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    void saveToDatabase(String to, String subject, String message) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/emaildb", "root", "password");

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO sent_emails(recipient,subject,message,attachment) VALUES(?,?,?,?)");

            ps.setString(1, to);
            ps.setString(2, subject);
            ps.setString(3, message);
            ps.setString(4, attachmentFile == null ? "None" : attachmentFile.getName());

            ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void loadEmails() {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/emaildb", "root", "password");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM sent_emails");

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID", "Recipient", "Subject", "Message", "Attachment", "Time"}, 0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("recipient"),
                        rs.getString("subject"),
                        rs.getString("message"),
                        rs.getString("attachment"),
                        rs.getTimestamp("sent_time")
                });
            }

            table.setModel(model);
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new EmailAttachSend();
    }
}
