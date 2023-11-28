package mage.client.dialog;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import mage.abilities.hint.HintUtils;
import mage.client.cards.BigCard;
import mage.client.components.MageDesktopIconifySupport;
import mage.client.util.GUISizeHelper;
import mage.client.util.SettingsManager;
import mage.client.util.gui.GuiDisplayUtil;
import mage.util.GameLog;
import mage.util.RandomUtil;
import mage.view.CardView;
import mage.view.EmblemView;
import mage.view.GameView;
import mage.view.PlayerView;
import org.apache.log4j.Logger;
import org.mage.card.arcane.ManaSymbols;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

/**
 * Game GUI: collect card hints from all visible game's cards
 *
 * @author JayDi85
 */
public class CardHintsHelperDialog extends MageDialog implements MageDesktopIconifySupport {

    private static final Logger logger = Logger.getLogger(CardHintsHelperDialog.class);

    public static final int GUI_MAX_CARD_HINTS_DIALOGS_PER_GAME = 5; // warning, GUI has a bad performance on too much windows render

    private static final String WINDOW_TITLE = "Card hints helper";
    private static final String FILTER_ALL = "ALL";
    private static final String SEARCH_EMPTY_TEXT = "search";
    private static final String SEARCH_TOOLTIP = "Filter hints by any words in player, card, id or hint";
    private static final String EMPTY_HINTS_WARNING = "<br>Game not started or nothing to show";

    private static final int MAX_CARD_IDS_PER_HINT = 3; // for group by hints

    private boolean positioned;

    private GameView lastGameView = null;
    private final List<CardHintInfo> lastHints = new ArrayList<>();

    private String currentFilter = FILTER_ALL;
    private CardHintGroupBy currentGroup = CardHintGroupBy.GROUP_BY_HINTS;
    private String currentSearch = "";

    private static class CardHintInfo {
        final PlayerView player;
        final String zone;
        final CardView card;
        final List<String> hints;
        String searchBase;
        String searchHints;

        public CardHintInfo(PlayerView player, String zone, CardView card) {
            this.player = player; // can be null for watcher player
            this.zone = zone;
            this.card = card;
            this.hints  = new ArrayList<>();

            // additional data
            if (this.card != null) {
                List<String> newHints = parseCardHints(this.card.getRules());
                if (newHints != null) {
                    this.hints.addAll(newHints);
                }
            }
            this.searchBase = (player == null ? "" : player.getName())
                    + "@" + zone
                    + "@" + (card == null ? "" : card.getIdName());
            this.searchHints = String.join("@", this.hints);

            // searching by lower case
            this.searchBase = this.searchBase.toLowerCase(Locale.ENGLISH);
            this.searchHints = this.searchHints.toLowerCase(Locale.ENGLISH);
        }

        public boolean containsText(List<String> searches) {
            return searches.stream().anyMatch(s -> this.searchBase.contains(s) || this.searchHints.contains(s));
        }
    }

    private enum CardHintGroupBy {
        GROUP_BY_HINTS("Hints"),
        GROUP_BY_CARDS("Cards");

        private final String name;

        CardHintGroupBy(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public CardHintsHelperDialog() {
        this.positioned = false;
        initComponents();

        // TODO: there are draw artifacts at the last symbol like selection
        this.hintsView.enableHyperlinksAndCardPopups(); // enable cards popup
        Color backColor = Color.gray;
        this.setOpaque(true);
        this.setBackground(backColor);
        this.hintsView.setExtBackgroundColor(backColor);
        this.hintsView.setSelectionColor(Color.gray);
        this.scrollView.setOpaque(true);
        this.scrollView.setBackground(backColor);
        this.scrollView.setViewportBorder(null);
        this.scrollView.getViewport().setOpaque(true);
        this.scrollView.getViewport().setBackground(backColor);

        this.setModal(false);

        this.setFrameIcon(new ImageIcon(ImageManagerImpl.instance.getLookedAtImage()));
        this.setClosable(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // incremental search support
        this.search.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                onSearchStart();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onSearchStart();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onSearchStart();
            }
        });

