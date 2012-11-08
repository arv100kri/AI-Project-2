package com.gesture.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.gesture.uiutils.MyJPanel;

/**
 *
 * @author koolkid
 */
public class UserInterface extends JFrame {
    private Vector<Double> rf_curTime;
    private Vector<Point> rf_drawPoint;
    private final int RADIUS = 5;
    private boolean drawingStarted = false;
    private final int MIN_TRAINING_COUNT = 10;
    private int trainingCount = 0;
    
    /**
     * Creates new form UserInterface
     */
    public UserInterface() 
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setTitle("Gesture Recognition and Training");
        initComponents();
        trainingCapturePanel.setVisible(false);
    }

    private void clearDrawingPanel(MyJPanel drawingPanel)
    {
    	Graphics g = drawingPanel.getGraphics();
		g.clearRect(0, 0, drawingPanel.getWidth(), drawingPanel.getHeight());
		drawingPanel.clearVectorPoints();
		drawingPanel.repaint();
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {

        recognizePane = new JTabbedPane();
        recognizePanel = new JPanel();
        recordTrainingButton = new JButton();
        startButton = new JButton();
        stopButton = new JButton();
        recognizeDrawPanel = new MyJPanel();
        recognizeButton = new JButton();
        verifyPanel = new JPanel();
        gestureComboBox = new JComboBox();
        verifyButton = new JButton();
        clearButton = new JButton();
        trainPanel = new JPanel();
        beginTrainingPanel = new JPanel();
        gestureNameLabel = new JLabel();
        gestureName = new JTextField();
        trainingButton = new JButton();
        trainingCapturePanel = new JPanel();
        trainingDrawPanel = new MyJPanel();
        startTrainingButton = new JButton();
        stopTrainingButton = new JButton();
        clearTrainingButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        startButton.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        startButton.setText("Start Capture");
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                startButton.setBackground(Color.ORANGE);
            }
            public void mouseExited(MouseEvent evt) {
                startButton.setBackground(UIManager.getColor("control"));
            }
        });
        
        startButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent evt)
        	{
        		recognizeDrawPanel.setDrawingStarted(true);
        	}
        	
        });
        
        stopButton.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        stopButton.setText("Stop Capture");
        stopButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                stopButton.setBackground(Color.ORANGE);
            }
            public void mouseExited(MouseEvent evt) {
                stopButton.setBackground(UIManager.getColor("control"));
            }
        });
        
        stopButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent evt)
        	{
        		recognizeDrawPanel.setDrawingStarted(false);
        	}
        	
        });
        
        clearButton.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        clearButton.setText("Clear");
        clearButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                clearButton.setBackground(Color.ORANGE);
            }
            public void mouseExited(MouseEvent evt) {
                clearButton.setBackground(UIManager.getColor("control"));
            }
        });
        clearButton.addActionListener(new ActionListener()
        {
			public void actionPerformed(ActionEvent evt) 
			{
				clearDrawingPanel(recognizeDrawPanel);
			}
        });
        
        recognizeDrawPanel.setBackground(new Color(255, 204, 204));

        GroupLayout recognizeDrawPanelLayout = new GroupLayout(recognizeDrawPanel);
        recognizeDrawPanel.setLayout(recognizeDrawPanelLayout);
        recognizeDrawPanelLayout.setHorizontalGroup(
            recognizeDrawPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 692, Short.MAX_VALUE)
        );
        recognizeDrawPanelLayout.setVerticalGroup(
            recognizeDrawPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );

        recognizeButton.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        recognizeButton.setText("Recognize Gesture");
        recognizeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                recognizeButton.setBackground(Color.ORANGE);
            }
            public void mouseExited(MouseEvent evt) {
                recognizeButton.setBackground(UIManager.getColor("control"));
            }
        });
        
        recognizeButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent evt)
        	{
        		//TO-DO Recognize the gesture drawn in the recognizeDrawPanel and display it
        	}	
    	});

        gestureComboBox.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        gestureComboBox.setModel(new DefaultComboBoxModel(new String[] { "Gesture Model 1", "Gesture Model 2", "Gesture Model 3", "Gesture Model 4", "Gesture Model 5", "Gesture Model 6" }));
        gestureComboBox.setSelectedIndex(0);
        
        verifyButton.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        verifyButton.setText("Verify");
        verifyButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                verifyButton.setBackground(Color.ORANGE);
            }
            public void mouseExited(MouseEvent evt) {
                verifyButton.setBackground(UIManager.getColor("control"));
            }
        });
        
        verifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String gestureToBeVerifiedAgainst = (String)gestureComboBox.getSelectedItem();
				System.out.println(gestureToBeVerifiedAgainst);
				// TODO Verify against this gesture
				
			}
		});
        
        GroupLayout verifyPanelLayout = new GroupLayout(verifyPanel);
        verifyPanel.setLayout(verifyPanelLayout);
        verifyPanelLayout.setHorizontalGroup(
            verifyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(verifyPanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(gestureComboBox, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(verifyButton, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        verifyPanelLayout.setVerticalGroup(
            verifyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, verifyPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(verifyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(verifyButton, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(gestureComboBox))
                .addContainerGap())
        );
        
        GroupLayout recognizePanelLayout = new GroupLayout(recognizePanel);
        recognizePanel.setLayout(recognizePanelLayout);
        recognizePanelLayout.setHorizontalGroup(
            recognizePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(verifyPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(recognizePanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(recognizePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(recognizeDrawPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(recognizePanelLayout.createSequentialGroup()
                        .addComponent(startButton)
                        .addGap(18, 18, 18)
                        .addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(recognizeButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, recognizePanelLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                .addGap(309, 309, 309))
        );
        recognizePanelLayout.setVerticalGroup(
            recognizePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(recognizePanelLayout.createSequentialGroup()
                .addGroup(recognizePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                    .addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                    .addComponent(recognizeButton, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(recognizeDrawPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(clearButton, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verifyPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        
/*--------------Tab 2 ---------------------*/
        
        recognizePane.addTab("Recognize", recognizePanel);

        gestureNameLabel.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        gestureNameLabel.setText("Gesture Name");

        gestureName.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        
        trainingButton.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        trainingButton.setText("Begin Training");
        trainingButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                trainingButton.setBackground(Color.ORANGE);
            }
            public void mouseExited(MouseEvent evt) {
                trainingButton.setBackground(UIManager.getColor("control"));
            }
        });
        
        trainingButton.addActionListener(new ActionListener()
        {
			public void actionPerformed(ActionEvent evt)
        	{
        		if(gestureName.getText().length()>0)
        		{
        			trainingCapturePanel.setVisible(true);
        			beginTrainingPanel.setVisible(false);
        		}
        	}
        });
        
        GroupLayout beginTrainingPanelLayout = new GroupLayout(beginTrainingPanel);
        beginTrainingPanel.setLayout(beginTrainingPanelLayout);
        beginTrainingPanelLayout.setHorizontalGroup(
            beginTrainingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(beginTrainingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gestureNameLabel)
                .addGap(52, 52, 52)
                .addComponent(gestureName, GroupLayout.PREFERRED_SIZE, 503, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, beginTrainingPanelLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(trainingButton, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
                .addGap(267, 267, 267))
        );
        beginTrainingPanelLayout.setVerticalGroup(
            beginTrainingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(beginTrainingPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(beginTrainingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(gestureNameLabel)
                    .addComponent(gestureName, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(trainingButton, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
        );

        trainingDrawPanel.setBackground(new Color(255, 204, 204));

        GroupLayout trainingDrawPanelLayout = new GroupLayout(trainingDrawPanel);
        trainingDrawPanel.setLayout(trainingDrawPanelLayout);
        trainingDrawPanelLayout.setHorizontalGroup(
            trainingDrawPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 692, Short.MAX_VALUE)
        );
        trainingDrawPanelLayout.setVerticalGroup(
            trainingDrawPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
        );

        startTrainingButton.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        startTrainingButton.setText("Start Capture");
        startTrainingButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                startTrainingButton.setBackground(Color.ORANGE);
            }
            public void mouseExited(MouseEvent evt) {
                startTrainingButton.setBackground(UIManager.getColor("control"));
            }
        });
        
        startTrainingButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent evt)
        	{
        		trainingDrawPanel.setDrawingStarted(true);
        	}
        	
        });
        
        
        stopTrainingButton.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        stopTrainingButton.setText("Stop Capture");
        stopTrainingButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                stopTrainingButton.setBackground(Color.ORANGE);
            }
            public void mouseExited(MouseEvent evt) {
                stopTrainingButton.setBackground(UIManager.getColor("control"));
            }
        });
        
        stopTrainingButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent evt)
        	{
        		trainingDrawPanel.setDrawingStarted(false);
        	}
        	
        });
        
        clearTrainingButton.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        clearTrainingButton.setText("Clear");
        clearTrainingButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                clearTrainingButton.setBackground(Color.ORANGE);
            }
            public void mouseExited(MouseEvent evt) {
                clearTrainingButton.setBackground(UIManager.getColor("control"));
            }
        });
        
        clearTrainingButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent evt)
        	{
        		clearDrawingPanel(trainingDrawPanel);
        	}
        });
        
        recordTrainingButton.setFont(new Font("Century Schoolbook", 0, 24)); // NOI18N
        recordTrainingButton.setText("Record");
        recordTrainingButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                recordTrainingButton.setBackground(Color.ORANGE);
            }
            public void mouseExited(MouseEvent evt) {
                recordTrainingButton.setBackground(UIManager.getColor("control"));
            }
        });
        
        recordTrainingButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent evt)
        	{
        		++trainingCount;
        		if(trainingCount == MIN_TRAINING_COUNT)
        		{
        			trainingCount = 0;
        			JOptionPane.showMessageDialog(null, "Gesture: "+gestureName.getText()+" has been added to list of trained gestures");
        			gestureName.setText("");
        			beginTrainingPanel.setVisible(true);
        			clearDrawingPanel(trainingDrawPanel);
        			trainingCapturePanel.setVisible(false);
        		}
        		//TO-DO : Actually record the gesture and build HMM for it
        	}
    	});
        
        javax.swing.GroupLayout trainingCapturePanelLayout = new javax.swing.GroupLayout(trainingCapturePanel);
        trainingCapturePanel.setLayout(trainingCapturePanelLayout);
        trainingCapturePanelLayout.setHorizontalGroup(
            trainingCapturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trainingCapturePanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(trainingCapturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(trainingCapturePanelLayout.createSequentialGroup()
                        .addComponent(startTrainingButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stopTrainingButton)
                        .addGap(14, 14, 14)
                        .addComponent(clearTrainingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recordTrainingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(trainingDrawPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );
        trainingCapturePanelLayout.setVerticalGroup(
            trainingCapturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trainingCapturePanelLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(trainingCapturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(startTrainingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(recordTrainingButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(clearTrainingButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stopTrainingButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(trainingDrawPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        
        GroupLayout trainPanelLayout = new GroupLayout(trainPanel);
        trainPanel.setLayout(trainPanelLayout);
        trainPanelLayout.setHorizontalGroup(
            trainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(beginTrainingPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(trainingCapturePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        trainPanelLayout.setVerticalGroup(
            trainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(trainPanelLayout.createSequentialGroup()
                .addComponent(beginTrainingPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trainingCapturePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        recognizePane.addTab("Train", trainPanel);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(recognizePane, GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(recognizePane)
        );

        pack();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserInterface().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private JPanel beginTrainingPanel;
    private JButton clearButton;
    private JButton clearTrainingButton;
    private JComboBox gestureComboBox;
    private JTextField gestureName;
    private JLabel gestureNameLabel;
    private JButton recognizeButton;
    private MyJPanel recognizeDrawPanel;
    private JTabbedPane recognizePane;
    private JPanel recognizePanel;
    private JButton recordTrainingButton;
    private JButton startButton;
    private JButton startTrainingButton;
    private JButton stopButton;
    private JButton stopTrainingButton;
    private JPanel trainPanel;
    private JButton trainingButton;
    private JPanel trainingCapturePanel;
    private MyJPanel trainingDrawPanel;
    private JButton verifyButton;
    private JPanel verifyPanel;
    // End of variables declaration
}