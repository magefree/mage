package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

public class SneakTest extends CardTestCommanderDuelBase {

    private static final String karai = "Karai, Future of the Foot";
    private static final String goblin = "Raging Goblin";
    private static final String leonardosTechnique = "Leonardo's Technique";

    @Test
    public void testNonCreatureSneakSpellResolvesEffect() {
        // Leonardo's Technique is a sorcery — the Sneak cost should still work,
        // the spell resolves its effect, but the tapped-and-attacking replacement
        // effect must NOT apply to other permanents (Grizzly Bears) that enter
        // as part of the spell's resolution.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, goblin);
        addCard(Zone.HAND, playerA, leonardosTechnique);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");

        attack(1, playerA, goblin, playerB);

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, leonardosTechnique + " with Sneak", "Grizzly Bears");
        setChoice(playerA, goblin);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, goblin, 1);
        assertGraveyardCount(playerA, leonardosTechnique, 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertTappedCount("Grizzly Bears", false, 1); // replacement effect did not fire for returned creature
        assertLife(playerB, 40); // no combat damage — goblin returned to hand, Bears did not attack
    }

    @Test
    public void testCreatureSneakFromHandEntersTappedAndAttacking() {
        addCard(Zone.BATTLEFIELD, playerA, "Godless Shrine", 4);
        addCard(Zone.BATTLEFIELD, playerA, goblin);
        addCard(Zone.HAND, playerA, karai);

        attack(1, playerA, goblin, playerB);

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, karai + " with Sneak");
        setChoice(playerA, goblin);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertHandCount(playerA, goblin, 1);
        assertPermanentCount(playerA, karai, 1);
        assertTappedCount(karai, true, 1);
        assertAttacking(karai, true);
        assertLife(playerB, 37);
    }

    @Test
    public void testCreatureSneakAttackingPlaneswalkerEntersAttackingIt() {
        String sorin = "Sorin, Solemn Visitor";

        addCard(Zone.BATTLEFIELD, playerA, "Godless Shrine", 4);
        addCard(Zone.BATTLEFIELD, playerA, goblin);
        addCard(Zone.HAND, playerA, karai);
        addCard(Zone.BATTLEFIELD, playerB, sorin);

        attack(1, playerA, goblin, sorin);

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, karai + " with Sneak");
        setChoice(playerA, goblin);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertHandCount(playerA, goblin, 1);
        assertPermanentCount(playerA, karai, 1);
        assertTappedCount(karai, true, 1);
        assertAttacking(karai, true);
        assertCounterCount(sorin, CounterType.LOYALTY, 1);
        assertLife(playerB, 40); // no direct damage taken
    }

    @Test
    public void testCreatureSneakFromCommandZoneEntersTappedAndAttacking() {
        addCard(Zone.BATTLEFIELD, playerA, "Godless Shrine", 4);
        addCard(Zone.BATTLEFIELD, playerA, goblin);
        addCard(Zone.COMMAND, playerA, karai);

        attack(1, playerA, goblin, playerB);

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, karai + " with Sneak");
        setChoice(playerA, goblin);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertCommandZoneCount(playerA, karai, 0);
        assertHandCount(playerA, goblin, 1);
        assertPermanentCount(playerA, karai, 1);
        assertTappedCount(karai, true, 1);
        assertAttacking(karai, true);
        assertLife(playerB, 37);
    }
}
