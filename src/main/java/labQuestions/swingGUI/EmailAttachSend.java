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
    JTextArea attachmentArea;
    JButton attachBtn, sendBtn, viewBtn;
    File attachmentFile = null;
    JTable table;

    public EmailAttachSend() {
        setTitle("Swing Email Sender");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));


        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setPreferredSize(new Dimension(350, 300));

        JLabel toLabel = new JLabel("To:");
        toField = new JTextField();
        toField.setPreferredSize(new Dimension(300, 30));
        toField.setMaximumSize(new Dimension(300, 30));

        JLabel subjectLabel = new JLabel("Subject:");
        subjectField = new JTextField();
        subjectField.setPreferredSize(new Dimension(300, 30));
        subjectField.setMaximumSize(new Dimension(300, 30));

        JLabel messageLabel = new JLabel("Message:");
        messageArea = new JTextArea(5, 25);
        JScrollPane messageScroll = new JScrollPane(messageArea);
        messageScroll.setPreferredSize(new Dimension(300, 100));

        JLabel attachmentLabel = new JLabel("Attached File:");
        attachmentArea = new JTextArea(2, 25);
        attachmentArea.setEditable(false);
        attachmentArea.setLineWrap(true);
        attachmentArea.setWrapStyleWord(true);
        JScrollPane attachmentScroll = new JScrollPane(attachmentArea);
        attachmentScroll.setPreferredSize(new Dimension(300, 45));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        attachBtn = new JButton("Attach File");
        sendBtn = new JButton("Send Email");
        viewBtn = new JButton("View Sent Emails");

        buttonPanel.add(attachBtn);
        buttonPanel.add(sendBtn);
        buttonPanel.add(viewBtn);

        formPanel.add(toLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(toField);
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(subjectLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(subjectField);
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(messageLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(messageScroll);
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(attachmentLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(attachmentScroll);
        formPanel.add(Box.createVerticalStrut(0x10));

        formPanel.add(buttonPanel);

        topWrapper.add(formPanel);

        add(topWrapper, BorderLayout.NORTH);

        table = new JTable();
        JScrollPane tableScroll = new JScrollPane(table);
        add(tableScroll, BorderLayout.CENTER);

        attachBtn.addActionListener(e -> chooseFile());
        sendBtn.addActionListener(e -> sendEmail());
        viewBtn.addActionListener(e -> loadEmails());

        setVisible(true);
    }

    void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            attachmentFile = chooser.getSelectedFile();
            attachmentArea.setText(attachmentFile.getAbsolutePath());
            JOptionPane.showMessageDialog(this, "File Attached");
        }
    }

    void sendEmail() {
        String to = toField.getText();
        String subject = subjectField.getText();
        String message = messageArea.getText();

        final String from = "aayurai268@gmail.com";
        final String password = "pcpduatjvxtfswvd";

        Properties props = new Properties();
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
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
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
            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/emaildb",
                    "postgres",
                    "Aa2023@#"
            );

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO sent_emails(recipient, subject, message, attachment) VALUES (?, ?, ?, ?)"
            );

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
            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/emaildb",
                    "postgres",
                    "Aa2023@#"
            );

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM sent_emails");

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID", "Recipient", "Subject", "Message", "Attachment", "Time"}, 0
            );

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