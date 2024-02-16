package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class AuraMovingTest extends CardTestPlayerBase {

    /**
     * I enchanted Bruna with Alexi's Cloak, giving it shroud. Then, it did not
     * allow me to attach any auras with the triggered ability when I attacked
     * with Bruna. Here is the ruling from the judges:
     *
     * I have a bruna, light of alebaster with shroud. can i still attach auras
     * to it by the triggered ability? Can Chandra, Torch of Defiance's +1
     * damage be redirected to a planeswalker? Just asking because it says "each
     * opponent" Yes. If you're not casting an Aura as an Aura spell, you don't
     * need to target. See !303.4f 303.4f. If an Aura is entering the
     * battlefield under a player's control by any means other than by resolving
     * as an Aura spell, and the effect putting it onto the battlefield doesn't
     * specify the object or player the Aura will enchant, that player chooses
     * what it will enchant as the Aura enters the battlefield. The player must
     * choose a legal object or player according to the Aura's enchant ... ...
     * ability and any other applicable effects.
     */
    @Test
    public void testOneAttackerDamage() {

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Flash
        // Enchant creature
        // Enchanted creature has shroud.
        addCard(Zone.HAND, playerB, "Alexi's Cloak", 1); //Enchantment {1}{U}
        // Whenever Bruna, Light of Alabaster attacks or blocks, you may attach to it any number of
        // Auras on the battlefield and you may put onto the battlefield attached to it any number
        // of Aura cards that could enchant it from your graveyard and/or hand.
        addCard(Zone.BATTLEFIELD, playerB, "Bruna, Light of Alabaster", 1); // Creature 5/5 {3}{W}{W}{U}
        // Enchant creature
        //Enchanted creature gets +2/+1.
        addCard(Zone.GRAVEYARD, playerB, "Unholy Strength", 1);
        // Enchant land
        // Enchanted land has ": Target creature can't block this turn."
        addCard(Zone.GRAVEYARD, playerB, "Hostile Realm", 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Alexi's Cloak", "Bruna, Light of Alabaster");
        attack(2, playerB, "Bruna, Light of Alabaster");
        setChoice(playerB, "Yes");
        setChoice(playerB, "Yes");


        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Alexi's Cloak", 1);
        assertPermanentCount(playerB, "Unholy Strength", 1);
        assertPermanentCount(playerB, "Hostile Realm", 0);

        assertGraveyardCount(playerB, "Hostile Realm", 1);

        assertLife(playerA, 13);
    }

}
