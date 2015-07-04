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
package org.mage.test.cards.triggers;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PossibilityStormTest extends CardTestPlayerBase {

    /**
     * There's currently a bug with Possibility Storm and Zoetic Cavern. The way
     * it's supposed to work is the P. Storm trigger exiles Zoetic Cavern and
     * then uses last known information about the spell to determine the type of
     * card the trigger is looking for(creature in this instance). Instead it's
     * basing the type solely off what's printed on the card. What happened to
     * me earlier was the trigger skipped right over an Emrakul and then
     * revealed a Flooded Strand. I was prompted whether or not I wanted to
     * "cast" Flooded Strand without paying it's cost. Eventually I clicked yes
     * and it produced a Game Error that resulted in rollback. I recreated the
     * error against an AI opponent and copied the code. Can't actually post it
     * because the filter on this site claims it makes my post look too
     * "spammy". Here's a screenshot of it instead(in spoiler tag).
     */
    @Test
    public void TestWithZoeticCavern() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // Whenever a player casts a spell from his or her hand, that player exiles it, then exiles cards from
        // the top of his or her library until he or she exiles a card that shares a card type with it. That
        // player may cast that card without paying its mana cost. Then he or she puts all cards exiled with
        // Possibility Storm on the bottom of his or her library in a random order.
        addCard(Zone.BATTLEFIELD, playerA, "Possibility Storm", 2);

        // {T}: Add {1} to your mana pool.
        // Morph {2}
        addCard(Zone.HAND, playerA, "Zoetic Cavern");

        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion");
        skipInitShuffling();

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zoetic Cavern");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Zoetic Cavern", 0);

        boolean zoeticCavernInLibrary = false;
        for (Card card : playerA.getLibrary().getCards(currentGame)) {
            if (card.getName().equals("Zoetic Cavern")) {
                zoeticCavernInLibrary = true;
            }
        }
        Assert.assertEquals("Zoetic Cavern has to be in the library", true, zoeticCavernInLibrary);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);

    }

    /*
     * Having another Possibility Storm issue(shocking, I know). This time it
     * occurred when trying to finish off my opponent that was at 3 life.
     * I cast an Izzet Charm choosing draw 2 discard 2 for mode,
     * responded to the Possibility Storm trigger with Remand.
     * Remand's trigger revealed a Pact of Negation I chose not to cast, then the trigger for Izzet Charm
     * resolved apparently revealing a Cryptic Command. It automatically moved
     * the spell from exile to my library, apparently because it believed it did
     * not have a target(no spells remaining on the stack). I've seen this
     * happen with Mana Leaks that get revealed with no targets, but Cryptic
     * being modal means it should always be able to be cast. It's worth
     * mentioning that I've revealed Cryptics off triggers with other spells on
     * the stack and properly been asked if I wish to cast them. Thanks!
     */
    @Test
    public void TestWithCrypticCommand() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Whenever a player casts a spell from his or her hand, that player exiles it, then exiles cards from
        // the top of his or her library until he or she exiles a card that shares a card type with it. That
        // player may cast that card without paying its mana cost. Then he or she puts all cards exiled with
        // Possibility Storm on the bottom of his or her library in a random order.
        addCard(Zone.BATTLEFIELD, playerA, "Possibility Storm", 1);

        // Choose one — Counter target noncreature spell unless its controller pays {2};
        // or Izzet Charm deals 2 damage to target creature;
        //  or draw two cards, then discard two cards.
        addCard(Zone.HAND, playerA, "Izzet Charm"); // {U}{R}
        // Counter target spell. If that spell is countered this way, put it into its owner's hand instead of into that player's graveyard.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Remand");

        // Choose two -
        // Counter target spell;
        // or return target permanent to its owner's hand;
        // or tap all creatures your opponents control;
        // or draw a card.
        addCard(Zone.LIBRARY, playerA, "Cryptic Command");
        addCard(Zone.LIBRARY, playerA, "Pact of Negation");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Izzet Charm");
        setModeChoice(playerA, "3");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Remand", "Izzet Charm", "Whenever a player casts");
        setChoice(playerA, "No"); // Don't play  Pact of Negotiation

        setChoice(playerA, "Yes"); // Play Cryptic Command
        setModeChoice(playerA, "3");
        setModeChoice(playerA, "4");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, 0);
        assertGraveyardCount(playerA, "Cryptic Command", 1);

        assertTapped("Silvercoat Lion", true);
        assertHandCount(playerA, 1); // from Cryptic Command Draw

    }
}
