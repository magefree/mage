package mage.client.dialog;

import mage.cards.Card;
import mage.cards.CardGraphicInfo;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.decks.Deck;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.util.GUISizeHelper;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.GameImpl;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;
import mage.game.mulligan.MulliganType;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.players.StubPlayer;
import mage.view.*;
import org.apache.log4j.Logger;
import org.mage.card.arcane.CardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author JayDi85
 */
public class TestCardRenderDialog extends MageDialog {

    private static final Logger logger = Logger.getLogger(TestCardRenderDialog.class);
    float cardSizeMod = 1.0f;

    public TestCardRenderDialog() {
        initComponents();
    }

    public void showDialog() {
        this.setModal(false);
        getRootPane().setDefaultButton(buttonCancel);
        reloadCards();

        // windows settings
        MageFrame.getDesktop().remove(this);
        if (this.isModal()) {
            MageFrame.getDesktop().add(this, JLayeredPane.MODAL_LAYER);
        } else {
            MageFrame.getDesktop().add(this, JLayeredPane.PALETTE_LAYER);
        }
        this.makeWindowCentered();

        // Close on "ESC"
        registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setVisible(true);
    }

    private void onCancel() {
        this.removeDialog();
    }

    private PermanentView createPermanentCard(Game game, UUID controllerId, String code, String cardNumber, int power, int toughness, int damage, boolean tapped) {
        CardInfo cardInfo = CardRepository.instance.findCard(code, cardNumber);
        ExpansionInfo setInfo = ExpansionRepository.instance.getSetByCode(code);
        CardSetInfo testSet = new CardSetInfo(cardInfo.getName(), setInfo.getCode(), cardNumber, cardInfo.getRarity(),
                new CardGraphicInfo(cardInfo.getFrameStyle(), cardInfo.usesVariousArt()));
        Card card = CardImpl.createCard(cardInfo.getClassName(), testSet);

        Set<Card> cardsList = new HashSet<>();
        cardsList.add(card);
        game.loadCards(cardsList, controllerId);

        PermanentCard perm = new PermanentCard(card, controllerId, game);
        if (damage > 0) perm.damage(damage, controllerId, game);
        if (power > 0) perm.getPower().setValue(power);
        if (toughness > 0) perm.getToughness().setValue(toughness);
        perm.removeSummoningSickness();
        perm.setTapped(tapped);
        if (perm.isTransformable()) {
            perm.setTransformed(true);
        }
        PermanentView cardView = new PermanentView(perm, card, controllerId, game);
        //cardView.setInViewerOnly(true);

        return cardView;
    }

    private CardView createFaceDownCard(Game game, UUID controllerId, String code, String cardNumber, boolean isMorphed, boolean isManifested, boolean tapped) {
        CardInfo cardInfo = CardRepository.instance.findCard(code, cardNumber);
        ExpansionInfo setInfo = ExpansionRepository.instance.getSetByCode(code);
        CardSetInfo testSet = new CardSetInfo(cardInfo.getName(), setInfo.getCode(), cardNumber, cardInfo.getRarity(),
                new CardGraphicInfo(cardInfo.getFrameStyle(), cardInfo.usesVariousArt()));
        Card card = CardImpl.createCard(cardInfo.getClassName(), testSet);

        Set<Card> cardsList = new HashSet<>();
        cardsList.add(card);
        game.loadCards(cardsList, controllerId);

        PermanentCard perm = new PermanentCard(card, controllerId, game);
        perm.setFaceDown(true, game);
        perm.setMorphed(isMorphed);
        perm.setManifested(isManifested);
        perm.removeSummoningSickness();
        perm.setTapped(tapped);
        if (perm.isTransformable()) {
            perm.setTransformed(true);
        }
        PermanentView cardView = new PermanentView(perm, card, controllerId, game);
        cardView.setInViewerOnly(false); // must false for face down
        return cardView;
    }

    private CardView createHandCard(Game game, UUID controllerId, String code, String cardNumber) {
        CardInfo cardInfo = CardRepository.instance.findCard(code, cardNumber);
        ExpansionInfo setInfo = ExpansionRepository.instance.getSetByCode(code);
        CardSetInfo testSet = new CardSetInfo(cardInfo.getName(), setInfo.getCode(), cardNumber, cardInfo.getRarity(),
                new CardGraphicInfo(cardInfo.getFrameStyle(), cardInfo.usesVariousArt()));
        Card card = CardImpl.createCard(cardInfo.getClassName(), testSet);

        Set<Card> cardsList = new HashSet<>();
        cardsList.add(card);
        game.loadCards(cardsList, controllerId);

        CardView cardView = new CardView(card);
        return cardView;
    }

