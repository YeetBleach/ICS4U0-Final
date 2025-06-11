import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;

public class MainUI extends JFrame{
    private JTextField origMsg;
    private JTextField keyField1, keyField2, keyField3;
    private JTextField verificationField;
    private JTextField secretKeyField;
    private JTextField verify;
    private boolean canDecrypt=false;

    private JLabel msgPromptLabel;

    private JButton encryptButton, decryptButton, signButton, verifyButton, resetButton, copyBUtoto;

    // Changed to JTextField for copyable functionality (single line)
    private JTextField ciphMsgField;
    private JTextField authMsgField;
    private JTextField decryptedMsgField;

    private final CipherEngine cipherEngine;
    private final Authenticator authenticator;
    private final KeyManager keyManager;

    public MainUI(){
        this.cipherEngine = new CipherEngine();
        this.authenticator= new Authenticator();
        this.keyManager= new KeyManager();
        this.setTitle("Cipher Engine");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(840, 500);
        this.setLayout(new BorderLayout());
        this.setTitle("Cipher Engine");


        this.secretKeyField = placeHolder("Secret Key");
        this.verificationField = placeHolder("Enter Verification Code");

        JPanel keyPanel = new JPanel();
        keyPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        keyPanel.setLayout(new GridLayout(1, 5, 20, 10));
        keyPanel.add(this.keyField1 = placeHolder("Key 1 (integer)"));
        keyPanel.add(this.keyField2 = placeHolder("Key 2 (UPPER/LOWER)"));
        keyPanel.add(this.keyField3 = placeHolder("Key 3 (integer)"));
        keyPanel.add(this.secretKeyField = placeHolder("Secret Key (integer)"));
        keyPanel.add(this.verificationField = placeHolder("Verification Code (integer)"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(this.encryptButton = new JButton("Encrypt"));
        buttonPanel.add(this.decryptButton = new JButton("Decrypt"));
        buttonPanel.add(this.signButton = new JButton("Sign"));
        buttonPanel.add(this.verifyButton = new JButton("Verify"));
        buttonPanel.add(this.resetButton = new JButton("Reset"));



        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        this.msgPromptLabel = new JLabel("Enter Message:");
        this.origMsg = new JTextField(10);
        JLabel ciphMsgLabel = new JLabel("Encoded Sentence:");
        JLabel authMsgLabel = new JLabel("Authentication Result:");
        JLabel decryptedMsgLabel = new JLabel("Decrypted Message:");

        // Create copyable text fields
        this.ciphMsgField = copyableTxtField();
        this.authMsgField = copyableTxtField();
        this.decryptedMsgField = copyableTxtField();

        this.msgPromptLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
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

        JOptionPane.showMessageDialog(this, "Please enter a message, an integer value for keys 1, 3 and secret key, and either 'Upper' or 'lower' for key 2");

        encryptButton.addActionListener(e ->{
            try {
                if(keyField2.getText().toUpperCase().equals("UPPER")||keyField2.getText().toUpperCase().equals("LOWER")){
                    String msg = origMsg.getText();
                    keyManager.saveKey(Integer.parseInt(keyField1.getText()), Integer.parseInt(keyField3.getText()));
                    keyManager.saveKey(keyField2.getText().toUpperCase());
                    keyManager.setVerifyKey(Integer.parseInt(secretKeyField.getText()));
                    authenticator.setKeys(keyManager);
                    String encryptedMsg=cipherEngine.encrypt(msg, Integer.parseInt(keyField1.getText()), keyField2.getText().toUpperCase(), Integer.parseInt(keyField3.getText()));
                    this.ciphMsgField.setText(encryptedMsg);
                    authenticator.setEntireCode(cipherEngine.getAuthValue());
                    this.origMsg.setText("");
                    JOptionPane.showMessageDialog(this, "Click sign button to generate an verification code");
                }
                else{
                    JOptionPane.showMessageDialog(this, "Please enter 'Upper' or 'Lower' for key2");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric input for keys.");
            }
        });

        this.decryptButton.addActionListener(e -> {;
            if (canDecrypt==true){
                String msg = this.ciphMsgField.getText();
                int k1 = Integer.parseInt(this.keyField1.getText());
                String k2 = this.keyField2.getText().toUpperCase();
                int k3 = Integer.parseInt(this.keyField3.getText());
                String decryptedMsg = cipherEngine.decrypt(msg, k1, k2, k3);
                this.decryptedMsgField.setText(decryptedMsg);
            }
            else{
                JOptionPane.showMessageDialog(this, "Not authorized for decryption");
            }
        });

        this.resetButton.addActionListener(e -> {
            this.origMsg.setText("");
            this.remove(this.keyField1);
            this.remove(this.keyField2);
            this.remove(this.keyField3);
            this.remove(this.secretKeyField);
            this.remove(this.verificationField);

            this.keyField1 = placeHolder("Key 1");
            this.keyField2 = placeHolder("Key 2 (UPPER/LOWER)");
            this.keyField3 = placeHolder("Key 3");
            this.secretKeyField = placeHolder("Secret Key");
            this.verificationField = placeHolder("Verification Code");

            ((JPanel)this.getContentPane().getComponent(0)).removeAll();
            keyPanel.add(this.keyField1);
            keyPanel.add(this.keyField2);
            keyPanel.add(this.keyField3);
            keyPanel.add(this.secretKeyField);
            keyPanel.add(this.verificationField);

            this.ciphMsgField.setText("");
            this.authMsgField.setText("");
            this.decryptedMsgField.setText("");

            keyPanel.revalidate();
            keyPanel.repaint();
        });

        this.verifyButton.addActionListener(e -> {
            try{
                if(authenticator.getAuthCode().equals(new BigInteger("0"))){
                    JOptionPane.showMessageDialog(this, "Please click the sign button to generate an authentication code");
                }
                else{
                    canDecrypt=authenticator.verify(verificationField.getText());
                    authMsgField.setText("Decryption state is "+canDecrypt);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric input.");
            }
        });

        this.signButton.addActionListener(e->{
            authenticator.setAuthCode();
            JOptionPane.showMessageDialog(this, "Fill the verification code box and click verify button to test");
        });

    }

    // Method to create a copyable text field that's read-only
    private JTextField copyableTxtField() {
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
}