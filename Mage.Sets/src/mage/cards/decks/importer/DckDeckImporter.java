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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mage.cards.ExpansionSet;
import mage.cards.decks.DeckCardLists;
import mage.sets.Sets;

/**
 *
 * @author North
 */
public class DckDeckImporter extends DeckImporterImpl {

    private static final Pattern pattern = Pattern.compile("(SB:)?\\s*(\\d*)\\s*\\[([a-zA-Z0-9]{3}):(\\d*)\\].*");

    @Override
    protected void readLine(String line, DeckCardLists deckList) {

        if (line.length() == 0 || line.startsWith("#")) {
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
            int cardNum = Integer.parseInt(m.group(4));
            ExpansionSet set = Sets.findSet(setCode);
            String card = null;
            if (set != null) {
                card = set.findCardName(cardNum);
            }
            if (card != null) {
                for (int i = 0; i < count; i++) {
                    if (!sideboard) {
                        deckList.getCards().add(card);
                    } else {
                        deckList.getSideboard().add(card);
                    }
                }
            } else {
                sbMessage.append("Could not find card '").append("' at line ").append(lineCount).append(": ").append(line).append("\n");
            }
        } else if (line.startsWith("NAME:")) {
            deckList.setName(line.substring(5, line.length()));
        } else if (line.startsWith("AUTHOR:")) {
            deckList.setAuthor(line.substring(7, line.length()));
        }
    }
}
