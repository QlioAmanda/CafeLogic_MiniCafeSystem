package cafe.gui.admin; 

import cafe.gui.CafeTheme;
import cafe.gui.components.RoundedButton;
import cafe.gui.common.ErrorDialog; 
import cafe.service.LoginService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AdminLoginDialog extends JDialog {
    private boolean authenticated = false;
    private JTextField txtUser;
    private JPasswordField txtPass;

    public AdminLoginDialog(Frame owner) {
        super(owner, true);
        setUndecorated(true);
        setSize(320, 320);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CafeTheme.BG_COLOR);
        mainPanel.setBorder(new LineBorder(CafeTheme.EXIT_BTN_COLOR, 3));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        header.setBackground(CafeTheme.EXIT_BTN_COLOR);
        JLabel title = new JLabel("LOGIN ADMIN");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        header.add(title);
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(CafeTheme.BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblUser.setForeground(CafeTheme.TEXT_COLOR);
        content.add(lblUser, gbc);

        gbc.gridy = 1;
        txtUser = new JTextField();
        txtUser.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtUser.setPreferredSize(new Dimension(200, 40));
        txtUser.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CafeTheme.EXIT_BTN_COLOR, 1), new EmptyBorder(5, 5, 5, 5)));
        content.add(txtUser, gbc);

        gbc.gridy = 2; gbc.insets = new Insets(15, 10, 5, 10);
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPass.setForeground(CafeTheme.TEXT_COLOR);
        content.add(lblPass, gbc);

        gbc.gridy = 3; gbc.insets = new Insets(5, 10, 5, 10);
        txtPass = new JPasswordField();
        txtPass.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtPass.setPreferredSize(new Dimension(200, 40));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CafeTheme.EXIT_BTN_COLOR, 1), new EmptyBorder(5, 5, 5, 5)));
        content.add(txtPass, gbc);
        mainPanel.add(content, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        footer.setBackground(CafeTheme.BG_COLOR);
        RoundedButton btnCancel = new RoundedButton("Batal");
        btnCancel.setBackground(new Color(200, 80, 80));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(100, 40));
        RoundedButton btnLogin = new RoundedButton("Masuk");
        btnLogin.setBackground(CafeTheme.BUTTON_COLOR);
        btnLogin.setPreferredSize(new Dimension(100, 40));
        footer.add(btnCancel); footer.add(btnLogin);
        mainPanel.add(footer, BorderLayout.SOUTH);
        add(mainPanel);

        btnCancel.addActionListener(e -> dispose());
        btnLogin.addActionListener(e -> attemptLogin());
        txtPass.addActionListener(e -> attemptLogin());
    }

    private void attemptLogin() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());
        if (LoginService.getInstance().loginAdmin(user, pass)) {
            authenticated = true;
            dispose();
        } else {
            new ErrorDialog(this, "Username atau Password salah!").setVisible(true);
        }
    }
    public boolean isAuthenticated() { return authenticated; }
}