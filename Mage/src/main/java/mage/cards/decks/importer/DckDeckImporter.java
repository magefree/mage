
package mage.cards.decks.importer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLayout;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

/**
 *
 * @author North
 */
public class DckDeckImporter extends DeckImporter {

    private static final Pattern pattern = Pattern.compile("(SB:)?\\s*(\\d*)\\s*\\[([^]:]+):([^]:]+)\\]\\s*(.*)\\s*$");

    private static final Pattern layoutPattern = Pattern.compile("LAYOUT (\\w+):\\((\\d+),(\\d+)\\)([^|]+)\\|(.*)$");

    private static final Pattern layoutStackPattern = Pattern.compile("\\(([^)]*)\\)");

    private static final Pattern layoutStackEntryPattern = Pattern.compile("\\[(\\w+[^:]*\\w*):(\\d+\\w*)]"); // [JR:64ab],[JR:64],[MPSAK1321:43],[MPSAKH:9],[MPS123-AKH:32],[MPS-13AKH:30],[MPS-AKH:49],[MPS-AKH:11]

    @Override
    protected void readLine(String line, DeckCardLists deckList) {

        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }

        Matcher m = pattern.matcher(line);
        if (m.matches()) {
            boolean sideboard = false;
            if ("SB:".equals(m.group(1))) {
                sideboard = true;
            }
            int count = Integer.parseInt(m.group(2));
            String setCode = m.group(3);
            String cardNum = m.group(4);
            String cardName = m.group(5);

            cardNum = cardNum == null ? "" : cardNum.trim();
            setCode = setCode == null ? "" : setCode.trim();
            cardName = cardName == null ? "" : cardName.trim();

            // search priority: set/code -> name
            // with bulletproof on card number or name changes

            DeckCardInfo deckCardInfo = null;

            // search by number
            CardInfo foundedCard = CardRepository.instance.findCard(setCode, cardNum);
            boolean wasOutdated = false;
            if ((foundedCard != null) && !foundedCard.getName().equals(cardName)){
                sbMessage.append("Line ").append(lineCount).append(": ").append("founded outdated card number or name, will try to replace: ").append(line).append('\n');
                wasOutdated = true;
                foundedCard = null;
            }

            // search by name
            if (foundedCard == null) {
                if(!wasOutdated){
                    sbMessage.append("Line ").append(lineCount).append(": ").append("can't find card by number, will try ro replace: ").append(line).append('\n');
                }

                if (!cardName.equals("")) {
                    foundedCard = CardRepository.instance.findPreferedCoreExpansionCard(cardName, false, setCode);
                }

                if (foundedCard != null) {
                    sbMessage.append("Line ").append(lineCount).append(": ")
                            .append("replaced to [").append(foundedCard.getSetCode()).append(":").append(foundedCard.getCardNumberAsInt()).append("] ")
                            .append(foundedCard.getName()).append('\n');
                }else{
                    sbMessage.append("Line ").append(lineCount).append(": ").append("ERROR, can't find card [").append(cardName).append("]").append('\n');
                }
            }

            if (foundedCard != null) {
                deckCardInfo = new DeckCardInfo(foundedCard.getName(), foundedCard.getCardNumber(), foundedCard.getSetCode());
            }
            if (deckCardInfo != null) {
                for (int i = 0; i < count; i++) {
                    if (!sideboard) {
                        deckList.getCards().add(deckCardInfo);
                    } else {
                        deckList.getSideboard().add(deckCardInfo);
                    }
                }
            }
        } else if (line.startsWith("NAME:")) {
            deckList.setName(line.substring(5, line.length()));
        } else if (line.startsWith("AUTHOR:")) {
            deckList.setAuthor(line.substring(7, line.length()));
        } else if (line.startsWith("LAYOUT")) {
            Matcher m2 = layoutPattern.matcher(line);
            if (m2.find()) {
                String target = m2.group(1);
                int rows = Integer.parseInt(m2.group(2));
                int cols = Integer.parseInt(m2.group(3));
                String settings = m2.group(4);
                String stackData = m2.group(5);
                Matcher stackMatcher = layoutStackPattern.matcher(stackData);
                //
                List<List<List<DeckCardInfo>>> grid = new ArrayList<>();
                int totalCardCount = 0;
                for (int row = 0; row < rows; ++row) {
                    List<List<DeckCardInfo>> rowData = new ArrayList<>();
                    grid.add(rowData);
                    for (int col = 0; col < cols; ++col) {
                        List<DeckCardInfo> stack = new ArrayList<>();
                        rowData.add(stack);
                        if (stackMatcher.find()) {
                            String thisStackData = stackMatcher.group(1);
                            Matcher stackEntries = layoutStackEntryPattern.matcher(thisStackData);
                            while (stackEntries.find()) {
                                ++totalCardCount;
                                stack.add(new DeckCardInfo("", stackEntries.group(2), stackEntries.group(1)));
                            }
                        } else {
                            sbMessage.append("Missing stack\n.");
                        }
                    }
                }
                //
                DeckCardLayout layout = new DeckCardLayout(grid, settings);
                int expectedCount = 0;
                switch (target) {
                    case "MAIN":
                        deckList.setCardLayout(layout);
                        expectedCount = deckList.getCards().size();
                        break;
                    case "SIDEBOARD":
                        deckList.setSideboardLayout(layout);
                        expectedCount = deckList.getSideboard().size();
                        break;
                    default:
                        sbMessage.append("Bad target `").append(target).append("` for layout.\n");
                        break;
                }
                //
                if (totalCardCount != expectedCount) {
                    sbMessage.append("Layout mismatch: Expected ").append(expectedCount).append(" cards, but got ").append(totalCardCount).append(" in layout `").append(target).append("`\n.");
                }

            } else {
                sbMessage.append("Malformed layout line");
            }
        }
    }
}
