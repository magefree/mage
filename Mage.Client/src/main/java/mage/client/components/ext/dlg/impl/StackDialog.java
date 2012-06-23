package mage.client.components.ext.dlg.impl;

import mage.cards.MageCard;
import mage.client.cards.BigCard;
import mage.client.components.HoverButton;
import mage.client.components.ext.dlg.DialogContainer;
import mage.client.components.ext.dlg.DialogManager;
import mage.client.components.ext.dlg.DlgParams;
import mage.client.components.ext.dlg.IDialogPanel;
import mage.client.game.FeedbackPanel;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Command;
import mage.client.util.Config;
import mage.client.util.SettingsManager;
import mage.view.CardView;
import mage.view.CardsView;
import mage.view.StackAbilityView;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

/**
 * @author mw, noxx
 */
public class StackDialog extends IDialogPanel {

    private static final long serialVersionUID = 1L;
    private JLabel jTitle = null;
    private JLabel jTitle2 = null;
    private HoverButton jButtonAccept = null;
    private HoverButton jButtonResponse = null;

    private JLayeredPane jLayeredPane;
    private FeedbackPanel feedbackPanel;

    private class CustomLabel extends JLabel {

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2D = (Graphics2D)g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2D.setColor(Color.black);
            g2D.drawString(getText(), 1, 11);
            g2D.setColor(Color.white);
            g2D.drawString(getText(), 0, 10);
        }

        private static final long serialVersionUID = 1L;
    };

    /**
     * This is the default constructor
     */
    public StackDialog(DlgParams params) {
        super(params);
        this.feedbackPanel = params.feedbackPanel;
        initialize();
        displayStack(params.getCards(), params.gameId, params.bigCard);
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {

        int w = getDlgParams().rect.width;
        int h = getDlgParams().rect.height;

        jLayeredPane = new JLayeredPane();
        add(jLayeredPane);
        jLayeredPane.setSize(w, h);
        jLayeredPane.setVisible(true);
        jLayeredPane.setOpaque(false);

        jTitle = new CustomLabel();
        jTitle.setBounds(new Rectangle(5, 3, w, 16));
        jTitle.setFont(new Font("Dialog", Font.BOLD, 14));
        jTitle.setText("Current stack: ");

        /*jTitle2 = new CustomLabel();
        jTitle2.setBounds(new Rectangle(5, 5 + SettingsManager.getInstance().getCardSize().height + 30, 129, 20));
        jTitle2.setFont(new Font("Dialog", Font.BOLD, 14));
        jTitle2.setText("Spell targets:");*/

        this.setLayout(null);
        jLayeredPane.setLayout(null);

        jLayeredPane.add(jTitle, null);
        //jLayeredPane.add(jTitle2, null);
        jLayeredPane.add(getJButtonAccept(), null);
        jLayeredPane.add(getJButtonResponse(), null);

        makeTransparent(jLayeredPane);
    }

    private void displayStack(CardsView cards, UUID gameId, BigCard bigCard) {

        if (cards == null || cards.size() == 0) {
            return;
        }

        /**
         * Display spells and theis targets above them
         */
        int dx = (SettingsManager.getInstance().getCardSize().width + 15) * (cards.size() - 1);
        int dy = 30;

        for (CardView card : cards.values()) {

            if (card instanceof StackAbilityView) {
                CardView tmp = ((StackAbilityView)card).getSourceCard();
                tmp.overrideRules(card.getRules());
                tmp.setIsAbility(true);
                tmp.overrideTargets(card.getTargets());
                tmp.overrideId(card.getId());
                card = tmp;
            }

            MageCard cardImg = Plugins.getInstance().getMageCard(card, bigCard, getCardDimension(), gameId, true);
            //cardImg.setBorder(BorderFactory.createLineBorder(Color.red));
            cardImg.setLocation(dx, dy);

            jLayeredPane.add(cardImg, JLayeredPane.DEFAULT_LAYER, 1);

            dx -= (SettingsManager.getInstance().getCardSize().width + 15);
        }
    }

    private Dimension getCardDimension() {
        if (cardDimension == null) {
            cardDimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
        }
        return cardDimension;
    }

    private HoverButton getJButtonAccept() {
        if (jButtonAccept == null) {
            jButtonAccept = new HoverButton("", ImageManagerImpl.getInstance().getDlgAcceptButtonImage(),
                    ImageManagerImpl.getInstance().getDlgActiveAcceptButtonImage(),
                    ImageManagerImpl.getInstance().getDlgAcceptButtonImage(),
                    new Rectangle(60, 60));
            int w = getDlgParams().rect.width - 90;
            int h = getDlgParams().rect.height - 90;
            jButtonAccept.setBounds(new Rectangle(w / 2 - 80, h - 50, 60, 60));
            //jButtonAccept.setBorder(BorderFactory.createLineBorder(Color.red));

            jButtonAccept.setObserver(new Command() {
                @Override
                public void execute() {
                    DialogManager.getManager().fadeOut((DialogContainer)getParent());
                    //GameManager.getInputControl().getInput().selectButtonOK();
                    StackDialog.this.feedbackPanel.doClick();
                }
                private static final long serialVersionUID = 1L;
            });
        }
        return jButtonAccept;
    }

    private HoverButton getJButtonResponse() {
        if (jButtonResponse == null) {
            jButtonResponse = new HoverButton("", ImageManagerImpl.getInstance().getDlgCancelButtonImage(),
                    ImageManagerImpl.getInstance().getDlgActiveCancelButtonImage(),
                    ImageManagerImpl.getInstance().getDlgCancelButtonImage(),
                    new Rectangle(60, 60));
            int w = getDlgParams().rect.width - 90;
            int h = getDlgParams().rect.height - 90;
            jButtonResponse.setBounds(new Rectangle(w / 2 + 5, h - 48, 60, 60));

            jButtonResponse.setObserver(new Command() {
                @Override
                public void execute() {
                    DialogManager.getManager().fadeOut((DialogContainer)getParent());
                }
                private static final long serialVersionUID = 1L;
            });
        }
        return jButtonResponse;
    }

    private Dimension cardDimension;
 }