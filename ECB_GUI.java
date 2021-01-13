import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ECB_GUI extends JFrame implements ActionListener, ChangeListener {

JLabel outPutLabel;
JTextField textInput;
JSpinner blockSizeInput;
JButton encryptButton;
JButton decryptButton;
JTextArea outPutArea;

int frameHeight = 500;
int frameWidth = 1000;


    public static void main(String[] args) {
        new ECB_GUI();
        new ECBEncryption();
    }

    ECB_GUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(frameWidth, frameHeight));
        this.getContentPane().setBackground(Color.BLUE);


        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(43, 43, 43));
        mainPanel.setSize(new Dimension(500, 400));
        mainPanel.setBounds(250, 25, 500, 400 );
        mainPanel.setLayout(null);

        JLabel headline = new JLabel("ECB Encryption");
        Dimension headLineSize = headline.getPreferredSize();
        headline.setBounds(250 - (200 / 2), 30, headLineSize.width, headLineSize.height);
        headline.setSize(new Dimension(250, 35));
        headline.setFont(new Font("Arial", Font.PLAIN, 30));
        headline.setForeground(new Color(0xFDE030));

        textInput = new JTextField();
        Dimension textInputSize = textInput.getPreferredSize();
        textInput.setBounds(250 - (300 / 2), 100, textInputSize.width, textInputSize.height);
        textInput.setSize(new Dimension(300,40));
        textInput.setFont(new Font("Arial", Font.PLAIN, 20));
        textInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                outPutLabel.setText("");
                outPutArea.setText("");
                setMaxBlockSize();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                outPutLabel.setText("");
                outPutArea.setText("");
                setMaxBlockSize();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        blockSizeInput = new JSpinner();
        Dimension blockSizeInputSize = blockSizeInput.getPreferredSize();
        blockSizeInput.setBounds(250 - (150 / 2), 175, blockSizeInputSize.width, blockSizeInputSize.height);
        blockSizeInput.setSize(new Dimension(150,40));
        blockSizeInput.setFont(new Font("Arial", Font.PLAIN, 20));
        blockSizeInput.setModel(new SpinnerNumberModel(2, 2, 2, 1));
        blockSizeInput.addChangeListener(this);

        encryptButton = new JButton("Verschlüsseln");
        encryptButton.setSize(new Dimension(50,30));
        Dimension encryptBtnSize = encryptButton.getPreferredSize();
        encryptButton.setBounds(166 - (encryptBtnSize.width / 2), 250, encryptBtnSize.width, encryptBtnSize.height);
        encryptButton.addActionListener(this);

        decryptButton = new JButton("Entschlüsseln");
        decryptButton.setSize(new Dimension(50,30));
        Dimension decryptBtnSize = decryptButton.getPreferredSize();
        decryptButton.setBounds(332 - (decryptBtnSize.width / 2), 250, decryptBtnSize.width, decryptBtnSize.height);
        decryptButton.addActionListener(this);

        outPutLabel = new JLabel();
        Dimension outPutLabelSize = headline.getPreferredSize();
        outPutLabel.setBounds(200 - (200 / 2), 300, outPutLabelSize.width, outPutLabelSize.height);
        outPutLabel.setSize(new Dimension(300, 16));
        outPutLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        outPutArea = new JTextArea();
        Dimension outPutAreaSize = outPutArea.getPreferredSize();
        outPutArea.setBounds(250 - (300 / 2), 320, outPutAreaSize.width, outPutAreaSize.height);
        outPutArea.setSize(new Dimension(300,60));
        outPutArea.setBackground(new Color(43, 43, 43));
        outPutArea.setFont(new Font("Arial", Font.PLAIN, 15));
        outPutArea.setLineWrap(true);

        JScrollPane outPutScroll = new JScrollPane(outPutArea);
        outPutScroll.setBounds(250 - (300 / 2), 320, outPutAreaSize.width, outPutAreaSize.height);
        outPutScroll.setSize(new Dimension(300, 60));
        outPutScroll.setBorder(null);
        outPutScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);



        mainPanel.add(headline);
        mainPanel.add(textInput);
        mainPanel.add(encryptButton);
        mainPanel.add(blockSizeInput);
        mainPanel.add(decryptButton);
        mainPanel.add(outPutLabel);
        mainPanel.add(outPutScroll);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    private void setMaxBlockSize() {
        int maxLength = textInput.getText().length() * ECBEncryption.symbolLenght();
        if (textInput.getText().length() == 0) {
            blockSizeInput.setModel(new SpinnerNumberModel(2, 2, 2, 1));
            return;
        }
        if (maxLength < (int) blockSizeInput.getValue() || textInput.getText().length() == 0) {
            blockSizeInput.setModel(new SpinnerNumberModel(maxLength, 2, maxLength, 1));
            return;
        }
        blockSizeInput.setModel(new SpinnerNumberModel((int) blockSizeInput.getValue(), 2, maxLength, 1));
    }
    @Override
    public void stateChanged(ChangeEvent e) {
        int maxLength = textInput.getText().length() * ECBEncryption.symbolLenght();
        if (e.getSource() == blockSizeInput) {
            outPutLabel.setText("");
            outPutArea.setText("");
            if (maxLength == (int) blockSizeInput.getValue()) {
                outPutArea.setText("Kann nicht erhöht werden. Die länge des eingegebenen Textes ist zu kurz!");
                outPutArea.setForeground(Color.RED);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (textInput.getText().length() == 0) {
            outPutLabel.setText("ERROR: Input Feld Leer!");
            outPutLabel.setForeground(Color.RED);
            return;
        }
        
        if (e.getSource() == encryptButton) {

            if (ECBEncryption.CheckForInvalidChar(textInput.getText()) != null) {
                outPutLabel.setText(ECBEncryption.CheckForInvalidChar(textInput.getText()));
                outPutLabel.setForeground(Color.RED);
                return;
            }
            if (ECBEncryption.CheckTextLength(textInput.getText(), (Integer) blockSizeInput.getValue()) != null) {
                outPutLabel.setText(ECBEncryption.CheckTextLength(textInput.getText(), (Integer) blockSizeInput.getValue()));
                outPutLabel.setForeground(Color.RED);
                return;
            }
            String encryptedString = ECBEncryption.encrypt(textInput.getText(), (Integer) blockSizeInput.getValue());
            outPutLabel.setText("Verschlüsselter String: ");
            outPutLabel.setForeground(Color.WHITE);

            outPutArea.setText(encryptedString);
            outPutArea.setForeground(Color.WHITE);

        } else if (e.getSource() == decryptButton) {

            if (ECBEncryption.CheckForInvalidChar(textInput.getText()) != null) {
                outPutLabel.setText(ECBEncryption.CheckForInvalidChar(textInput.getText()));
                outPutLabel.setForeground(Color.RED);
                return;
            }
            if (ECBEncryption.CheckTextLength(textInput.getText(), (Integer) blockSizeInput.getValue()) != null) {
                outPutLabel.setText(ECBEncryption.CheckTextLength(textInput.getText(), (Integer) blockSizeInput.getValue()));
                outPutLabel.setForeground(Color.RED);
                return;
            }

            String decryptedString = ECBEncryption.decrypt(textInput.getText(), (Integer) blockSizeInput.getValue());
            outPutLabel.setText("Entschlüsselter String: ");
            outPutLabel.setForeground(Color.WHITE);

            outPutArea.setText(decryptedString);
            outPutArea.setForeground(Color.WHITE);

        }
    }
}
