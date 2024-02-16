package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class SpreadingSeasTest extends CardTestPlayerBase {

    /**
     * Played Spreading Seas on opps manland (e.g. Blinkmoth Nexus) . He
     * activated it on response, seas resolves but the manland loses creature
     * type what should not happened.
     * <p>
     * 305.7. If an effect changes a land's subtype to one or more of the basic
     * land types, the land no longer has its old land type. It loses all
     * abilities generated from its rules text and its old land types, and it
     * gains the appropriate mana ability for each new basic land type. Note
     * that this doesn't remove any abilities that were granted to the land by
     * other effects. Changing a land's subtype doesn't add or remove any card
     * types (such as creature) or supertypes (such as basic, legendary, and
     * snow) the land may have. If a land gains one or more land types in
     * addition to its own, it keeps its land types and rules text, and it gains
     * the new land types and mana abilities.
     */
    @Test
    public void testCreatureTypeStays() {
        // Enchant land
        // When Spreading Seas enters the battlefield, draw a card.
        // Enchanted land is an Island.
        addCard(Zone.HAND, playerA, "Spreading Seas", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // Tap: Add 1.
        // {1}: Blinkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying until end of turn. It's still a land.
        // {1}, {T}: Target Blinkmoth gets +1/+1 until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Blinkmoth Nexus");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spreading Seas", "Blinkmoth Nexus");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}:", TestPlayer.NO_TARGET, "Spreading Seas");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Spreading Seas", 0);
        assertGraveyardCount(playerA, "Spreading Seas", 0);
        assertPowerToughness(playerB, "Blinkmoth Nexus", 1, 1);

        assertPermanentCount(playerA, "Spreading Seas", 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testUtopiaSprawlWithSpreadingSeas() {
        addCard(Zone.HAND, playerA, "Spreading Seas", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.HAND, playerA, "Utopia Sprawl");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Utopia Sprawl", "Forest");
        setChoice(playerA, "Green");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spreading Seas", "Forest");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertNotSubtype("Forest", SubType.FOREST);
    }

    @Test
    public void testSpreadingSeasWithUrzaLand() {
        addCard(Zone.HAND, playerA, "Spreading Seas", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Urza's Tower", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spreading Seas", "Urza's Tower");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertNotSubtype("Urza's Tower", SubType.URZAS);
        assertNotSubtype("Urza's Tower", SubType.TOWER);
    }

    /**
     * https://github.com/magefree/mage/issues/4529
     * Some spell effects that effect the use of mana abilities on lands are inoperative.
     * Example, Spreading Seas transforms enchanted land into an island and it loses all
     * other abilities.
     * The AI does not recognize this and is able to use all abilities of the enchanted
     * land including all previous mana abilities and activated abilities,
     * in addition to now also being an island due to Spreading Sea's effect.
     */
    @Test
    public void testSpreadingRemovesOtherAbilities() {
        // Enchant land
        // When Spreading Seas enters the battlefield, draw a card.
        // Enchanted land is an Island.
        addCard(Zone.HAND, playerA, "Spreading Seas", 1); // ENCHANTMENT {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // {T}: Add {C}.
        // {1}{R}, {T}: Create a 0/1 red Kobold creature token named Kobolds of Kher Keep.
        addCard(Zone.BATTLEFIELD, playerB, "Kher Keep", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spreading Seas", "Kher Keep");
        // Kher Keep loses all abilit
        checkPlayableAbility("", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{1}{R}", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Spreading Seas", 1);

        assertPermanentCount(playerB, "Kobolds of Kher Keep", 0);
        assertTapped("Kher Keep", false);
    }

}
