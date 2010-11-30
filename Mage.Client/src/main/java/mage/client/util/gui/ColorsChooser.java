package mage.client.util.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import mage.client.util.Constants;

public class ColorsChooser extends JComboBox implements ListCellRenderer {

	private static final long serialVersionUID = 1L;

	public ColorsChooser(final String colors) {

		this.setRenderer(this);

		final DefaultComboBoxModel model = new DefaultComboBoxModel();
		model.addElement("bu");
		model.addElement("bg");
		model.addElement("br");
		model.addElement("bw");
		model.addElement("ug");
		model.addElement("ur");
		model.addElement("uw");
		model.addElement("gr");
		model.addElement("gw");
		model.addElement("rw");
		setModel(model);
		setSelectedItem(colors);
		this.setFocusable(false);
	}

	@Override
	public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {

		final JPanel panel = new JPanel(new GridLayout(1, 3));
		drawOn(panel, (String) value);
		panel.setBorder(Constants.EMPTY_BORDER);
		if (isSelected) {
			panel.setBackground(Color.LIGHT_GRAY);
		}
		return panel;
	}
	
	private void drawOn(JPanel panel, String value) {
		String s = value.replace("b", "{B}").replace("r", "{R}").replace("g", "{G}").replace("w", "{W}").replace("u", "{U}");
		panel.add(new JLabel(s));
	}
}