        updateTitle();
        changeGUISize();
    }

    private void updateTitle() {
        // dynamic title
        List<String> settings = new ArrayList<>();

        // from filter
        if (!Objects.equals(this.currentFilter, FILTER_ALL)) {
            settings.add(this.currentFilter);
        }

        // from group
        settings.add(this.currentGroup.toString());

        // from search
        if (this.currentSearch.length() > 0 && !this.currentSearch.equals(SEARCH_EMPTY_TEXT)) {
            settings.add(this.currentSearch);
        }

        String newTitle = String.format("%s [%s]", WINDOW_TITLE, String.join(", ", settings));
        this.setTitle(newTitle);
        this.setTitelBarToolTip(newTitle);
    }

    public void cleanUp() {
        // clean inner objects/components here
        this.lastGameView = null;
        this.lastHints.clear();
    }

    public void setGameData(GameView gameView, UUID gameId, BigCard bigCard) {
        // one time init on open window
        this.hintsView.setGameData(gameId, bigCard);

        // prepare gui filter
        List<String> filters = new ArrayList<>();
        filters.add(FILTER_ALL);
        // me
        gameView.getPlayers()
                .stream()
                .filter(PlayerView::getControlled)
                .map(PlayerView::getName)
                .findFirst()
                .ifPresent(filters::add);
        // other players
        filters.addAll(gameView.getPlayers()
                .stream()
                .filter(p -> !p.getControlled())
                .map(PlayerView::getName)
                .collect(Collectors.toList()));
        this.comboFilterBy.setModel(new DefaultComboBoxModel<>(filters.toArray(new String[0])));
        this.comboFilterBy.addActionListener(evt -> {
            this.currentFilter = (String) this.comboFilterBy.getSelectedItem();
            updateHints();
        });

        // prepare gui group
        this.comboGroupBy.setModel(new DefaultComboBoxModel(CardHintGroupBy.values()));
        this.comboGroupBy.addActionListener(evt -> {
            this.currentGroup = (CardHintGroupBy) this.comboGroupBy.getSelectedItem();
            updateHints();
        });
    }

    public void loadHints(GameView newGameView) {
        if (newGameView == null) {
            return;
        }
        if (this.lastGameView != newGameView) {
            this.lastGameView = newGameView;
        }

        // collect full hints data
        this.lastHints.clear();

        // player can be null on watcher
        PlayerView currentPlayer = this.lastGameView.getPlayers()
                .stream()
                .filter(PlayerView::getControlled)
                .findFirst()
                .orElse(null);

        // hand
        this.lastGameView.getMyHand().values().forEach(card -> {
            this.lastHints.add(new CardHintInfo(currentPlayer, "hand", card));
        });

        // stack
        this.lastGameView.getStack().values().forEach(card -> {
            this.lastHints.add(new CardHintInfo(currentPlayer, "stack", card));
        });

        // TODO: add support of revealed, view, player-top and other non CardView?

        // per player
        for (PlayerView player : this.lastGameView.getPlayers()) {
            // battlefield
            player.getBattlefield().values().forEach(card -> {
                this.lastHints.add(new CardHintInfo(player, "battlefield", card));
            });

            // commander
            player.getCommandObjectList().forEach(object -> {
                // TODO: add support of emblem, dungeon and other non CardView?
                if (object instanceof CardView) {
                    this.lastHints.add(new CardHintInfo(player, "command", (CardView) object));
                } else if (object instanceof EmblemView) {
                    //((EmblemView) object).getRules()
                }
            });

            // graveyard
            player.getGraveyard().values().forEach(card -> {
                this.lastHints.add(new CardHintInfo(player, "graveyard", card));
            });

            // exile
            player.getExile().values().forEach(card -> {
                this.lastHints.add(new CardHintInfo(player, "exile", card));
            });
        }

        // keep cards with hints only
        this.lastHints.removeIf(info -> !info.card.getRules().contains(HintUtils.HINT_START_MARK));

        updateHints();
    }

