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

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DecDeckImporter extends DeckImporter {

    @Override
    protected void readLine(String line, DeckCardLists deckList) {
        if (line.length() == 0 || line.startsWith("//")) {
            return;
        }

        boolean sideboard = false;
        if (line.startsWith("SB:")) {
            line = line.substring(3).trim();
            sideboard = true;
        }

        int delim = line.indexOf(' ');
        String lineNum = line.substring(0, delim).trim();
        String lineName = line.substring(delim).trim();
        try {
            int num = Integer.parseInt(lineNum);
            CardInfo cardInfo = CardRepository.instance.findPreferedCoreExpansionCard(lineName, true);
            if (cardInfo == null) {
                sbMessage.append("Could not find card: '").append(lineName).append("' at line ").append(lineCount).append("\n");
            } else {
                for (int i = 0; i < num; i++) {
                    if (!sideboard) {
                        deckList.getCards().add(new DeckCardInfo(cardInfo.getName(), cardInfo.getCardNumber(), cardInfo.getSetCode()));
                    } else {
                        deckList.getSideboard().add(new DeckCardInfo(cardInfo.getName(), cardInfo.getCardNumber(), cardInfo.getSetCode()));
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            sbMessage.append("Invalid number: ").append(lineNum).append(" at line ").append(lineCount).append("\n");
        }
    }

}
