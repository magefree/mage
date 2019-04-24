

/*
 * Permanent.java
 *
 * Created on Dec 22, 2009, 3:25:49 PM
 */

package mage.client.cards;

import mage.cards.CardDimensions;
import mage.cards.MagePermanent;
import mage.cards.Sets;
import mage.client.util.Config;
import mage.client.util.TransformedImageCache;
import mage.view.CounterView;
import mage.view.PermanentView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static mage.client.constants.Constants.DAMAGE_MAX_LEFT;
import static mage.client.constants.Constants.POWBOX_TEXT_MAX_TOP;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Permanent extends Card {

    protected PermanentView permanent;

    protected final List<MagePermanent> links = new ArrayList<>();
    protected boolean linked;
    protected final BufferedImage tappedImage;
    protected BufferedImage flippedImage;

    /** Creates new form Permanent
     * @param permanent
     * @param bigCard
     * @param dimensions
     * @param gameId */
    public Permanent(PermanentView permanent, BigCard bigCard, CardDimensions dimensions, UUID gameId) {
        super(permanent, bigCard, dimensions, gameId);
        this.setSize(this.getPreferredSize());
        this.permanent = permanent;
        tappedImage = new BufferedImage(Config.dimensions.frameHeight, Config.dimensions.frameWidth, BufferedImage.TYPE_INT_RGB);
    }

    public UUID getPermanentId() {
        return permanent.getId();
    }

    @Override
    public List<MagePermanent> getLinks() {
        return links;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    @Override
    protected String getText(String cardType) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getText(cardType));
        if (permanent.getOriginal() != null) {
        sb.append("\n----- Originally -------\n");
        sb.append(permanent.getOriginal().getName());
        if (!permanent.getOriginal().getManaCost().isEmpty()) {
            sb.append('\n').append(permanent.getOriginal().getManaCost());
        }
        sb.append('\n').append(getType(permanent.getOriginal()));
        if (permanent.getOriginal().getColor().hasColor()) {
            sb.append('\n').append(permanent.getOriginal().getColor().toString());
        }
        if (permanent.getOriginal().isCreature()) {
            sb.append('\n').append(permanent.getOriginal().getPower()).append('/').append(permanent.getOriginal().getToughness());
        }
        else if (permanent.getOriginal().isPlanesWalker()) {
            sb.append('\n').append(permanent.getOriginal().getLoyalty());
        }
        for (String rule: getRules()) {
            sb.append('\n').append(rule);
        }
        if (!permanent.getOriginal().getExpansionSetCode().isEmpty()) {
            sb.append('\n').append(permanent.getCardNumber()).append(" - ");
            sb.append('\n').append(Sets.getInstance().get(permanent.getOriginal().getExpansionSetCode()).getName()).append(" - ");
            sb.append(permanent.getOriginal().getRarity().toString());
        }
//        sb.append("\n").append(card.getId());
        }
        return sb.toString();

    }

    @Override
    protected List<String> getRules() {
        if (permanent.getCounters() != null) {
            List<String> rules = new ArrayList<>(permanent.getRules());
            for (CounterView counter: permanent.getCounters()) {
                rules.add(counter.getCount() + " x " + counter.getName());
            }
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
            if (r.x < 0) {
                r.x = 0;
            }
            if (r.y < 0) {
                r.y = 0;
            }
            this.setBounds(r);
            this.repaint();
            for (MagePermanent perm: links) {
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
            g2.drawRect(0, 0, Config.dimensions.frameHeight - 1, Config.dimensions.frameWidth - 1);
        }
        else {
            g2.drawRect(0, 0, Config.dimensions.frameWidth - 1, Config.dimensions.frameHeight - 1);
        }

    }

    protected void generateTappedImage() {
        Graphics2D g = (Graphics2D) tappedImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(TransformedImageCache.getRotatedResizedImage(small, dimension.frameWidth, dimension.frameHeight, Math.toRadians(90.0)), 0, 0, this);

        g.dispose();
    }

    @Override
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
            return new Dimension(Config.dimensions.frameHeight, Config.dimensions.frameWidth);
        }
        else {
            return new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
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
        if (!tooltipShowing) {
            if (tooltipPopup != null) {
                tooltipPopup.hide();
            }
            PopupFactory factory = PopupFactory.getSharedInstance();
            int x = (int) this.getLocationOnScreen().getX() + (permanent.isTapped()?Config.dimensions.frameHeight:Config.dimensions.frameWidth);
            int y = (int) this.getLocationOnScreen().getY() + 40;
            tooltipPopup = factory.getPopup(this, tooltipText, x, y);
            tooltipPopup.show();
            //hack to get tooltipPopup to resize to fit text
            tooltipPopup.hide();
            tooltipPopup = factory.getPopup(this, tooltipText, x, y);
            tooltipPopup.show();
            tooltipShowing = true;
        }
    }

    @Override
    public PermanentView getOriginalPermanent() {
        return permanent;
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
