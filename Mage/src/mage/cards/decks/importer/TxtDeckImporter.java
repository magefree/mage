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

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TxtDeckImporter extends DeckImporter {

    public static final String[] SET_VALUES = new String[] { "lands", "creatures", "planeswalkers","other spells","sideboard cards" };
    public static final Set<String> IGNORE_NAMES = new HashSet<String>(Arrays.asList(SET_VALUES));
    
    private boolean sideboard = false;


    @Override
    protected void readLine(String line, DeckCardLists deckList) {
        if (line.length() == 0 || line.startsWith("//")) {
            return;
        }

        if (line.toLowerCase().startsWith("sideboard")) {
            sideboard = true;
            return;
        }

        int delim = line.indexOf(' ');
        String lineNum = line.substring(0, delim).trim();
        String lineName = line.substring(delim).replace("’","\'").trim();
        if (IGNORE_NAMES.contains(lineName)) {
            return;
        }
        try {
            int num = Integer.parseInt(lineNum);
            List<CardInfo> cards = CardRepository.instance.findCards(lineName);
            if (cards.isEmpty()) {
                sbMessage.append("Could not find card: '").append(lineName).append("' at line ").append(lineCount).append("\n");
            } else {
                // search the card released last with this name
                Date lastReleaseDate = new GregorianCalendar(1900, 1, 1).getTime();
                CardInfo lastReleasedCard = null;
                for (CardInfo cardinfo: cards) {
                    ExpansionInfo set = ExpansionRepository.instance.getSetByCode(cardinfo.getSetCode());
                    if (set != null) {
                        if (lastReleaseDate == null || set.getReleaseDate().after(lastReleaseDate)) {
                            lastReleasedCard = cardinfo;
                            lastReleaseDate = set.getReleaseDate();
                        }
                    }
                }
                if (lastReleasedCard == null) {
                    lastReleasedCard = cards.get(0);
                }
                for (int i = 0; i < num; i++) {
                    if (!sideboard) {
                        deckList.getCards().add(new DeckCardInfo(lastReleasedCard.getName(),lastReleasedCard.getCardNumber(), lastReleasedCard.getSetCode()));
                    } else {
                        deckList.getSideboard().add(new DeckCardInfo(lastReleasedCard.getName(),lastReleasedCard.getCardNumber(), lastReleasedCard.getSetCode()));
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            sbMessage.append("Invalid number: ").append(lineNum).append(" at line ").append(lineCount).append("\n");
        }
    }

}