    private AbilityView createEmblem(Emblem emblem) {
        AbilityView emblemView = new AbilityView(emblem.getAbilities().get(0), emblem.getName(), new CardView(new EmblemView(emblem)));
        emblemView.setName(emblem.getName());
        return emblemView;
    }

    private AbilityView createPlane(Plane plane) {
        AbilityView planeView = new AbilityView(plane.getAbilities().get(0), plane.getName(), new CardView(new PlaneView(plane)));
        planeView.setName(plane.getName());
        return planeView;
    }

    private void reloadCards() {
        cardsPanel.cleanUp();
        cardsPanel.setCustomRenderMode(comboRenderMode.getSelectedIndex());
        cardsPanel.setCustomNeedFullPermanentRender(false); // to fix cropped/position bugged with PermanentView (CardArean do not support it as normal, see CardArea for info)
        cardsPanel.setCustomCardSize(new Dimension(getCardWidth(), getCardHeight()));
        cardsPanel.setCustomXOffsetBetweenCardsOrColumns(10);
        cardsPanel.changeGUISize(); // reload new settings

        // create custom mouse listener
        cardsPanel.setCustomMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardsPanel.mouseClicked(e); // default

                // make cards chooseable or not
                if (e.getSource() instanceof CardPanel) {
                    CardPanel panel = (CardPanel) e.getSource();
                    panel.setChoosable(!panel.isChoosable());
                    cardsPanel.redraw();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                cardsPanel.mousePressed(e); // default
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                cardsPanel.mouseReleased(e); // default
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cardsPanel.mouseEntered(e); // default
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cardsPanel.mouseExited(e); // default
            }
        });

        Game game = new TestGame(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        Deck deck = new Deck();
        Player playerYou = new StubPlayer("player1", RangeOfInfluence.ALL);
        game.addPlayer(playerYou, deck);
        Player playerOpponent = new StubPlayer("player2", RangeOfInfluence.ALL);
        game.addPlayer(playerOpponent, deck);

        ArrayList<CardView> cardViews = new ArrayList<>();
        ///*
        cardViews.add(createPermanentCard(game, playerYou.getId(), "RNA", "263", 0, 0, 0, false)); // mountain
        cardViews.add(createPermanentCard(game, playerYou.getId(), "RNA", "185", 0, 0, 0, true)); // Judith, the Scourge Diva
        //*/
        cardViews.add(createHandCard(game, playerYou.getId(), "DIS", "153")); // Odds // Ends (split card)
        cardViews.add(createHandCard(game, playerYou.getId(), "ELD", "38")); // Animating Faerie (adventure card)
        cardViews.add(createFaceDownCard(game, playerOpponent.getId(), "ELD", "38", false, false, false)); // face down
        cardViews.add(createFaceDownCard(game, playerOpponent.getId(), "ELD", "38", true, false, true)); // morphed
        cardViews.add(createFaceDownCard(game, playerOpponent.getId(), "ELD", "38", false, true, false)); // manifested

        // duplicate cards
        if (checkBoxGenerateManyCards.isSelected()) {
            while (cardViews.size() < 30) {
                int addingCount = cardViews.size();
                for (int i = 0; i < addingCount; i++) {
                    CardView view = cardViews.get(i);
                    CardView newView = new CardView(view);
                    cardViews.add(newView);
                }
            }
        }

        /*
        cardViews.add(createPermanentCard(game, playerYou.getId(), "RNA", "78", 125, 89, 0, false)); // Noxious Groodion

        cardViews.add(createPermanentCard(game, playerYou.getId(), "RNA", "14", 3, 5, 2, false)); // Knight of Sorrows
        cardViews.add(createPermanentCard(game, playerYou.getId(), "DKA", "140", 5, 2, 2, false)); // Huntmaster of the Fells, transforms
        cardViews.add(createPermanentCard(game, playerYou.getId(), "RNA", "221", 0, 0, 0, false)); // Bedeck // Bedazzle
        cardViews.add(createPermanentCard(game, playerYou.getId(), "XLN", "234", 0, 0, 0, false)); // Conqueror's Galleon
        cardViews.add(createEmblem(new AjaniAdversaryOfTyrantsEmblem())); // Emblem Ajani
        cardViews.add(createPlane(new AkoumPlane())); // Plane - Akoum
        //*/

        BigCard big = new BigCard();
        CardsView view = new CardsView(cardViews);
        cardsPanel.loadCards(view, big, game.getId());
    }

    private int getCardWidth() {
        if (GUISizeHelper.editorCardDimension == null) {
            return 200;
        }
        return (int) (GUISizeHelper.editorCardDimension.width * cardSizeMod);
    }

    private int getCardHeight() {
        return (int) (1.4 * getCardWidth());
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonCancel = new javax.swing.JButton();
        cardsPanel = new mage.client.cards.CardArea();
        buttonReloadCards = new javax.swing.JButton();
        labelRenderMode = new javax.swing.JLabel();
        comboRenderMode = new javax.swing.JComboBox<>();
        sliderSize = new javax.swing.JSlider();
        labelSize = new javax.swing.JLabel();
        checkBoxGenerateManyCards = new javax.swing.JCheckBox();

        buttonCancel.setText("Close");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        buttonReloadCards.setText("Reload cards");
        buttonReloadCards.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReloadCardsActionPerformed(evt);
            }
        });

        labelRenderMode.setText("Render mode:");

        comboRenderMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"MTGO", "Image"}));
        comboRenderMode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboRenderModeItemStateChanged(evt);
            }
        });

        sliderSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderSizeStateChanged(evt);
            }
        });

        labelSize.setText("Card size:");

        checkBoxGenerateManyCards.setText("Generate many cards");
        checkBoxGenerateManyCards.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkBoxGenerateManyCardsItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 748, Short.MAX_VALUE)
                                                .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(cardsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(buttonReloadCards)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelRenderMode)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(comboRenderMode, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelSize)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(sliderSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(checkBoxGenerateManyCards)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(buttonReloadCards)
                                                .addComponent(labelRenderMode)
                                                .addComponent(comboRenderMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(labelSize))
                                        .addComponent(sliderSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(checkBoxGenerateManyCards))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cardsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        onCancel();
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void buttonReloadCardsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReloadCardsActionPerformed
        reloadCards();
    }//GEN-LAST:event_buttonReloadCardsActionPerformed

    private void comboRenderModeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboRenderModeItemStateChanged
        reloadCards();
    }//GEN-LAST:event_comboRenderModeItemStateChanged

    private void sliderSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderSizeStateChanged
        // from DragCardGrid         
        // Fraction in [-1, 1]
        float sliderFrac = ((float) (sliderSize.getValue() - 50)) / 50;
        // Convert to frac in [0.5, 2.0] exponentially
        cardSizeMod = (float) Math.pow(2, sliderFrac);
        reloadCards();
    }//GEN-LAST:event_sliderSizeStateChanged

    private void checkBoxGenerateManyCardsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkBoxGenerateManyCardsItemStateChanged
        reloadCards();
    }//GEN-LAST:event_checkBoxGenerateManyCardsItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonReloadCards;
    private mage.client.cards.CardArea cardsPanel;
    private javax.swing.JCheckBox checkBoxGenerateManyCards;
    private javax.swing.JComboBox<String> comboRenderMode;
    private javax.swing.JLabel labelRenderMode;
    private javax.swing.JLabel labelSize;
    private javax.swing.JSlider sliderSize;
    // End of variables declaration//GEN-END:variables
}

class TestGame extends GameImpl {

    private int numPlayers;

    public TestGame(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        super(attackOption, range, mulligan, startLife);
    }

    public TestGame(final TestGame game) {
        super(game);
        this.numPlayers = game.numPlayers;
    }

    @Override
    public MatchType getGameType() {
        return new TestGameType();
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }

    @Override
    public TestGame copy() {
        return new TestGame(this);
    }

}

class TestGameType extends MatchType {

    public TestGameType() {
        this.name = "Test Game Type";
        this.maxPlayers = 10;
        this.minPlayers = 3;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = true;
    }

    protected TestGameType(final TestGameType matchType) {
        super(matchType);
    }

    @Override
    public TestGameType copy() {
        return new TestGameType(this);
    }
}
