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

import java.io.File;
import java.util.Scanner;
import mage.cards.decks.DeckCardLists;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DeckImporter {

    private static final Logger logger = Logger.getLogger(DeckImporter.class);
    protected StringBuilder sbMessage = new StringBuilder();
    protected int lineCount;

    public DeckCardLists importDeck(String file) {
        File f = new File(file);
        DeckCardLists deckList = new DeckCardLists();
        if (!f.exists()) {
            logger.warn("Deckfile " + file + " not found.");
            return deckList;
        }
        lineCount = 0;
        sbMessage.setLength(0);
        try {
            try (Scanner scanner = new Scanner(f)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    lineCount++;
                    readLine(line, deckList);
                }
                if (sbMessage.length() > 0) {
                    logger.fatal(sbMessage);
                }
            } catch (Exception ex) {
                logger.fatal(null, ex);
            }
        } catch (Exception ex) {
            logger.fatal(null, ex);
        }
        return deckList;
    }

    public String getErrors(){
        return sbMessage.toString();
    }

    protected abstract void readLine(String line, DeckCardLists deckList);
}
