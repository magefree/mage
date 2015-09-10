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
package org.mage.test.cards.abilities.keywords;

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
public class ManifestTest extends CardTestPlayerBase {

    /**
     * Tests that ETB triggered abilities did not trigger for manifested cards
     */
    @Test
    public void testETBTriggeredAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Manifest the top card of your library {1}{W}
        addCard(Zone.HAND, playerA, "Soul Summons");

        // Tranquil Cove enters the battlefield tapped.
        // When Tranquil Cove enters the battlefield, you gain 1 life.
        // {T}: Add {W} or {U} to your mana pool.
        addCard(Zone.LIBRARY, playerA, "Tranquil Cove");
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Summons");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // no life gain
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // a facedown creature is on the battlefield
        assertPermanentCount(playerA, "", 1);
        assertPowerToughness(playerA, "", 2, 2);
        // not tapped
        assertTapped("", false);
    }

    /**
     * If Doomwake Giant gets manifested, it's Constellation trigger may not
     * trigger
     */
    @Test
    public void testETBTriggeredAbilities2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Manifest the top card of your library {1}{W}
        addCard(Zone.HAND, playerA, "Soul Summons");

        // Constellation - When Doomwake Giant or another enchantment enters the battlefield
        // under your control, creatures your opponents control get -1/-1 until end of turn.
        addCard(Zone.LIBRARY, playerA, "Doomwake Giant");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Summons");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // no life gain
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // a facedown creature is on the battlefield
        assertPermanentCount(playerA, "", 1);
        assertPowerToughness(playerA, "", 2, 2);
        // PlayerB's Silvercoat Lion should not have get -1/-1/
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
    }

    /**
     * If Doomwake Giant gets manifested, it's Constellation trigger may not
     * trigger
     */
    @Test
    public void testETBTriggeredAbilities3() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Exile target creature. Its controller manifests the top card of his or her library {1}{U}
        addCard(Zone.HAND, playerB, "Reality Shift");

        // Constellation - When Doomwake Giant or another enchantment enters the battlefield
        // under your control, creatures your opponents control get -1/-1 until end of turn.
        addCard(Zone.LIBRARY, playerA, "Doomwake Giant");

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Reality Shift", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // no life gain
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerB, "Reality Shift", 1);
        assertExileCount("Silvercoat Lion", 1);
        // a facedown creature is on the battlefield
        assertPermanentCount(playerA, "", 1);
        assertPowerToughness(playerA, "", 2, 2);
        // PlayerA's Pillarfield Ox should not have get -1/-1/
        assertPermanentCount(playerB, "Pillarfield Ox", 1);
        assertPowerToughness(playerB, "Pillarfield Ox", 2, 4);
    }

    /**
     * If Doomwake Giant gets manifested, it's Constellation trigger may not
     * trigger
     */
    @Test
    public void testNylea() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Exile target creature. Its controller manifests the top card of his or her library {1}{U}
        addCard(Zone.HAND, playerB, "Reality Shift");

        // As long as your devotion to white is less than five, Nylea isn't a creature.
        // <i>(Each {G} in the mana costs of permanents you control counts towards your devotion to green.)</i>
        addCard(Zone.LIBRARY, playerA, "Nylea, God of the Hunt");

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Reality Shift", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // no life gain
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerB, "Reality Shift", 1);
        assertExileCount("Silvercoat Lion", 1);
        // a facedown creature is on the battlefield
        assertPermanentCount(playerA, "", 1);
        assertPowerToughness(playerA, "", 2, 2);

    }

    /*
     Had a Foundry Street Denizen and another creature out.
     Opponent Reality Shift'ed the other creature, manifested card was a red creature. This pumped the foundry street denizen even though it shouldn't.
     */
    @Test
    public void testColorOfManifestedCardDoesNotCount() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Exile target creature. Its controller manifests the top card of his or her library {1}{U}
        addCard(Zone.HAND, playerB, "Reality Shift");

        // Gore Swine {2}{R}
        // 4/1
        addCard(Zone.LIBRARY, playerA, "Gore Swine");

        // Whenever another red creature enters the battlefield under your control, Foundry Street Denizen gets +1/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Foundry Street Denizen");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Reality Shift", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // no life gain
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerB, "Reality Shift", 1);
        assertExileCount("Silvercoat Lion", 1);
        // a facedown creature is on the battlefield
        assertPermanentCount(playerA, "", 1);
        assertPowerToughness(playerA, "", 2, 2);
        assertPowerToughness(playerA, "Foundry Street Denizen", 1, 1);

    }
    /*
     I casted a Silence the Believers on a manifested card. It moved to the exile zone face-down.
     */

    @Test
    public void testCardGetsExiledFaceUp() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        // Exile target creature. Its controller manifests the top card of his or her library {1}{U}
        addCard(Zone.HAND, playerB, "Reality Shift");
        // Silence the Believers - Instant {2}{B}{B}
        // Strive — Silence the Believers costs more to cast for each target beyond the first.
        // Exile any number of target creatures and all Auras attached to them.
        addCard(Zone.HAND, playerB, "Silence the Believers");
        addTarget(playerB, "");
        // Gore Swine {2}{R}
        // 4/1
        addCard(Zone.LIBRARY, playerA, "Gore Swine");

        // Whenever another red creature enters the battlefield under your control, Foundry Street Denizen gets +1/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Reality Shift", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Silence the Believers", "");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // no life gain
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerB, "Reality Shift", 1);
        assertExileCount("Silvercoat Lion", 1);
        assertExileCount("Gore Swine", 1);
        // no facedown creature is on the battlefield
        assertPermanentCount(playerA, "", 0);

        for (Card card : currentGame.getExile().getAllCards(currentGame)) {
            if (card.getName().equals("Gore Swine")) {
                Assert.assertTrue("Gore Swine may not be face down in exile", !card.isFaceDown(currentGame));
            }
        }

    }

    // Qarsi High Priest went to manifest Illusory Gains,
    // but it made me choose a target for gains, then enchanted the card to that creature.
    @Test
    public void testManifestAura() {

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        // {1}{B}, {T}, Sacrifice another creature: Manifest the top card of your library.
        addCard(Zone.BATTLEFIELD, playerB, "Qarsi High Priest", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        addCard(Zone.LIBRARY, playerB, "Illusory Gains", 1);
        addCard(Zone.LIBRARY, playerB, "Mountain", 1);

        skipInitShuffling();

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}{B},{T}, Sacrifice another creature");
        addTarget(playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        // no life gain
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Illusory Gains", 0);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

        // a facedown creature is on the battlefield
        assertPermanentCount(playerB, "", 1);

    }

    // Check if a Megamorph card is manifested and turned face up by their megamorph ability
    // it gets the +1/+1 counter.
    @Test
    public void testManifestMegamorph() {

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 6);
        // {1}{B}, {T}, Sacrifice another creature: Manifest the top card of your library.
        addCard(Zone.BATTLEFIELD, playerB, "Qarsi High Priest", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        // Reach (This creature can block creatures with flying.)
        // Megamorph {5}{G}
        addCard(Zone.LIBRARY, playerB, "Aerie Bowmasters", 1);
        addCard(Zone.LIBRARY, playerB, "Mountain", 1);

        skipInitShuffling();

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}{B},{T}, Sacrifice another creature");
        addTarget(playerB, "Silvercoat Lion");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{5}{G}: Turn");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // no life gain
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

        assertPermanentCount(playerB, "", 0);
        assertPermanentCount(playerB, "Aerie Bowmasters", 1);
        assertPowerToughness(playerB, "Aerie Bowmasters", 4, 5); // 3/4  and the +1/+1 counter from Megamorph

    }

    /**
     * When a Forest came manifested into play my Courser of Kruphix gained me a
     * life.
     *
     */
    @Test
    public void testManifestForest() {

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        // Play with the top card of your library revealed.
        // You may play the top card of your library if it's a land card.
        // Whenever a land enters the battlefield under your control, you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerB, "Courser of Kruphix", 1);

        // {1}{B}, {T}, Sacrifice another creature: Manifest the top card of your library.
        addCard(Zone.BATTLEFIELD, playerB, "Qarsi High Priest", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        addCard(Zone.LIBRARY, playerB, "Forest", 1);

        skipInitShuffling();

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}{B},{T}, Sacrifice another creature");
        addTarget(playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // no life gain
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

        assertPermanentCount(playerB, "", 1);

    }

    /**
     * Whisperwood Elemental - Its sacrifice ability doesn't work..
     *
     */
    @Test
    public void testWhisperwoodElemental() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Seismic Rupture deals 2 damage to each creature without flying.
        addCard(Zone.HAND, playerA, "Seismic Rupture", 1);

        // At the beginning of your end step, manifest the top card of your library.
        // Sacrifice Whisperwood Elemental: Until end of turn, face-up, nontoken creatures you control gain "When this creature dies, manifest the top card of your library."
        addCard(Zone.BATTLEFIELD, playerB, "Whisperwood Elemental", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Sacrifice");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Seismic Rupture");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // no life gain
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Seismic Rupture", 1);
        assertGraveyardCount(playerB, "Whisperwood Elemental", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 2);

        assertPermanentCount(playerB, "", 2);

    }
}
