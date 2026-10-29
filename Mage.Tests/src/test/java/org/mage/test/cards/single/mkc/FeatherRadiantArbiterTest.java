package org.mage.test.cards.single.mkc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author brahle
 */
public class FeatherRadiantArbiterTest extends CardTestPlayerBase {

    @Test
    public void testInstantCopyTwoCreatures() {
        setStrictChooseMode(true);
        // Feather, Radiant Arbiter {R}{W}{W} 4/3 Angel
        // Flying, lifelink
        // Whenever you cast a noncreature spell that targets only Feather, Radiant Arbiter,
        // you may choose any number of other creatures that spell could target and pay {2} for each of those creatures.
        // If you do, for each of those creatures, copy that spell. The copy targets that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Giant Growth"); // {G} Target creature gets +3/+3 until end of turn.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Feather, Radiant Arbiter");
        addTarget(playerA, "Grizzly Bears^Silvercoat Lion");
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // skip stack order for copies

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Feather, Radiant Arbiter", 4 + 3, 3 + 3);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 3, 2 + 3);
        assertPowerToughness(playerA, "Silvercoat Lion", 2 + 3, 2 + 3);
        assertTappedCount("Forest", true, 5); // 1 for Giant Growth + 4 for two copies
    }

    @Test
    public void testAuraCopyCreatesTokens() {
        setStrictChooseMode(true);
        // Angelic Gift {1}{W} - Enchant creature - When Angelic Gift enters the battlefield, draw a card.
        // Enchanted creature has flying.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Angelic Gift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angelic Gift", "Feather, Radiant Arbiter");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Angelic Gift", 2); // 1 original + 1 token
        assertHandCount(playerA, 2); // drew 2 cards (one for original, one for copy)
        assertAbility(playerA, "Grizzly Bears", mage.abilities.keyword.FlyingAbility.getInstance(), true);
        assertTappedCount("Plains", true, 4); // 2 for Angelic Gift + 2 for copy
    }

    @Test
    public void testDeclinePayment() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Giant Growth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Feather, Radiant Arbiter");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // Choose 0 creatures

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Feather, Radiant Arbiter", 4 + 3, 3 + 3);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
        assertTappedCount("Forest", true, 1); // Only {G} paid
    }

    @Test
    public void testMultiTargetSpellDoesNotTrigger() {
        setStrictChooseMode(true);
        // Seeds of Strength {G}{W} - Instant
        // Target creature gets +1/+1 until end of turn.
        // Target creature gets +1/+1 until end of turn.
        // Target creature gets +1/+1 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 2);
        addCard(Zone.HAND, playerA, "Seeds of Strength");

        // Target Feather with 2, Bears with 1 -> Should NOT trigger Feather
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Seeds of Strength");
        addTarget(playerA, "Feather, Radiant Arbiter");
        addTarget(playerA, "Feather, Radiant Arbiter");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Feather, Radiant Arbiter", 4 + 2, 3 + 2);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
        assertTappedCount("Savannah", true, 2); // 2 mana for Seeds of Strength
    }

    @Test
    public void testCanTargetOpponentsCreature() {
        setStrictChooseMode(true);
        // Swords to Plowshares {W} - Exile target creature. Its controller gains life equal to its power.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter");
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter"); // 0/2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Swords to Plowshares");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swords to Plowshares", "Feather, Radiant Arbiter");
        addTarget(playerA, "Ornithopter");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, 1); // Feather exiled
        assertExileCount(playerB, 1); // Ornithopter exiled
        assertLife(playerA, 20 + 4); // Feather's power 4
        assertLife(playerB, 20 + 0); // Ornithopter power 0
        assertTappedCount("Plains", true, 3); // 1 for Swords + 2 for copy
    }
}
