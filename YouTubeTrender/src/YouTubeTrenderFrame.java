import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class YouTubeTrenderFrame extends JFrame {
    Dimension frmWDim = new Dimension(10, 0);
    Dimension frmHDim = new Dimension(0, 10);
    Dimension smlVerticalGap = new Dimension(0, 5);
    Dimension smlHorizontalGap = new Dimension(5, 0);

    private JTextField jTextFieldDataFile;
    private JTextField jTextFieldChannel;
    private JTextField jTextFieldDate;
    private JTextField jTextFieldTitle;
    private JTextArea jTextAreaVideoDescription;
    private JTextField jTextFieldViewCount;

    /**
     * Creates new form YouTubeTrenderFrame
     */
    public YouTubeTrenderFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(650, 300));
        setResizable(false);
        initComponents();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) { // feel free to change this as you see fit.
                    // Available: Nimbus, CDE, Metal, Windows...
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            Logger.getLogger(YouTubeTrenderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        EventQueue.invokeLater(() -> new YouTubeTrenderFrame().setVisible(true));
    }

    private void initComponents() {
        JPanel jPanelContainer = new JPanel();
        jPanelContainer.setLayout(new BoxLayout(jPanelContainer, BoxLayout.Y_AXIS));
        jPanelContainer.setBorder(new MatteBorder(10, 10, 10, 10, new Color(0.0f, 0.66f, 0.42f)));
        jPanelContainer.add(Box.createRigidArea(frmHDim));
        jPanelContainer.add(createTopPanel());
        jPanelContainer.add(Box.createRigidArea(frmHDim));
        jPanelContainer.add(createVideoPanel());
        jPanelContainer.add(Box.createRigidArea(frmHDim));
        jPanelContainer.add(Box.createRigidArea(frmHDim));
        jPanelContainer.add(createVideoDetailsPanel());

        add(jPanelContainer);

        pack();
    }


    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setMaximumSize(new Dimension(550, 25));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        jTextFieldDataFile = new JTextField();
        jTextFieldDataFile.setPreferredSize(new Dimension(200, 25));
        jTextFieldDataFile.setText("YouTubeTrender/data/youtubedata_15_50.json");
        JButton jButtonParse = new JButton("Load");

        jButtonParse.addActionListener(e -> {
            String filename = jTextFieldDataFile.getText();
            YouTubeDataParser parser = new YouTubeDataParser();
            try {
                List<YouTubeVideo> videos = parser.parse(filename);
                System.out.println("Loaded videos: " + videos.size());

                jListVideos.setListData(videos.toArray(new YouTubeVideo[0]));

//                for (YouTubeVideo video : videos) {
//                    System.out.println(video);
//                }
            } catch (YouTubeDataParserException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading file: " + ex.getMessage());
            }
        });


        topPanel.add(Box.createRigidArea(frmWDim));
        topPanel.add(jTextFieldDataFile);
        topPanel.add(Box.createRigidArea(frmWDim));
        topPanel.add(jButtonParse);
        topPanel.add(Box.createRigidArea(frmWDim));

        return topPanel;
    }

    private JList<YouTubeVideo> jListVideos;
    private JPanel createVideoPanel() {
        JPanel videoPanel = new JPanel();
        videoPanel.setPreferredSize(new Dimension(525, 240));
        videoPanel.setBorder(BorderFactory.createTitledBorder("Videos"));
        jListVideos = new JList<>();
        JScrollPane jScrollPaneListVideo = new JScrollPane(jListVideos);
        jScrollPaneListVideo.setPreferredSize(new Dimension(500, 200));

        jListVideos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                YouTubeVideo video = jListVideos.getSelectedValue();
                if (video != null) {
                    jTextFieldChannel.setText(video.getChannel());
                    jTextFieldTitle.setText(video.getTitle());
                    jTextFieldDate.setText(video.getDate());
                    jTextFieldViewCount.setText(String.valueOf(video.getViewCount()));
                    jTextAreaVideoDescription.setText(video.getDescription());
                }
            }
        });

        videoPanel.add(jScrollPaneListVideo);
        return videoPanel;
    }

    private JPanel createVideoDetailsPanel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setPreferredSize(new Dimension(525, 300));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Video Details"));

        JLabel jLabelChannel = new JLabel("Channel:");
        jTextFieldChannel = new JTextField();
        jTextFieldChannel.setEditable(false);
        JLabel jLabelDate = new JLabel("Date Posted:");
        jTextFieldDate = new JTextField();
        jTextFieldDate.setEditable(false);
        JLabel jLabelTitle = new JLabel("Title:");
        jTextFieldTitle = new JTextField();
        jTextFieldTitle.setEditable(false);
        JLabel jLabelViewCount = new JLabel("View Count:");
        jTextFieldViewCount = new JTextField();
        jTextFieldViewCount.setEditable(false);
        JLabel jLabelDescription = new JLabel("Description:");
        JScrollPane jScrollPaneVideoDescription = new JScrollPane();
        jTextAreaVideoDescription = new JTextArea();
        jTextAreaVideoDescription.setEditable(false);
        jTextAreaVideoDescription.setColumns(20);
        jTextAreaVideoDescription.setLineWrap(true);
        jTextAreaVideoDescription.setRows(5);
        jTextAreaVideoDescription.setWrapStyleWord(true);
        jScrollPaneVideoDescription.setViewportView(jTextAreaVideoDescription);
        JTextField jTextField = new JTextField(); // dummy to fill the gap
        jTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        jTextField.setEditable(false);

        detailsPanel.add(createHorizontalBox(jLabelChannel, jTextFieldChannel));
        detailsPanel.add(createHorizontalBox(jLabelDate, jTextFieldDate));
        detailsPanel.add(createHorizontalBox(jLabelTitle, jTextFieldTitle));
        detailsPanel.add(createHorizontalBox(jLabelViewCount, jTextFieldViewCount));
        detailsPanel.add(createHorizontalBox(jLabelDescription, jTextField));

        Box descriptionTextBox = Box.createHorizontalBox();
        descriptionTextBox.setPreferredSize(new Dimension(480, 110));
        descriptionTextBox.add(jScrollPaneVideoDescription);

        detailsPanel.add(Box.createHorizontalGlue());
        detailsPanel.add(descriptionTextBox);

        return detailsPanel;
    }

    /**
     * Convenience method to create a Horizontal Box
     *
     * @param jLabel     the label on the left of the box
     * @param jTextField the text field on the right of the box
     * @return the horizontal box
     */
    private Box createHorizontalBox(JLabel jLabel, JTextField jTextField) {
        Box b = Box.createHorizontalBox();
        b.setPreferredSize(new Dimension(480, 25));
        jLabel.setPreferredSize(new Dimension(80, 25));
        jLabel.setHorizontalAlignment(SwingConstants.LEFT);
        b.add(jLabel);
        b.add(Box.createRigidArea(smlHorizontalGap));
        b.add(jTextField);
        return b;
    }


}
