import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainUI extends JFrame{
    private JTextField origMsg;
    private JTextField keyField1, keyField2, keyField3;
    private JTextField verificationField;
    private JTextField secretKeyField;
    private JTextField verify;

    private JButton encryptButton, decryptButton, signButton, verifyButton;

    // Changed to JTextField for copyable functionality (single line)
    private JTextField ciphMsgField;
    private JTextField authMsgField;
    private JTextField decryptedMsgField;

    private final CipherEngine cipherEngine;

    public MainUI(){
        this.cipherEngine = new CipherEngine();
        this.setTitle("Cipher Engine");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());

        this.secretKeyField = placeHolder("Secret Key");
        this.verificationField = placeHolder("Enter Verification Code");

        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new FlowLayout());
        keyPanel.add(this.keyField1 = placeHolder("Key 1"));
        keyPanel.add(this.keyField2 = placeHolder("Key 2"));
        keyPanel.add(this.keyField3 = placeHolder("Key 3"));
        keyPanel.add(this.secretKeyField = placeHolder("Secret Key"));
        keyPanel.add(this.verificationField = placeHolder("Verification Code"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(this.encryptButton = new JButton("Encrypt"));
        buttonPanel.add(this.decryptButton = new JButton("Decrypt"));
        buttonPanel.add(this.signButton = new JButton("Sign"));
        buttonPanel.add(this.verifyButton = new JButton("Verify"));

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        JLabel msgPromptLabel = new JLabel("Enter Message:");
        this.origMsg = new JTextField(10);
        JLabel ciphMsgLabel = new JLabel("Encoded Sentence:");
        JLabel authMsgLabel = new JLabel("Authentication Result:");
        JLabel decryptedMsgLabel = new JLabel("Decrypted Message:");

        // Create copyable text fields
        this.ciphMsgField = createCopyableTextField();
        this.authMsgField = createCopyableTextField();
        this.decryptedMsgField = createCopyableTextField();

        msgPromptLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.origMsg.setAlignmentX(Component.LEFT_ALIGNMENT);
        ciphMsgLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.ciphMsgField.setAlignmentX(Component.LEFT_ALIGNMENT);
        authMsgLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.authMsgField.setAlignmentX(Component.LEFT_ALIGNMENT);
        decryptedMsgLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.decryptedMsgField.setAlignmentX(Component.LEFT_ALIGNMENT);

        messagePanel.add(msgPromptLabel);
        messagePanel.add(Box.createVerticalStrut(5));
        messagePanel.add(this.origMsg);
        messagePanel.add(Box.createVerticalStrut(5));
        messagePanel.add(ciphMsgLabel);
        messagePanel.add(Box.createVerticalStrut(5));
        messagePanel.add(this.ciphMsgField);
        messagePanel.add(Box.createVerticalStrut(5));
        messagePanel.add(authMsgLabel);
        messagePanel.add(Box.createVerticalStrut(5));
        messagePanel.add(this.authMsgField);
        messagePanel.add(Box.createVerticalStrut(5));
        messagePanel.add(decryptedMsgLabel);
        messagePanel.add(Box.createVerticalStrut(5));
        messagePanel.add(this.decryptedMsgField);

        this.add(keyPanel, BorderLayout.NORTH);
        this.add(messagePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    // Method to create a copyable text field that's read-only
    private JTextField createCopyableTextField() {
        JTextField textField = new JTextField(1);
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createLoweredBevelBorder());
        return textField;
    }

    // Method to create placeholder text in a JTextField
    private JTextField placeHolder(String text) {
        JTextField textField = new JTextField(text);
        textField.setForeground(Color.GRAY);

        // Flag to track whether the placeholder is currently shown
        final boolean[] showingPlaceholder = { true };

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder[0]) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    showingPlaceholder[0] = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(text);
                    textField.setForeground(Color.GRAY);
                    showingPlaceholder[0] = true;
                }
            }
        });

        return textField;
    }

    // Getter methods to access the copyable text fields
    public JTextField getCiphMsgField() {
        return ciphMsgField;
    }

    public JTextField getAuthMsgField() {
        return authMsgField;
    }

    public JTextField getDecryptedMsgField() {
        return decryptedMsgField;
    }
}