    public void updateHints() {
        // use already prepared data from loadHints

        List<CardHintInfo> filteredCards = new ArrayList<>(this.lastHints);

        // apply player filter
        if (!this.currentFilter.equals(FILTER_ALL)) {
            filteredCards.removeIf(info -> info.player == null || !info.player.getName().equals(this.currentFilter));
        }

        // apply search filter
        if (!this.currentSearch.isEmpty()) {
            List<String> needSearches = new ArrayList<>(Arrays.asList(this.currentSearch.toLowerCase(Locale.ENGLISH).trim().split(" ")));
            filteredCards.removeIf(info -> !(info.containsText(needSearches)));
        }

        // apply group type
        List<String> renderData = new ArrayList<>();
        switch (this.currentGroup) {
            // data must be sorted and grouped already in prepared code

            default:
            case GROUP_BY_HINTS: {
                // player -> hint + cards
                Map<String, List<CardHintInfo>> groupsByZone = prepareGroupByPlayerAndZones(filteredCards, true, false);
                groupsByZone.forEach((group, groupCards) -> {
                    if (groupCards.isEmpty()) {
                        return;
                    }
                    if (!renderData.isEmpty()) {
                        renderData.add("<br>");
                    }

                    // header: player
                    CardHintInfo sampleData = groupCards.get(0);
                    renderData.add(String.format("<b>%s</b>:",
                            GameLog.getColoredPlayerName(sampleData.player == null ? "watcher" : sampleData.player.getName())
                    ));

                    // data: unique hints
                    Map<String, List<CardHintInfo>> groupByHints = prepareGroupByHints(groupCards);

                    groupByHints.forEach((groupByHint, groupByHintCards) -> {
                        if (groupByHintCards.isEmpty()) {
                            return;
                        }

                        // hint
                        String renderLine = groupByHint;

                        // data: cards list like [123], [456], [789] and 2 other
                        String cardNames = groupByHintCards
                                .stream()
                                .limit(MAX_CARD_IDS_PER_HINT)
                                .map(info -> {
                                    // workable card hints
                                    return GameLog.getColoredObjectIdName(
                                            info.card.getColor(),
                                            info.card.getId(),
                                            String.format("[%s]", info.card.getId().toString().substring(0, 3)),
                                            "",
                                            info.card.getName()
                                    );
                                })
                                .collect(Collectors.joining(", "));
                        if (groupByHintCards.size() > MAX_CARD_IDS_PER_HINT) {
                            cardNames += String.format(" and %d other", groupByHintCards.size() - MAX_CARD_IDS_PER_HINT);
                        }
                        renderLine += " (" + cardNames + ")";

                        renderData.add(renderLine);
                    });
                });
                break;
            }

            case GROUP_BY_CARDS: {
                // player + zone -> card + hint
                Map<String, List<CardHintInfo>> groupsByZone = prepareGroupByPlayerAndZones(filteredCards, true, true);
                groupsByZone.forEach((group, groupCards) -> {
                    if (groupCards.isEmpty()) {
                        return;
                    }
                    if (!renderData.isEmpty()) {
                        renderData.add("<br>");
                    }

                    // header: player/zone
                    CardHintInfo sampleData = groupCards.get(0);
                    renderData.add(String.format("<b>%s - %s</b>:",
                            GameLog.getColoredPlayerName(sampleData.player == null ? "watcher" : sampleData.player.getName()),
                            sampleData.zone
                    ));

                    // data: cards list
                    groupCards.forEach(info -> {
                        List<String> hints = parseCardHints(info.card.getRules());
                        if (hints != null) {
                            String cardName = GameLog.getColoredObjectIdName(
                                    info.card.getColor(),
                                    info.card.getId(),
                                    info.card.getName(),
                                    String.format("[%s]", info.card.getId().toString().substring(0, 3)),
                                    null
                            );
                            renderData.addAll(hints.stream().map(s -> cardName + ": " + s).collect(Collectors.toList()));
                        }
                    });
                });
                break;
            }
        }

        // empty
        if (renderData.isEmpty()) {
            renderData.add(EMPTY_HINTS_WARNING);
        }

        // final render
        String renderFinal = String.join("<br>", renderData);
        // keep scrolls position between updates
        int oldPos = this.scrollView.getVerticalScrollBar().getValue();
        this.hintsView.setText("<html>" + ManaSymbols.replaceSymbolsWithHTML(renderFinal, ManaSymbols.Type.CHAT));
        SwingUtilities.invokeLater(() -> {
            this.scrollView.getVerticalScrollBar().setValue(oldPos);
        });

        updateTitle();
        showAndPositionWindow();
    }

