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
 * BattlefieldPanel.java
 *
 * Created on 10-Jan-2010, 10:43:14 PM
 */

package mage.client.game;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.swing.JComponent;
import javax.swing.JScrollPane;


import mage.cards.MagePermanent;
import mage.client.cards.BigCard;
import mage.client.cards.Permanent;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Config;
import mage.view.PermanentView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BattlefieldPanel extends javax.swing.JLayeredPane {

	private Map<UUID, MagePermanent> permanents = new HashMap<UUID, MagePermanent>();
	private UUID gameId;
	private BigCard bigCard;
	private Map<String, JComponent> ui = new HashMap<String, JComponent>();

	protected static Map<UUID, PermanentView> battlefield;
	protected static List<Thread> threads = new ArrayList<Thread>();
	private static Dimension cardDimension;
	
    /** Creates new form BattlefieldPanel */
    public BattlefieldPanel(JScrollPane jScrollPane) {
    	ui.put("jScrollPane", jScrollPane);
    	ui.put("battlefieldPanel", this);
        initComponents();
        
        addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent e) {
				sortLayout();
			}
        });
    }

	public void init(UUID gameId, BigCard bigCard) {
		this.gameId = gameId;
		this.bigCard = bigCard;
	}

	public void update(Map<UUID, PermanentView> battlefield) {
        boolean changed = false;

        List<PermanentView> permanentsToAdd = new ArrayList<PermanentView>();
		for (PermanentView permanent: battlefield.values()) {
			if (!permanents.containsKey(permanent.getId())) {
				permanentsToAdd.add(permanent);
				changed = true;
			} else {
				MagePermanent p = permanents.get(permanent.getId());
				if (!changed) {
					int s1 = permanent.getAttachments() == null ? 0 : permanent.getAttachments().size();
					int s2 = p.getLinks().size();
					if (s1 != s2) {
						changed = true;
					}
				}
				permanents.get(permanent.getId()).update(permanent);
			}
		}
        int count = permanentsToAdd.size();
        for (PermanentView permanent : permanentsToAdd) {
            addPermanent(permanent, count);
        }

		for (Iterator<Entry<UUID, MagePermanent>> i = permanents.entrySet().iterator(); i.hasNext();) {
			Entry<UUID, MagePermanent> entry = i.next();
			if (!battlefield.containsKey(entry.getKey())) {
				removePermanent(entry.getKey(), 1);
				i.remove();
				changed = true;
			}
		}
		
		if (changed) {
			BattlefieldPanel.battlefield = battlefield;
			sortLayout();
			synchronized (this) {
				for (Thread t : threads) {
					t.start();
				}
				threads.clear();
			}
		}
	}
	
	public void sortLayout() {
		Plugins.getInstance().sortPermanents(ui, permanents.values());
		if (battlefield == null) {return;}
		
		for (PermanentView permanent: battlefield.values()) {
			if (permanent.getAttachments() != null) {
				groupAttachments(permanent);
			}
		}
		
		invalidate();
		repaint();
	}

	private void addPermanent(PermanentView permanent, final int count) {
		if (cardDimension == null) {
			cardDimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
		}
		final MagePermanent perm = Plugins.getInstance().getMagePermanent(permanent, bigCard, cardDimension, gameId, true);
		if (!Plugins.getInstance().isCardPluginLoaded()) {
			perm.setBounds(findEmptySpace(new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight)));
		} else {
			//perm.setAlpha(0);
		}
		permanents.put(permanent.getId(), perm);
		
		BattlefieldPanel.this.add(perm, 10);
		if (!Plugins.getInstance().isCardPluginLoaded()) {
			moveToFront(perm);
			perm.update(permanent);
		} else {
            Plugins.getInstance().onAddCard(perm, 1);
			/*Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
        			Plugins.getInstance().onAddCard(perm, count);
				}
			});
			synchronized (this) {
				threads.add(t);
			}*/
		}
	}
	
	private void groupAttachments(PermanentView permanent) {
		MagePermanent perm = permanents.get(permanent.getId());
		if (perm == null) return;
		int position = getPosition(perm);
		perm.getLinks().clear();
		Rectangle r = perm.getBounds();
		if (!Plugins.getInstance().isCardPluginLoaded()) {
			for (UUID attachmentId: permanent.getAttachments()) {
				MagePermanent link = permanents.get(attachmentId);
				if (link != null) {
					perm.getLinks().add(link);
					r.translate(20, 20);
					link.setBounds(r);
					setPosition(link, ++position);
				}
			}
		} else {
			for (UUID attachmentId: permanent.getAttachments()) {
				MagePermanent link = permanents.get(attachmentId);
				if (link != null) {
					link.setBounds(r);
					perm.getLinks().add(link);
					r.translate(8, 10);
					perm.setBounds(r);
					moveToFront(link);
					moveToFront(perm);
				}
			}
		}
		
	}

	private void removePermanent(UUID permanentId, final int count) {
        for (Component c: this.getComponents()) {
        	final Component comp = c;
        	if (comp instanceof Permanent) {
        		if (((Permanent)comp).getPermanentId().equals(permanentId)) {
					this.remove(comp);
        		}
        	} else if (comp instanceof MagePermanent) {
        		if (((MagePermanent)comp).getOriginal().getId().equals(permanentId)) {
        			Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
		        			Plugins.getInstance().onRemoveCard((MagePermanent)comp, count);
							BattlefieldPanel.this.remove(comp);
						}
					});
        			t.start();
        		}
        	}
        }
	}

	private Rectangle findEmptySpace(Dimension size) {
		int battlefieldWidth = this.getWidth();
		Rectangle r = new Rectangle(size);
		boolean intersects;
		while (true) {
			intersects = false;
			for (MagePermanent perm: permanents.values()) {
				Rectangle pr = perm.getBounds();
				if (r.intersects(pr)) {
					intersects = true;
					if (pr.x + pr.width + r.width > battlefieldWidth)
						r.setLocation(0, pr.y + pr.height + 1);
					else
						r.translate(pr.x + pr.width - r.x, 0);
					break;
				}
			}
			if (!intersects)
				break;
		}
		return r;
	}

	@Override
	public boolean isOptimizedDrawingEnabled () {
		return false;
	}
	
	public Map<UUID, MagePermanent> getPermanents() {
		return permanents;
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        //setBackground(java.awt.Color.gray);
        //setForeground(java.awt.Color.gray);
        setOpaque(true);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
