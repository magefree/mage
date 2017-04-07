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
import mage.client.util.GUISizeHelper;
import mage.client.util.audio.AudioManager;
import mage.client.util.layout.CardLayoutStrategy;
import mage.client.util.layout.impl.OldCardLayoutStrategy;
import mage.utils.CardUtil;
import mage.view.CounterView;
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

    private final Map<UUID, MagePermanent> permanents = new LinkedHashMap<>();
    private UUID gameId;
    private BigCard bigCard;
    private final Map<String, JComponent> uiComponentsList = new HashMap<>();

    protected Map<UUID, PermanentView> battlefield;
    private Dimension cardDimension;

    private JLayeredPane jPanel;
    private JScrollPane jScrollPane;
    private int width;

    private final CardLayoutStrategy layoutStrategy = new OldCardLayoutStrategy();

    //private static int iCounter = 0;
    private boolean addedPermanent;
    private boolean addedArtifact;
    private boolean addedCreature;

    private boolean removedCreature;
    // defines if the battlefield is within a top (means top row of player panels) or a bottom player panel
    private boolean topPanelBattlefield;

    /**
     * Creates new form BattlefieldPanel
     */
    public BattlefieldPanel() {
        uiComponentsList.put("battlefieldPanel", this);
        initComponents();
        uiComponentsList.put("jPanel", jPanel);
        setGUISize();

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

    public void cleanUp() {
        for (Component c : this.jPanel.getComponents()) {
            if (c instanceof Permanent || c instanceof MagePermanent) {
                this.jPanel.remove(c);
            }
        }
        permanents.clear();
        // Plugins.getInstance().sortPermanents(uiComponentsList, permanents.values());
        this.bigCard = null;
    }

    public void changeGUISize() {
        setGUISize();
        sortLayout();
    }

    private void setGUISize() {
        jScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
        jScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));
        cardDimension = GUISizeHelper.battlefieldCardMaxDimension;
    }

    public boolean isTopPanelBattlefield() {
        return topPanelBattlefield;
    }

    public void setTopPanelBattlefield(boolean topPanelBattlefield) {
        this.topPanelBattlefield = topPanelBattlefield;
    }

    public void update(Map<UUID, PermanentView> battlefield) {
        boolean changed = false;

        List<PermanentView> permanentsToAdd = new ArrayList<>();
        for (PermanentView permanent : battlefield.values()) {
            if (!permanent.isPhasedIn()) {
                continue;
            }
            MagePermanent oldMagePermanent = permanents.get(permanent.getId());
            if (oldMagePermanent == null) {
                permanentsToAdd.add(permanent);
                changed = true;
            } else {
                if (!changed) {
                    changed = CardUtil.isCreature(oldMagePermanent.getOriginalPermanent()) != CardUtil.isCreature(permanent);
                    if (!changed) {
                        int s1 = permanent.getAttachments() == null ? 0 : permanent.getAttachments().size();
                        int s2 = oldMagePermanent.getLinks().size();
                        if (s1 != s2) {
                            changed = true;
                        } else if (s1 > 0) {
                            Set<UUID> attachmentIds = new HashSet<>(permanent.getAttachments());
                            for (MagePermanent magePermanent : oldMagePermanent.getLinks()) {
                                if (!attachmentIds.contains(magePermanent.getOriginalPermanent().getId())) {
                                    // that means that the amount of attachments is the same
                                    // but they are different:
                                    // we've just found an attachment on previous view
                                    // that doesn't exist anymore on current view
                                    changed = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!changed) {
                        UUID u1 = oldMagePermanent.getOriginalPermanent().getAttachedTo();
                        UUID u2 = permanent.getAttachedTo();
                        if (u1 == null && u2 != null || u2 == null && u1 != null
                                || (u1 != null && !u1.equals(u2))) {
                            changed = true;
                        }
                    }
                    if (!changed) {
                        List<CounterView> counters1 = oldMagePermanent.getOriginalPermanent().getCounters();
                        List<CounterView> counters2 = permanent.getCounters();
                        if (counters1 == null && counters2 != null || counters1 != null && counters2 == null) {
                            changed = true;
                        } else if (counters1 != null && counters2 != null && counters1.size() != counters2.size()) {
                            changed = true;
                        }
                    }

                }
                oldMagePermanent.update(permanent);
            }
        }

        addedArtifact = addedCreature = addedPermanent = false;

        int count = permanentsToAdd.size();
        for (PermanentView permanent : permanentsToAdd) {
            addPermanent(permanent, count);
        }

        if (addedArtifact) {
            AudioManager.playAddArtifact();
        } else if (addedCreature) {
            AudioManager.playSummon();
        } else if (addedPermanent) {
            AudioManager.playAddPermanent();
        }

        removedCreature = false;

        for (Iterator<Entry<UUID, MagePermanent>> iterator = permanents.entrySet().iterator(); iterator.hasNext();) {
            Entry<UUID, MagePermanent> entry = iterator.next();
            if (!battlefield.containsKey(entry.getKey()) || !battlefield.get(entry.getKey()).isPhasedIn()) {
                removePermanent(entry.getKey(), 1);
                iterator.remove();
                changed = true;
            }
        }

        if (removedCreature) {
            AudioManager.playDiedCreature();
        }

        if (changed) {
            this.battlefield = battlefield;
            sortLayout();
        }
    }

    public void sortLayout() {
        if (battlefield == null || this.getWidth() < 1) { // Can't do layout when panel is not sized yet
            return;
        }

        layoutStrategy.doLayout(this, width);

        this.jScrollPane.repaint();
        this.jScrollPane.revalidate();

        invalidate();
        repaint();
    }

    private void addPermanent(PermanentView permanent, final int count) {
        if (cardDimension == null) {
            cardDimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
        }
        final MagePermanent perm = Plugins.instance.getMagePermanent(permanent, bigCard, cardDimension, gameId, true);
        if (!Plugins.instance.isCardPluginLoaded()) {
            //perm.setBounds(findEmptySpace(new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight)));
        } else {
            //perm.setAlpha(0);
        }
        permanents.put(permanent.getId(), perm);

        BattlefieldPanel.this.jPanel.add(perm, 10);
        //this.jPanel.add(perm);
        if (!Plugins.instance.isCardPluginLoaded()) {
            moveToFront(perm);
            perm.update(permanent);
        } else {
            moveToFront(jPanel);
            Plugins.instance.onAddCard(perm, 1);
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

        if (permanent.isArtifact()) {
            addedArtifact = true;
        } else if (permanent.isCreature()) {
            addedCreature = true;
        } else {
            addedPermanent = true;
        }
    }

    private void removePermanent(UUID permanentId, final int count) {
        for (Component c : this.jPanel.getComponents()) {
            final Component comp = c;
            if (comp instanceof Permanent) {
                if (((Permanent) comp).getPermanentId().equals(permanentId)) {
                    comp.setVisible(false);
                    this.jPanel.remove(comp);
                }
            } else if (comp instanceof MagePermanent) {
                if (((MagePermanent) comp).getOriginal().getId().equals(permanentId)) {
                    Thread t = new Thread(() -> {
                        Plugins.instance.onRemoveCard((MagePermanent) comp, count);
                        comp.setVisible(false);
                        BattlefieldPanel.this.jPanel.remove(comp);
                    });
                    t.start();
                }
                if (((MagePermanent) comp).getOriginal().isCreature()) {
                    removedCreature = true;
                }
            }
        }
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
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

        Border empty = new EmptyBorder(0, 0, 0, 0);
        jScrollPane.setBorder(empty);
        jScrollPane.setViewportBorder(empty);
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);

        this.add(jScrollPane);
    }

    public JLayeredPane getMainPanel() {
        return jPanel;
    }

    public Map<UUID, PermanentView> getBattlefield() {
        return battlefield;
    }

    public Map<String, JComponent> getUiComponentsList() {
        return uiComponentsList;
    }
}