    private Map<String, List<CardHintInfo>> prepareGroupByPlayerAndZones(
            List<CardHintInfo> currentHints,
            boolean groupByPlayer,
            boolean groupByZone
    ) {
        if (!groupByPlayer && !groupByZone) {
            throw new IllegalArgumentException("Wrong code usage: must use minimum one group param");
        }

        Map<String, List<CardHintInfo>> res = new LinkedHashMap<>();
        if (currentHints.isEmpty()) {
            return res;
        }

        String lastGroupName = null;
        List<CardHintInfo> lastGroupCards = null;
        for (CardHintInfo info : currentHints) {
            String currentGroupName = "";
            if (groupByPlayer) {
                currentGroupName += "@" + (info.player == null ? "null" : info.player.getName());
            }
            if (groupByZone) {
                currentGroupName += "@" + info.zone;
            }

            if (lastGroupCards == null || !lastGroupName.equals(currentGroupName)) {
                lastGroupName = currentGroupName;
                lastGroupCards = new ArrayList<>();
                res.put(currentGroupName, lastGroupCards);
            }
            lastGroupCards.add(info);
        }

        // sort cards by card name inside
        res.forEach((zone, infos) -> {
            infos.sort(Comparator.comparing(o -> o.card.getName()));
        });

        return res;
    }

    private Map<String, List<CardHintInfo>> prepareGroupByHints(List<CardHintInfo> currentHints) {
        Map<String, List<CardHintInfo>> res = new LinkedHashMap<>();
        if (currentHints.isEmpty()) {
            return res;
        }

        // unique and sorted hints
        List<String> allPossibleHints = currentHints
                .stream()
                .map(info -> parseCardHints(info.card.getRules()))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        allPossibleHints.forEach(currentHintName -> {
            List<CardHintInfo> cards = res.getOrDefault(currentHintName, null);
            if (cards == null) {
                cards = new ArrayList<>();
                res.put(currentHintName, cards);
            }

            for (CardHintInfo info : currentHints) {
                if (info.card.getRules().contains(currentHintName)) {
                    cards.add(info);
                }
            }
        });

        // sort by card id (cause it show id instead name)
        res.forEach((hint, cards) -> {
            cards.sort(Comparator.comparing(o -> o.card.getId()));
        });

        return res;
    }

    private static List<String> parseCardHints(List<String> rules) {
        if (rules.isEmpty() || !rules.contains(HintUtils.HINT_START_MARK)) {
            return null;
        }
        List<String> hints = new ArrayList<>();
        boolean started = false;
        for (String rule : rules) {
            if (rule.equals(HintUtils.HINT_START_MARK)) {
                started = true;
                continue;
            }
            if (started) {
                hints.add(rule);
            }
        }
        return hints;
    }

    @Override
    public void changeGUISize() {
        setGUISize(GUISizeHelper.chatFont);
        this.validate();
        this.repaint();
    }

