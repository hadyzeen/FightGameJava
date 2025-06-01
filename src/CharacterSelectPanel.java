import javax.swing.*;
import java.awt.*;

public class CharacterSelectPanel extends JPanel {
    private GameFrame frame;
    private Image bg;

    public CharacterSelectPanel(GameFrame frame) {
        this.frame = frame;
        bg = new ImageIcon("assets/startbg.png").getImage(); // Your background image

        setLayout(new GridLayout(3, 1));
        setBackground(Color.BLACK);
        setOpaque(false);

        // Title
        JLabel title = new JLabel("Enter Names & Choose Characters", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);

        // Character options
        String[] boys = {"Super Hady", "Ninja Koko", "Captain Mido"};
        String[] girls = {"Divine Nourhan", "Queen Shada", "Ultra Nour"};
        JComboBox<String> player1Choice = new JComboBox<>(boys);
        JComboBox<String> player2Choice = new JComboBox<>(girls);

        // Name fields
        JTextField player1Name = new JTextField("Player 1", 10);
        JTextField player2Name = new JTextField("Player 2", 10);

        // Selection panel
        JPanel selectionPanel = new JPanel();
        selectionPanel.setBackground(new Color(10, 10, 30)); // dark navy
        selectionPanel.setOpaque(false);

        JLabel player1Label = new JLabel("Player 1:");
        player1Label.setFont(new Font("Arial", Font.BOLD, 16));
        player1Label.setForeground(Color.WHITE);

        JLabel player2Label = new JLabel("Player 2:");
        player2Label.setFont(new Font("Arial", Font.BOLD, 16));
        player2Label.setForeground(Color.WHITE);

        selectionPanel.add(player1Label);
        selectionPanel.add(player1Name);
        selectionPanel.add(player1Choice);
        selectionPanel.add(Box.createHorizontalStrut(50));
        selectionPanel.add(player2Label);
        selectionPanel.add(player2Name);
        selectionPanel.add(player2Choice);

        add(selectionPanel);

        // Start button panel
        JButton startButton = new JButton("Start Game");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        add(buttonPanel);

        // Start button action with validation and correct call
        startButton.addActionListener(e -> {
            String name1 = player1Name.getText().trim();
            String name2 = player2Name.getText().trim();

            if (name1.isEmpty() || name2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both player names.");
                return;
            }

            // Build image paths based on selected character names
            String char1 = "assets/" + player1Choice.getSelectedItem();
            String char2 = "assets/" + player2Choice.getSelectedItem();
            frame.startGame(char1, char2, name1, name2);

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, 900, 500, this);
    }
}