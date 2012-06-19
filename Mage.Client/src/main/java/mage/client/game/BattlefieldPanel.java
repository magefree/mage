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

import mage.cards.MagePermanent;
import mage.client.cards.BigCard;
import mage.client.cards.Permanent;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Config;
import mage.view.PermanentView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BattlefieldPanel extends javax.swing.JLayeredPane {

    private Map<UUID, MagePermanent> permanents = new HashMap<UUID, MagePermanent>();
    private UUID gameId;
    private BigCard bigCard;
    private Map<String, JComponent> ui = new HashMap<String, JComponent>();

    protected Map<UUID, PermanentView> battlefield;
    private Dimension cardDimension;

    private JComponent jPanel;
    private JScrollPane jScrollPane;
    private int width;

    private static int i = 0;

    /** Creates new form BattlefieldPanel */
    public BattlefieldPanel() {
        ui.put("battlefieldPanel", this);
        initComponents();
        ui.put("jPanel", jPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = e.getComponent().getWidth();
                int height = e.getComponent().getHeight();
                BattlefieldPanel.this.jScrollPane.setSize(width, height);
                BattlefieldPanel.this.width = width;
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
            this.battlefield = battlefield;
            sortLayout();
        }
    }

    //TODO: review sorting stuff
    public void sortLayout() {
        int height = Plugins.getInstance().sortPermanents(ui, permanents.values());
        BattlefieldPanel.this.jPanel.setPreferredSize(new Dimension(width - 30, height));
        this.jScrollPane.repaint();
        this.jScrollPane.revalidate();

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

        BattlefieldPanel.this.jPanel.add(perm, 10);
        //this.jPanel.add(perm);
        if (!Plugins.getInstance().isCardPluginLoaded()) {
            moveToFront(perm);
            perm.update(permanent);
        } else {
            moveToFront(jPanel);
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
            int index = permanent.getAttachments().size();
            for (UUID attachmentId: permanent.getAttachments()) {
                MagePermanent link = permanents.get(attachmentId);
                if (link != null) {
                    link.setBounds(r);
                    perm.getLinks().add(link);
                    r.translate(8, 10);
                    perm.setBounds(r);
                    moveToFront(link);
                    moveToFront(perm);
                    jPanel.setComponentZOrder(link, index);
                    index--;
                }
            }
            jPanel.setComponentZOrder(perm, index);
        }

    }

    private void removePermanent(UUID permanentId, final int count) {
        for (Component c: this.jPanel.getComponents()) {
            final Component comp = c;
            if (comp instanceof Permanent) {
                if (((Permanent)comp).getPermanentId().equals(permanentId)) {
                    comp.setVisible(false);
                    this.jPanel.remove(comp);
                }
            } else if (comp instanceof MagePermanent) {
                if (((MagePermanent)comp).getOriginal().getId().equals(permanentId)) {
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Plugins.getInstance().onRemoveCard((MagePermanent)comp, count);
                            comp.setVisible(false);
                            BattlefieldPanel.this.jPanel.remove(comp);
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

    private void initComponents() {
        setOpaque(false);

        jPanel = new JLayeredPane();
        jPanel.setLayout(null);
        jPanel.setOpaque(false);
        jScrollPane = new JScrollPane(jPanel);

        Border empty = new EmptyBorder(0,0,0,0);
        jScrollPane.setBorder(empty);
        jScrollPane.setViewportBorder(empty);
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);

        this.add(jScrollPane);
    }

}