    private void setGUISize(Font font) {
        this.hintsView.setFont(font);
        this.scrollView.setFont(font);
        this.scrollView.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
        this.scrollView.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));
    }

    @Override
    public void show() {
        super.show();

        // auto-position on first usage
        if (positioned) {
            showAndPositionWindow();
        }
    }

    private void showAndPositionWindow() {
        SwingUtilities.invokeLater(() -> {
            int width = CardHintsHelperDialog.this.getWidth();
            int height = CardHintsHelperDialog.this.getHeight();
            if (width > 0 && height > 0) {
                Point centered = SettingsManager.instance.getComponentPosition(width, height);
                if (!positioned) {
                    // starting position
                    // little randomize to see multiple opened windows
                    int xPos = centered.x / 2 + RandomUtil.nextInt(50);
                    int yPos = centered.y / 2 + RandomUtil.nextInt(50);
                    CardHintsHelperDialog.this.setLocation(xPos, yPos);
                    show();
                    positioned = true;
                }
                GuiDisplayUtil.keepComponentInsideFrame(centered.x, centered.y, CardHintsHelperDialog.this);
            }

            // workaround to fix draw artifacts
            //CardHintsHelperDialog.this.validate();
            //CardHintsHelperDialog.this.repaint();
        });
    }

    private void onSearchFocused() {
        // fast select
        if (SEARCH_EMPTY_TEXT.equals(search.getText())) {
            search.setText("");
        } else if (search.getText().length() > 0) {
            search.selectAll();
        }
    }
    
    private void onSearchStart() {
        String newSearch = SEARCH_EMPTY_TEXT.equals(search.getText()) ? "" : search.getText();
        if (!this.currentSearch.equals(newSearch)) {
            this.currentSearch = newSearch;
            updateHints();
        }
    }

    private void onSearchClear() {
        this.search.setText(SEARCH_EMPTY_TEXT);
        this.currentSearch = "";
        updateHints();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollView = new javax.swing.JScrollPane();
        hintsView = new mage.client.components.ColorPane();
        commands = new javax.swing.JPanel();
        comboFilterBy = new javax.swing.JComboBox<>();
        comboGroupBy = new javax.swing.JComboBox<>();
        searchPanel = new javax.swing.JPanel();
        search = new javax.swing.JTextField();
        searchClear = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Card hints helper");
        setMinimumSize(new java.awt.Dimension(200, 100));
        setPreferredSize(new java.awt.Dimension(400, 300));
        setTitelBarToolTip("<Not Set>");
        getContentPane().setLayout(new java.awt.BorderLayout());

        scrollView.setBorder(null);
        scrollView.setOpaque(true);

        hintsView.setEditable(false);
        hintsView.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        hintsView.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        hintsView.setText("<html> test text");
        hintsView.setFocusable(false);
        hintsView.setOpaque(true);
        scrollView.setViewportView(hintsView);

        getContentPane().add(scrollView, java.awt.BorderLayout.CENTER);

        commands.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        commands.setLayout(new java.awt.GridLayout(1, 3, 5, 5));

        comboFilterBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        commands.add(comboFilterBy);

        comboGroupBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        commands.add(comboGroupBy);

        searchPanel.setLayout(new java.awt.BorderLayout());

        search.setText(SEARCH_EMPTY_TEXT);
        search.setToolTipText(SEARCH_TOOLTIP);
        search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchFocusGained(evt);
            }
        });
        searchPanel.add(search, java.awt.BorderLayout.CENTER);

        searchClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/sideboard_out.png"))); // NOI18N
        searchClear.setToolTipText("Clear search field");
        searchClear.setPreferredSize(new java.awt.Dimension(23, 23));
        searchClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchClearActionPerformed(evt);
            }
        });
        searchPanel.add(searchClear, java.awt.BorderLayout.EAST);

        commands.add(searchPanel);

        getContentPane().add(commands, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusGained
        onSearchFocused();
    }//GEN-LAST:event_searchFocusGained

    private void searchClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchClearActionPerformed
        onSearchClear();
    }//GEN-LAST:event_searchClearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboFilterBy;
    private javax.swing.JComboBox<String> comboGroupBy;
    private javax.swing.JPanel commands;
    private mage.client.components.ColorPane hintsView;
    private javax.swing.JScrollPane scrollView;
    private javax.swing.JTextField search;
    private javax.swing.JButton searchClear;
    private javax.swing.JPanel searchPanel;
    // End of variables declaration//GEN-END:variables

}
