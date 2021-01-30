package mage.client.game;

import mage.abilities.icon.CardIconRenderSettings;
import mage.cards.MageCard;
import mage.cards.MagePermanent;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.ClientDefaultSettings;
import mage.client.util.GUISizeHelper;
import mage.client.util.audio.AudioManager;
import mage.client.util.layout.CardLayoutStrategy;
import mage.client.util.layout.impl.CardLayoutStrategyImpl;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.util.DebugUtil;
import mage.view.CounterView;
import mage.view.PermanentView;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;

/**
 * Game GUI: battlefield panel (cards panel + scrollbars)
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class BattlefieldPanel extends javax.swing.JLayeredPane {

    private static final Logger logger = Logger.getLogger(BattlefieldPanel.class);

    private static final int GAME_REDRAW_TIMEOUT_MS = 300; // timeout before game goes to redraw on scrollbars change

    // WARNING, permanents contains top level PANELS (cards), use getMainPanel for real MagePermanent (permanents)
    // Source code logic and naming here:
    // * MageCard card - top layer panel (example: permanent + icons layer + another layer);
    // * MagePermanent permanent - original card panel with all data, but without additional panels like icons;
    // * Only MagePermanent allows for panels here, so getMainPanel() must return MagePermanent all the time
    private final Map<UUID, MageCard> permanents = new LinkedHashMap<>();

    private UUID gameId;
    private BigCard bigCard;
    private final Map<String, JComponent> uiComponentsList = new HashMap<>();

    protected Map<UUID, PermanentView> battlefield;
    private Dimension cardDimension;

    private JLayeredPane jPanel; // cards
    private JScrollPane jScrollPane; // scrollbars
    private final CardLayoutStrategy layoutStrategy = new CardLayoutStrategyImpl();

    private javax.swing.Timer gameUpdateTimer; // timer for custom GUI re-drawing (example: update attack arrows)

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
        uiComponentsList.put("scrollPane", this.jScrollPane);
        setGUISize();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateSize();
            }
        });

         gameUpdateTimer = new Timer(GAME_REDRAW_TIMEOUT_MS, evt -> SwingUtilities.invokeLater(() -> {
            gameUpdateTimer.stop();
             ClientCallback updateMessage = new ClientCallback(ClientCallbackMethod.GAME_REDRAW_GUI, gameId);
             MageFrame.getInstance().processCallback(updateMessage);
        }));
    }

    public void updateSize() {
        this.jScrollPane.setSize(this.getWidth(), this.getHeight());
        sortLayout();
    }

    public void init(UUID gameId, BigCard bigCard) {
        this.gameId = gameId;
        this.bigCard = bigCard;
    }

    public void cleanUp() {
        gameUpdateTimer.stop();

        for (Component c : this.jPanel.getComponents()) {
            if (c instanceof MageCard) {
                if (((MageCard) c).getMainPanel() instanceof MagePermanent) {
                    this.jPanel.remove(c);
                }
            }
        }
        permanents.clear();
        this.bigCard = null;
    }

    public void changeGUISize() {
        setGUISize();
        sortLayout();
    }

    private void setGUISize() {
        jScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
        jScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));
        // scrollbars speed changes on layout (depends on cards amount)
        cardDimension = GUISizeHelper.battlefieldCardMaxDimension;
    }

    public boolean isTopPanelBattlefield() {
        return topPanelBattlefield;
    }

    public void setTopPanelBattlefield(boolean topPanelBattlefield) {
        this.topPanelBattlefield = topPanelBattlefield;
    }

    public void update(Map<UUID, PermanentView> battlefield) {
        gameUpdateTimer.stop();
        boolean changed = false;

        List<PermanentView> permanentsToAdd = new ArrayList<>();
        for (PermanentView permanent : battlefield.values()) {
            if (!permanent.isPhasedIn()) {
                continue;
            }
            MageCard oldFound = permanents.get(permanent.getId());
            MagePermanent oldMagePermanent = oldFound == null ? null : (MagePermanent) oldFound.getMainPanel();
            if (oldMagePermanent == null) {
                permanentsToAdd.add(permanent);
                changed = true;
            } else {
                if (!changed) {
                    changed = oldMagePermanent.getOriginalPermanent().isCreature() != permanent.isCreature();
                    // Check if there was a change in the permanets that are the permanent attached to
                    if (!changed) {
                        int attachments = permanent.getAttachments() == null ? 0 : permanent.getAttachments().size();
                        int attachmentsBefore = oldMagePermanent.getLinks().size();
                        if (attachments != attachmentsBefore) {
                            changed = true;
                        } else if (attachments > 0) {
                            Set<UUID> attachmentIds = new HashSet<>(permanent.getAttachments());
                            for (MageCard mageCard : oldMagePermanent.getLinks()) {
                                MagePermanent magePermanent = (MagePermanent) mageCard.getMainPanel();
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
                    // Check if permanents it now attached to another or no permanent
                    if (!changed) {
                        UUID attachedToIdBefore = oldMagePermanent.getOriginalPermanent().getAttachedTo();
                        UUID attachedToId = permanent.getAttachedTo();
                        if (attachedToIdBefore == null && attachedToId != null || attachedToId == null && attachedToIdBefore != null
                                || (attachedToIdBefore != null && !attachedToIdBefore.equals(attachedToId))) {
                            changed = true;
                        }
                    }
                    // Check for changes in the counters of the permanent
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

        for (Iterator<Entry<UUID, MageCard>> iterator = permanents.entrySet().iterator(); iterator.hasNext(); ) {
            Entry<UUID, MageCard> entry = iterator.next();
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

        layoutStrategy.doLayout(this, this.getWidth());

        this.jScrollPane.repaint();
        this.jScrollPane.revalidate();

        invalidate();
        repaint();
    }

    private void addPermanent(PermanentView permanent, final int count) {
        if (cardDimension == null) {
            cardDimension = new Dimension(ClientDefaultSettings.dimensions.getFrameWidth(), ClientDefaultSettings.dimensions.getFrameHeight());
        }
        final MageCard perm = Plugins.instance.getMagePermanent(permanent, bigCard, new CardIconRenderSettings(), cardDimension, gameId, true, PreferencesDialog.getRenderMode(), true);
        perm.setCardContainerRef(jPanel);
        perm.update(permanent);
        // cards sizes changes in parent call by sortLayout
        //perm.setCardBounds

        permanents.put(permanent.getId(), perm);

        this.jPanel.add(perm, (Integer) 10);
        moveToFront(jPanel);
        Plugins.instance.onAddCard(perm, 1);

        if (permanent.isArtifact()) {
            addedArtifact = true;
        } else if (permanent.isCreature()) {
            addedCreature = true;
        } else {
            addedPermanent = true;
        }
    }

    private void removePermanent(UUID permanentId, final int count) {
        for (Component comp : this.jPanel.getComponents()) {
            if (comp instanceof MageCard) {
                MageCard mageCard = (MageCard) comp;
                if (mageCard.getMainPanel() instanceof MagePermanent) {
                    MagePermanent magePermanent = (MagePermanent) mageCard.getMainPanel();
                    if (magePermanent.getOriginal().getId().equals(permanentId)) {
                        Thread t = new Thread(() -> {
                            Plugins.instance.onRemoveCard(mageCard, count);
                            mageCard.setVisible(false);
                            this.jPanel.remove(mageCard);
                        });
                        t.start();
                    }
                    if (magePermanent.getOriginal().isCreature()) {
                        removedCreature = true;
                    }
                }
            }
        }
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }

    public Map<UUID, MageCard> getPermanentPanels() {
        return permanents;
    }

    private void initComponents() {
        setOpaque(false);

        jPanel = new JLayeredPane();
        jPanel.setLayout(null);
        jPanel.setOpaque(false);
        jScrollPane = new JScrollPane(jPanel);
        if (DebugUtil.GUI_GAME_DRAW_BATTLEFIELD_BORDER) {
            jPanel.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            jScrollPane.setBorder(BorderFactory.createLineBorder(Color.green));
        }

        Border empty = new EmptyBorder(0, 0, 0, 0);
        jScrollPane.setBorder(empty);
        jScrollPane.setViewportBorder(empty);
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);
        jScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (gameUpdateTimer.isRunning()) {
                    gameUpdateTimer.restart();
                } else {
                    gameUpdateTimer.start();
                }
            }
        });

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
