package mage.client.dialog;

import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PhasesDialog extends Dialog {
	
	public PhasesDialog(Frame owner) {
		super(owner);
		initComponents();
	}
	
	public void showDialog() {
		this.setSize(300, 300);
		this.setLocation(100, 100);
		this.setResizable(false);
		this.setTitle("Configure phase stops");
		this.setVisible(true);
	}
	
	private void initComponents() {
		
		label = new JLabel("Stop on:");
		upkeepPhase = new JCheckBox("Upkeep", true);
		drawPhase = new JCheckBox("Draw", true);
		main2Phase = new JCheckBox("Main 2", true);
		
		setLayout(null);
		
		add(label);
		label.setBounds(20, 30, 100, 20);
		
		add(upkeepPhase);
		upkeepPhase.setBounds(35, 50, 150, 30);

		add(drawPhase);
		drawPhase.setBounds(35, 80, 150, 30);
		
		add(main2Phase);
		main2Phase.setBounds(35, 110, 150, 30);
		
        pack();
	}
	
	private JLabel label;
	private JCheckBox upkeepPhase;
	private JCheckBox drawPhase;
	private JCheckBox main2Phase;

}
