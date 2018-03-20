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
import java.util.Locale;
import java.util.Scanner;
import mage.cards.decks.DeckCardLists;

/**
 *
 * @author North
 */
public final class DeckImporterUtil {

    public static final String[] SIDEBOARD_MARKS = new String[]{"//sideboard", "sb: "};

    public static boolean haveSideboardSection(String file) {
        // search for sideboard section:
        // or //sideboard
        // or SB: 1 card name -- special deckstats.net

        File f = new File(file);
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim().toLowerCase(Locale.ENGLISH);

                for (String mark : SIDEBOARD_MARKS) {
                    if (line.startsWith(mark)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            // ignore error, deckimporter will process it
        }

        // not found
        return false;
    }

    public static DeckImporter getDeckImporter(String file) {
        if (file.toLowerCase(Locale.ENGLISH).endsWith("dec")) {
            return new DecDeckImporter();
        } else if (file.toLowerCase(Locale.ENGLISH).endsWith("mwdeck")) {
            return new MWSDeckImporter();
        } else if (file.toLowerCase(Locale.ENGLISH).endsWith("txt")) {
            return new TxtDeckImporter(haveSideboardSection(file));
        } else if (file.toLowerCase(Locale.ENGLISH).endsWith("dck")) {
            return new DckDeckImporter();
        } else if (file.toLowerCase(Locale.ENGLISH).endsWith("dek")) {
            return new DekDeckImporter();
        } else {
            return null;
        }
    }

    public static DeckCardLists importDeck(String file, StringBuilder errorMessages) {
        DeckImporter deckImporter = getDeckImporter(file);
        if (deckImporter != null) {
            return deckImporter.importDeck(file, errorMessages);
        } else {
            return new DeckCardLists();
        }
    }

    public static DeckCardLists importDeck(String file) {
        return importDeck(file, null);
    }
}
