package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author muz
 */
public class RiverSongsDiaryTest extends CardTestPlayerBase {

    /**
     * River Song's Diary {3}
     * Artifact
     * Imprint — Whenever a player casts an instant or sorcery spell from their hand, exile it instead of putting it into a graveyard as it resolves.
     * At the beginning of your upkeep, if there are four or more cards exiled with River Song's Diary, choose one of them at random. You may cast it without paying its mana cost.
     */
    @Test
    public void testResolvingSpellIsExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "River Song's Diary");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
        assertExileCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
    }

    /**
     * Fire // Ice {1}{R} // {1}{U}
     * Instant // Instant
     * Fire deals 2 damage divided as you choose among one or two targets.
     * Tap target permanent. Draw a card.
     */
    @Test
    public void testResolvingSplitSpellIsExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "River Song's Diary");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Fire // Ice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire", playerB);
        addTargetAmount(playerA, playerB, 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 18);
        assertExileCount(playerA, "Fire // Ice", 1);
        assertGraveyardCount(playerA, "Fire // Ice", 0);
    }

    @Test
    public void testCounteredSpellIsNotExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "River Song's Diary");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Lightning Bolt");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20);
        assertExileCount(playerA, "Lightning Bolt", 0);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }

    /**
     * High Tide {U}
     * Instant
     * Until end of turn, whenever a player taps an Island for mana, that player adds an additional {U}.
     *
     * Disenchant {1}{W}
     * Instant
     * Destroy target artifact or enchantment.
     */
    @Test
    public void testUpkeepTriggerStillSeesDiaryExileAfterDiaryLeavesBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "River Song's Diary");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "High Tide", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Disenchant");

        for (int i = 0; i < 4; i++) {
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "High Tide");
        }
        checkExileCount("Four High Tides exiled with the Diary", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "High Tide", 4);
        castSpell(3, PhaseStep.UPKEEP, playerB, "Disenchant", "River Song's Diary");

        setChoice(playerA, true);
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "River Song's Diary", 0);
        assertExileCount(playerA, "High Tide", 3);
        assertGraveyardCount(playerA, "High Tide", 1);
    }
}
