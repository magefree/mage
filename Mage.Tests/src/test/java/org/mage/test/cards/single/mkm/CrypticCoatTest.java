package org.mage.test.cards.single.mkm;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class CrypticCoatTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.CrypticCoat Cryptic Coat} {2}{U}
     * Artifact — Equipment
     * When Cryptic Coat enters the battlefield, cloak the top card of your library, then attach Cryptic Coat to it. (To cloak a card, put it onto the battlefield face down as a 2/2 creature with ward {2}. Turn it face up any time for its mana cost if it’s a creature card.)
     * Equipped creature gets +1/+0 and can’t be blocked.
     * {1}{U}: Return Cryptic Coat to its owner’s hand.
     */
    private static final String coat = "Cryptic Coat";

    @Test
    public void test_CloakCreature() {
        skipInitShuffling();
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, coat);
        addCard(Zone.LIBRARY, playerA, "Ancient Crab");
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, coat);

        checkPT("Cloaked is 3/2", 1, PhaseStep.BEGIN_COMBAT, playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 3, 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", EmptyNames.FACE_DOWN_CREATURE.toString());
        setChoice(playerB, true); // pay for ward
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}{U}: Turn this face-down permanent face up.");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Ancient Crab", 1 + 1, 5);
        assertDamageReceived(playerA, "Ancient Crab", 3);
        assertTappedCount("Mountain", true, 3);
        assertTappedCount("Island", true, 6);
    }
}
