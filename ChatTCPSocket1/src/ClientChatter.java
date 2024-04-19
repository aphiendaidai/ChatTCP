import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ClientChatter extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtStaff;
    private JTextField txtSeverIP;
    private JTextField txtSeverPort;
    private Socket serverSocket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientChatter frame = new ClientChatter();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ClientChatter() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblStaff = new JLabel("Staff:");
        lblStaff.setHorizontalAlignment(SwingConstants.RIGHT);
        lblStaff.setBounds(44, 40, 56, 16);
        contentPane.add(lblStaff);

        txtStaff = new JTextField();
        txtStaff.setBounds(112, 37, 130, 22);
        contentPane.add(txtStaff);
        txtStaff.setColumns(10);

        JLabel lblMngIP = new JLabel("Mng IP:");
        lblMngIP.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMngIP.setBounds(48, 82, 52, 16);
        contentPane.add(lblMngIP);

        txtSeverIP = new JTextField();
        txtSeverIP.setBounds(112, 79, 130, 22);
        contentPane.add(txtSeverIP);
        txtSeverIP.setColumns(10);

        JLabel lblPort = new JLabel("Port:");
        lblPort.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPort.setBounds(48, 122, 52, 16);
        contentPane.add(lblPort);

        txtSeverPort = new JTextField();
        txtSeverPort.setBounds(112, 119, 130, 22);
        contentPane.add(txtSeverPort);
        txtSeverPort.setColumns(10);

        JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String staffName = txtStaff.getText();
                String serverIP = txtSeverIP.getText();
                int serverPort = Integer.parseInt(txtSeverPort.getText());
                try {
                    serverSocket = new Socket(serverIP, serverPort);
                    out = new PrintWriter(serverSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

                    out.println("Staff:" + staffName);

                    Chatpanel p = new Chatpanel(serverSocket, staffName, "Manager");
                    p.getTxtMessages().append("Connected to Manager");
                    p.updateUI();
                    p.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnConnect.setBounds(112, 163, 97, 25);
        contentPane.add(btnConnect);
    }
}
