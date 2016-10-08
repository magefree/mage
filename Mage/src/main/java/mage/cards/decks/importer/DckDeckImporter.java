/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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

    private static final Pattern pattern = Pattern.compile("(SB:)?\\s*(\\d*)\\s*\\[([^]:]+):([^]:]+)\\].*");

    private static final Pattern layoutPattern = Pattern.compile("LAYOUT (\\w+):\\((\\d+),(\\d+)\\)([^|]+)\\|(.*)$");

    private static final Pattern layoutStackPattern = Pattern.compile("\\(([^)]*)\\)");

    private static final Pattern layoutStackEntryPattern = Pattern.compile("\\[(\\w+):(\\w+)]");

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

            DeckCardInfo deckCardInfo = null;
            CardInfo cardInfo = CardRepository.instance.findCard(setCode, cardNum);
            if (cardInfo != null) {
                deckCardInfo = new DeckCardInfo(cardInfo.getName(), cardInfo.getCardNumber(), cardInfo.getSetCode());
            }
            if (deckCardInfo != null) {
                for (int i = 0; i < count; i++) {
                    if (!sideboard) {
                        deckList.getCards().add(deckCardInfo);
                    } else {
                        deckList.getSideboard().add(deckCardInfo);
                    }
                }
            } else {
                sbMessage.append("Could not find card '").append("' at line ").append(lineCount).append(": ").append(line).append("\n");
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
