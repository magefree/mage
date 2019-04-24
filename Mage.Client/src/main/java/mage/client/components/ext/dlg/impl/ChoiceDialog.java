package mage.client.components.ext.dlg.impl;

import mage.cards.MageCard;
import mage.client.cards.BigCard;
import mage.client.components.HoverButton;
import mage.client.components.ext.ShadowLabel;
import mage.client.components.ext.dlg.DialogContainer;
import mage.client.components.ext.dlg.DialogManager;
import mage.client.components.ext.dlg.DlgParams;
import mage.client.components.ext.dlg.IDialogPanel;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Command;
import mage.client.util.SettingsManager;
import mage.client.util.audio.AudioManager;
import mage.view.CardView;
import mage.view.CardsView;
import org.mage.card.arcane.CardPanel;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author mw, noxx
 */
public class ChoiceDialog extends IDialogPanel {

    private static final long serialVersionUID = 1L;

    private ShadowLabel jTitle = null;
    private HoverButton jButtonOK = null;
    private HoverButton jButtonPrevPage = null;
    private HoverButton jButtonNextPage = null;
    private HoverButton jButtonCancel = null;

    private JButton jButtonSort = null;

    private final CardsView cards;
    private final UUID gameId;

    private int page = 1;
    private int maxPages;
    private int in_a_row = 4;
    private int rows = 2;

    private boolean isOptional = false;
    private boolean isChooseAbility = false;
    private boolean isCancelStopsPlaying = true;

    private final DlgParams params;
    
    private final String title;

    /**
     * This is the default constructor
     * @param params
     * @param title
     */
    public ChoiceDialog(DlgParams params, String title) {
        super(params);
        this.params = params;
        this.gameId = params.gameId;
        this.title = title;

        cards = params.getCards();
        isOptional = params.isOptional();
        isCancelStopsPlaying = params.isCancelStopsPlaying();
        isChooseAbility = params.isChooseAbility();

        in_a_row = 5;
        rows = 2;

        /**
         * Calculate max pages
         */
        maxPages = cards.size() / (in_a_row * rows);
        if (cards.size() % (in_a_row * rows) != 0) {
            maxPages++;
        }

        /**
         * Init
         */
        initialize();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        jTitle = new ShadowLabel(title, 14);
        jTitle.setBounds(new Rectangle(5, 4, 500, 16));
        jTitle.setFont(new Font("Dialog", Font.BOLD, 14));

        this.setLayout(null);

        /**
         * Components
         */
        this.add(jTitle, null);
        this.add(getJButtonOK(), null);
        this.add(getJButtonPrevPage(), null);
        this.add(getJButtonNextPage(), null);
        this.add(getJButtonSort(), null);
        this.add(getJButtonCancel(), null);
        makeTransparent();

        /**
         * Manage cards
         */
        ///GameManager.getManager().resetChosenCards();
        displayCards(params.getCards(), params.gameId, params.bigCard);
    }

    public void cleanUp() {
        for (Component comp : this.getComponents()) {
            if (comp instanceof CardPanel) {
                ((CardPanel) comp).cleanUp();
                this.remove(comp);
            }
        }
    }

    private void displayCards(CardsView cards, UUID gameId, BigCard bigCard) {
        if (cards.isEmpty()) {
            return;
        }

        ArrayList<Component> toRemove = new ArrayList<>();
        for (int i = getComponentCount() - 1; i > 0; i--) {
            Component o = getComponent(i);
            if (o instanceof MageCard) {
                toRemove.add(o);
            }
        }
        for (int i = 0; i < toRemove.size(); i++) {
            remove(toRemove.get(i));
        }

        ArrayList<CardView> cardList = new ArrayList<>(cards.values());

        int width = SettingsManager.instance.getCardSize().width;
        int height = SettingsManager.instance.getCardSize().height;

        int dx = 0;
        int dy = 30;


        int startIndex = (page - 1) * (in_a_row * rows);
        int countPerPage = (in_a_row * rows);
        int j = 0;

        for (int i = startIndex; i < cards.size() && i < startIndex + countPerPage; i++) {
            if (i > startIndex && i % in_a_row == 0) { // next row
                j++;
                dx = 0;
            }

            CardView card = cardList.get(i);
            MageCard cardImg = Plugins.instance.getMageCard(card, bigCard, getCardDimension(), gameId, true, true);

            cardImg.setLocation(dx, dy + j*(height + 30));
            add(cardImg);

            dx += (width + 20);
        }

        repaint();
    }

    private HoverButton getJButtonOK() {
        if (jButtonOK == null) {
            jButtonOK = new HoverButton("", ImageManagerImpl.instance.getDlgAcceptButtonImage(),
                    ImageManagerImpl.instance.getDlgActiveAcceptButtonImage(),
                    ImageManagerImpl.instance.getDlgAcceptButtonImage(),
                    new Rectangle(60, 60));
            int w = getDlgParams().rect.width - 75;
            int h = getDlgParams().rect.height - 90;
            jButtonOK.setBounds(new Rectangle(w / 2 - 40, h - 50, 60, 60));
            jButtonOK.setToolTipText("Ok");

            jButtonOK.setObserver(() -> DialogManager.getManager(gameId).fadeOut((DialogContainer) getParent()));
        }
        return jButtonOK;
    }

