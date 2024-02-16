package org.mage.test.cards.single.dmu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.k.KarnsSylex Karn's Sylex}
 * Karn’s Sylex enters the battlefield tapped.
 * Players can’t pay life to cast spells or to activate abilities that aren’t mana abilities.
 * {X}, {T}, Exile Karn’s Sylex: Destroy each nonland permanent with mana value X or less. Activate only as a sorcery.
 *
 * @author Alex-Vasile
 */
public class KarnsSylexTest extends CardTestPlayerBase {
    private static final String karnsSylex = "Karn's Sylex";

    /**
     * Test that it does not allow for Phyrexian mana to be paid with life.
     */
    @Test
    public void blockPhyrexianMana() {
        // {3}{B/P}
        String tezzeretsGambit = "Tezzeret's Gambit";
        addCard(Zone.HAND, playerA, tezzeretsGambit);
        addCard(Zone.BATTLEFIELD, playerA, karnsSylex);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        checkPlayableAbility("Can't pay life to cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + tezzeretsGambit, false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
    }

    /**
     * Blocks things like Bolas's Citadel.
     */
    @Test
    public void blockBolassCitadel() {
        // You may play lands and cast spells from the top of your library.
        // If you cast a spell this way, pay life equal to its mana value rather than pay its mana cost.
        String bolassCitadel = "Bolas's Citadel";
        addCard(Zone.BATTLEFIELD, playerA, bolassCitadel);
        addCard(Zone.BATTLEFIELD, playerA, karnsSylex);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");

        skipInitShuffling();
        checkPlayableAbility("Can't pay life to cast", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    /**
     * Test that it works with mana abilities, e.g. Thran Portal.
     */
    @Test
    public void allowsManaAbilities() {
        addCard(Zone.HAND, playerA, "Thran Portal");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, karnsSylex);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thran Portal");
        setChoice(playerA, "Thran");
        setChoice(playerA, "Mountain");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertLife(playerA, 20 - 1);
        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }
}
