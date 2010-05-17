/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

/*
 * Permanent.java
 *
 * Created on Dec 22, 2009, 3:25:49 PM
 */

package mage.client.cards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.PopupFactory;
import mage.client.util.ImageHelper;
import mage.view.CounterView;
import mage.view.PermanentView;
import static mage.client.util.Constants.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Permanent extends Card {

	protected PermanentView permanent;

	protected List<Permanent> links = new ArrayList<Permanent>();
	protected boolean linked;
	protected BufferedImage tappedImage;
	protected BufferedImage flippedImage;

    /** Creates new form Permanent */
	public Permanent(PermanentView permanent, BigCard bigCard, CardDimensions dimensions, UUID gameId) {
		super(permanent, bigCard, dimensions, gameId);
		this.setSize(this.getPreferredSize());
		this.permanent = permanent;
		tappedImage = new BufferedImage(dimension.frameHeight, dimension.frameWidth, BufferedImage.TYPE_INT_RGB);
	}

	public UUID getPermanentId() {
		return permanent.getId();
	}

	public List<Permanent> getLinks() {
		return links;
	}

	public boolean isLinked() {
		return linked;
	}

	public void setLinked(boolean linked) {
		this.linked = linked;
	}

	@Override
	protected List<String> getRules() {
		if (permanent.getCounters() != null) {
			List<String> rules = new ArrayList<String>(permanent.getRules());
			for (CounterView counter: permanent.getCounters())
				rules.add(counter.getCount() + " x " + counter.getName());
			return rules;
		}
		else {
			return permanent.getRules();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		p = e.getPoint();
		e.consume();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!linked) {
			int dx = e.getX() - p.x;
			int dy = e.getY() - p.y;
			Rectangle r = this.getBounds();
			r.x += dx;
			r.y += dy;
			if (r.x < 0) r.x = 0;
			if (r.y < 0) r.y = 0;
			this.setBounds(r);
			this.repaint();
			for (Permanent perm: links) {
				r.x += 20;
				r.y += 20;
				perm.setBounds(r);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		super.mouseClicked(arg0);
	}

	@Override
	public void paintComponent(Graphics graphics) {
	    Graphics2D g2 = (Graphics2D) graphics;
		this.setSize(this.getPreferredSize());
		if (permanent.isTapped()) {
			this.getText().setVisible(false);
	    	g2.drawImage(tappedImage, 0, 0, this);
	    }
	    else {
			this.getText().setVisible(true);
	    	g2.drawImage(small, 0, 0, this);
	    }

	    //Add a border, red if card currently has focus
	    if (isFocusOwner()) {
	      g2.setColor(Color.RED);
	    } else {
	      g2.setColor(Color.BLACK);
	    }
	    if (permanent.isTapped()) {
	    	g2.drawRect(0, 0, dimension.frameHeight - 1, dimension.frameWidth - 1);
	    }
	    else {
	    	g2.drawRect(0, 0, dimension.frameWidth - 1, dimension.frameHeight - 1);
	    }

	}

	protected void generateTappedImage() {
		Graphics2D g = (Graphics2D) tappedImage.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(this.createImage(ImageHelper.rotate(small, dimension)), 0, 0, this);

		g.dispose();
	}

	public void update(PermanentView permanent) {
		this.permanent = permanent;
		super.update(permanent);
		if (permanent.getDamage() > 0) {
			Graphics2D g = image.createGraphics();
			g.setColor(Color.RED);
			g.drawString(Integer.toString(permanent.getDamage()), DAMAGE_MAX_LEFT, POWBOX_TEXT_MAX_TOP);
			g.dispose();
		}
		generateTappedImage();
	}

	@Override
	public Dimension getPreferredSize() {
	    if (permanent != null && permanent.isTapped()) {
	    	return new Dimension(dimension.frameHeight, dimension.frameWidth);
	    }
	    else {
	    	return new Dimension(dimension.frameWidth, dimension.frameHeight);
	    }
	}

	@Override
	public Dimension getMinimumSize() {
		return this.getPreferredSize();
	}

	public boolean overlaps(Rectangle r1) {
		return this.getBounds().intersects(r1);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (popup != null)
			popup.hide();
		PopupFactory factory = PopupFactory.getSharedInstance();
		int x = (int) this.getLocationOnScreen().getX() + (permanent.isTapped()?dimension.frameHeight:dimension.frameWidth);
		int y = (int) this.getLocationOnScreen().getY() + 40;
		popup = factory.getPopup(this, popupText, x, y);
		popup.show();
		//hack to get popup to resize to fit text
		popup.hide();
		popup = factory.getPopup(this, popupText, x, y);
		popup.show();
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
