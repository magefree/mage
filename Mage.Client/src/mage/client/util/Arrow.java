package mage.client.util;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Arrow extends JPanel {
	private static final long serialVersionUID = -4631054277822828303L;
	
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int bodyWidth = 10;
	private float headSize = 17;
	private Composite composite;
	private Color color = Color.red;

	public Arrow () {
		setOpaque(false);
		setOpacity(0.6f);
	}

	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		float ex = endX - startX;
		float ey = endY - startY;
		if (ex == 0 && ey == 0) return;
		float length = (float)Math.sqrt(ex * ex + ey * ey);
		float bendPercent = (float)Math.asin(ey / length);
		if (endX > startX) bendPercent = -bendPercent;
		Area arrow = getArrow(length, bendPercent);
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(startX, startY);
		g2d.rotate(Math.atan2(ey, ex));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setComposite(composite);
		g2d.setColor(this.color);
		g2d.fill(arrow);
		g2d.setColor(Color.BLACK);
		g2d.draw(arrow);
	}

	private Area getArrow (float length, float bendPercent) {
		float p1x = 0, p1y = 0;
		float p2x = length, p2y = 0;
		float cx = length / 2, cy = length / 8f * bendPercent;

		float adjSize, ex, ey, abs_e;
		adjSize = (float)(bodyWidth / 2 / Math.sqrt(2));
		ex = p2x - cx;
		ey = p2y - cy;
		abs_e = (float)Math.sqrt(ex * ex + ey * ey);
		ex /= abs_e;
		ey /= abs_e;
		GeneralPath bodyPath = new GeneralPath();
		bodyPath.moveTo(p2x + (ey - ex) * adjSize, p2y - (ex + ey) * adjSize);
		bodyPath.quadTo(cx, cy, p1x, p1y - bodyWidth / 2);
		bodyPath.lineTo(p1x, p1y + bodyWidth / 2);
		bodyPath.quadTo(cx, cy, p2x - (ey + ex) * adjSize, p2y + (ex - ey) * adjSize);
		bodyPath.closePath();

		adjSize = (float)(headSize / Math.sqrt(2));
		ex = p2x - cx;
		ey = p2y - cy;
		abs_e = (float)Math.sqrt(ex * ex + ey * ey);
		ex /= abs_e;
		ey /= abs_e;
		GeneralPath headPath = new GeneralPath();
		headPath.moveTo(p2x - (ey + ex) * adjSize, p2y + (ex - ey) * adjSize);
		headPath.lineTo(p2x, p2y);
		headPath.lineTo(p2x + (ey - ex) * adjSize, p2y - (ex + ey) * adjSize);
		headPath.closePath();

		Area area = new Area(headPath);
		area.add(new Area(bodyPath));
		return area;
	}

	public int getBodyWidth () {
		return bodyWidth;
	}

	public void setBodyWidth (int bodyWidth) {
		this.bodyWidth = bodyWidth;
	}

	public float getHeadSize () {
		return headSize;
	}

	public void setHeadSize (float headSize) {
		this.headSize = headSize;
	}

	public void setArrowLocation (int startX, int startY, int endX, int endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		repaint();
	}

	public void setOpacity (float opacity) {
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public static void main (String[] args) {
		final JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final Arrow arrow = new Arrow();
		frame.add(arrow, BorderLayout.CENTER);
		frame.setSize(640, 480);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.getContentPane().addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved (MouseEvent e) {
				arrow.setArrowLocation(320, 240, e.getX(), e.getY());
			}

			public void mouseDragged (MouseEvent e) {
			}
		});
	}
}
