
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ZurTheEnchanterTest extends CardTestPlayerBase {

    /**
     * Zur the Enchanter's ability + shroud
     *
     * You can reproduce this by attacking with a Zur the Enchanter that has
     * shroud (Lightning Greaves, Diplomatic Immunity, Greater Auramancy + an
     * aura on him, etc.) and when his ability triggers searching for an aura
     * (in this case, Empyrial Armor) and trying to attach it to Zur himself.
     * The game won't allow you to attach it to him, even though it should,
     * since the enchantment is put onto the battlefield and not cast, hence, no
     * targeting is done. The rulings page for Zur itself say it so on Gatherer:
     *
     * Shroud shouldn't stop Empyrial Armor from attaching to Zur, only
     * something like protection from white, for example, would do that.
     */
    @Test
    public void testAuraToBattlefieldDoesNotTarget() {
        // Flying
        // Whenever Zur the Enchanter attacks, you may search your library for an enchantment card
        // with converted mana cost 3 or less and put it onto the battlefield. If you do, shuffle your library.
        addCard(Zone.BATTLEFIELD, playerB, "Zur the Enchanter"); // 1/4

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Enchant creature
        // Shroud (This permanent can't be the target of spells or abilities.)
        // Enchanted creature has shroud.
        addCard(Zone.HAND, playerB, "Diplomatic Immunity"); // {1}{U}
        // Enchant creature
        // Enchanted creature gets +1/+1 for each card in your hand.
        addCard(Zone.LIBRARY, playerB, "Empyrial Armor", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Diplomatic Immunity", "Zur the Enchanter");

        attack(2, playerB, "Zur the Enchanter");
        // setChoice(playerB, "Empyrial Armor"); // No need for this choice since the AI will autosearch and autopick this
        setChoice(playerB, "Zur the Enchanter");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Diplomatic Immunity", 1);
        assertPermanentCount(playerB, "Empyrial Armor", 1);
        assertPowerToughness(playerB, "Zur the Enchanter", 2, 5);
    }

}
