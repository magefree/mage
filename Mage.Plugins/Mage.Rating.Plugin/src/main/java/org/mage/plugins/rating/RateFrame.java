package org.mage.plugins.rating;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.mage.plugins.rating.results.ResultHandler;
import org.mage.plugins.rating.ui.BigCard;

public class RateFrame extends JFrame {

	private static Logger log = Logger.getLogger(RateFrame.class);
	private BigCard bigCard;
	
	public RateFrame() {
		setTitle("Mage Rate Cards, version 0.1");

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}

		int width = 621;
		int height = 384;
		setSize(width, height);
		int w = getGraphicsConfiguration().getBounds().width;
		int h = getGraphicsConfiguration().getBounds().height;
		setLocation((w - width)/2, (h - height)/2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);
		
		bigCard = new BigCard();
		bigCard.setBounds(20, 10, RateThread.bigCardDimension.frameWidth, RateThread.bigCardDimension.frameHeight);
		bigCard.setBorder(BorderFactory.createLineBorder(Color.gray));
		add(bigCard);
		
		JLabel label = new JLabel("The results are stored for every 10 compare.");
		label.setBounds(290, 270, 300, 30);
		add(label);
		
		JButton rate = new JButton("Create results.txt");
		rate.setBounds(340, 230, 120, 25);
		rate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ResultHandler.getInstance().rate();
					JOptionPane.showMessageDialog(null, "Done! Find results in ratings.txt file.");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Some error occured! Find more details in logs.");
				}
			}
		});
		add(rate);
	}
	
	public void startRating() {
		RateThread.getInstance().start(this, this.bigCard);
	}

	public static void main(String args[]) {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				log.error(e.getMessage(), e);
			}
		});
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				RateFrame frame = new RateFrame();
				frame.setVisible(true);
				frame.startRating();
			}
		});
	}

	private static final long serialVersionUID = -5836021378309984439L;
}