    private HoverButton getJButtonPrevPage() {
        if (jButtonPrevPage == null) {
            jButtonPrevPage = new HoverButton("", ImageManagerImpl.instance.getDlgPrevButtonImage(),
                    ImageManagerImpl.instance.getDlgActivePrevButtonImage(),
                    ImageManagerImpl.instance.getDlgPrevButtonImage(),
                    new Rectangle(60, 60));
            int w = getDlgParams().rect.width - 75;
            int h = getDlgParams().rect.height - 90;
            jButtonPrevPage.setBounds(new Rectangle(w / 2 - 125, h - 50, 60, 60));
            jButtonPrevPage.setVisible(false);

            jButtonPrevPage.setObserver(() -> {
                if (page == 1) {
                    return;
                }

                AudioManager.playPrevPage();

                page--;
                getJButtonPrevPage().setVisible(false);
                getJButtonOK().setVisible(false);
                getJButtonNextPage().setVisible(false);
                revalidate();
                displayCards(params.getCards(), params.gameId, params.bigCard);
                if (page != 1) {
                    getJButtonPrevPage().setVisible(true);
                }
                getJButtonOK().setVisible(true);
                getJButtonNextPage().setVisible(true);
            });
        }
        return jButtonPrevPage;
    }

    /**
     * This method initializes jButtonNextPage
     *
     * @return javax.swing.JButton
     */
    private HoverButton getJButtonNextPage() {
        if (jButtonNextPage == null) {
            jButtonNextPage = new HoverButton("", ImageManagerImpl.instance.getDlgNextButtonImage(),
                    ImageManagerImpl.instance.getDlgActiveNextButtonImage(),
                    ImageManagerImpl.instance.getDlgNextButtonImage(),
                    new Rectangle(60, 60));
            int w = getDlgParams().rect.width - 75;
            int h = getDlgParams().rect.height - 90;
            jButtonNextPage.setBounds(new Rectangle(w / 2 + 45, h - 50, 60, 60));

            if (maxPages > 1) {
                jButtonNextPage.setVisible(true);
            } else {
                jButtonNextPage.setVisible(false);
            }

            jButtonNextPage.setObserver(new Command() {
                private static final long serialVersionUID = -3174360416099554104L;

                public void execute() {
                    if (page == maxPages) {
                        return;
                    }

                    AudioManager.playNextPage();

                    page++;
                    getJButtonPrevPage().setVisible(false);
                    getJButtonOK().setVisible(false);
                    getJButtonNextPage().setVisible(false);
                    revalidate();
                    displayCards(params.getCards(), params.gameId, params.bigCard);
                    getJButtonPrevPage().setVisible(true);
                    getJButtonOK().setVisible(true);
                    if (page != maxPages) {
                        getJButtonNextPage().setVisible(true);
                    }
                }
            });
        }
        return jButtonNextPage;
    }

    /**
     * This method initializes jButtonSort
     *
     * @return javax.swing.JButton
     */
    private JButton getJButtonSort() {
        if (jButtonSort == null) {
            jButtonSort = new JButton();
            int w = getDlgParams().rect.width - 75;
            int h = getDlgParams().rect.height - 75;
            jButtonSort.setBounds(new Rectangle(w / 2 + 150, h - 30, 78, 22));
            jButtonSort.setText("Sort");
            jButtonSort.setVisible(false);
            if (maxPages == 1) {
                jButtonSort.setVisible(false);
            }

            /*
            jButtonSort.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cards.sort(new Comparator<CardBean>() {
                        public int compare(CardBean c1, CardBean c2) {
                            return c1.toString().compareTo(c2.toString());
                        }
                    });
                    GameManager.getManager().resetChosenCards();
                    jButtonSort.setVisible(false);
                    if (page > 1) {
                        page = 2;
                        displayCards();
                    }
                    page = 1;
                    displayCards();
                }
            });
            */
        }
        return jButtonSort;
    }

    /**
     * This method initializes jButtonCancel
     *
     * @return javax.swing.JButton
     */
    private HoverButton getJButtonCancel() {
        if (jButtonCancel == null) {
            jButtonCancel = new HoverButton("", ImageManagerImpl.instance.getDlgCancelButtonImage(),
                    ImageManagerImpl.instance.getDlgActiveCancelButtonImage(),
                    ImageManagerImpl.instance.getDlgCancelButtonImage(),
                    new Rectangle(60, 60));
            int w = getDlgParams().rect.width - 75;
            int h = getDlgParams().rect.height - 90;
            jButtonCancel.setBounds(new Rectangle(w / 2 + 150, h - 50, 60, 60));
            jButtonCancel.setToolTipText("Cancel");
            jButtonCancel.setVisible(isOptional);
            if (!isCancelStopsPlaying) {
                jButtonCancel.setToolTipText("Done (enough)");
                jButtonCancel.setBounds(new Rectangle(w / 2 + 150, h - 50, 60, 60));
            }

            jButtonCancel.setObserver(new Command() {
                private static final long serialVersionUID = -567322540616089486L;

                public void execute() {
                    DialogManager.getManager(gameId).fadeOut((DialogContainer) getParent());
                    /*
                    try {
                        ConnectionManager.sendAddChosenCard(null);
                    } catch (RemoteException re) {
                        re.printStackTrace();
                    }
                    */
                }
            });
        }
        return jButtonCancel;
    }

    /*
    public void turnCardBorderOff(CardBean card) {
        for (int i = 0; i < getComponentCount(); i++) {
            Object o = getComponent(i);
            if (o instanceof MWCardImpl) {
                CardBean c = ((MWCardImpl) o).getCard();
                if (c.equals(card)) {
                    ((MWCardImpl) o).setBordered(false);
                    return;
                }
            }
        }
    }*/

    public void changeTitle(String cardName) {
        jTitle.setText("Choose a card (by double-click), chosen card: " + cardName);
    }

}
