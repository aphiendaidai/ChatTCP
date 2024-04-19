import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ManagerChatter extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtSeverPort;
    private ServerSocket serverSocket;
	private JTabbedPane tabbedPane;
 static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ManagerChatter frame = new ManagerChatter();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ManagerChatter() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(1, 2, 0, 0));

        JLabel txt = new JLabel("Manager Port :");
        txt.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(txt);

        txtSeverPort = new JTextField();
        txtSeverPort.setText("12340");
        panel.add(txtSeverPort);
        txtSeverPort.setColumns(10);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        this.setSize(600, 300);
        int serverPort = Integer.parseInt(txtSeverPort.getText());
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                if (clientSocket != null) {
                    BufferedReader bf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String staffName = bf.readLine();
                    Chatpanel p = new Chatpanel(clientSocket, "Manager", staffName);
                    tabbedPane.add(staffName, p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
