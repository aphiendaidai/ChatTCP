import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Chatpanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextArea txtMessages;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String sender;
    private String receiver;

    public Chatpanel(Socket s, String sender, String receiver) {
        setLayout(null);
        this.socket = s;
        this.sender = sender;
        this.receiver = receiver;

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 414, 189);
        add(scrollPane);

        txtMessages = new JTextArea();
        scrollPane.setViewportView(txtMessages);

        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        btnSend.setBounds(335, 211, 89, 23);
        add(btnSend);

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread readThread = new Thread(new Runnable() {
                public void run() {
                    readMessage();
                }
            });
            readThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
        String message = txtMessages.getText();
        if (!message.isEmpty()) {
            out.println(sender + ": " + message);
            txtMessages.append("\n" + sender + ": " + message);
            txtMessages.setCaretPosition(txtMessages.getDocument().getLength());
            txtMessages.setText("");
        }
    }

    public void readMessage() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                txtMessages.append("\n" + receiver + ": " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JTextArea getTxtMessages() {
        return txtMessages;
    }
}
