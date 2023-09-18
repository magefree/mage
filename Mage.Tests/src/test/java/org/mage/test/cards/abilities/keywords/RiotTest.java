package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.RiotAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class RiotTest extends CardTestPlayerBase {

    /**
     * A creature with riot enters the battlefield with a +1/+1 counter on it or
     * with haste, its controller's choice. This choice is made as the creature
     * enters the battlefield, so no one can respond to the choice.
     * <p>
     * The creature will have the chosen bonus the moment it enters the
     * battlefield. If you choose to have the creature gain haste, it keeps
     * haste even after the turn ends. This could matter if another player gains
     * control of the creature later.
     */
    @Test
    public void RiotBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        // Riot (This creature enters the battleifled with your choice of a +1/+1 counter or haste.)
        addCard(Zone.HAND, playerA, "Rampaging Rendhorn", 1); // Creature {4}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rampaging Rendhorn");
        setChoice(playerA, true); // yes - counter

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Rampaging Rendhorn", 1);
        assertPowerToughness(playerA, "Rampaging Rendhorn", 5, 5);
        assertAbility(playerA, "Rampaging Rendhorn", HasteAbility.getInstance(), false);

    }

    @Test
    public void RiotHaste() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        // Riot (This creature enters the battlefield with your choice of a +1/+1 counter or haste.)
        addCard(Zone.HAND, playerA, "Rampaging Rendhorn", 1); // Creature {4}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rampaging Rendhorn");
        setChoice(playerA, false); // no - haste

        setStopAt(2, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Rampaging Rendhorn", 1);
        assertPowerToughness(playerA, "Rampaging Rendhorn", 4, 4);
        assertAbility(playerA, "Rampaging Rendhorn", HasteAbility.getInstance(), true);
    }

    @Test
    public void RiotRhythmOfTheWildBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Creature spells you control can't be countered.
        // Nontoken creatures you control have riot.
        addCard(Zone.BATTLEFIELD, playerA, "Rhythm of the Wild", 1);

        // Riot (This creature enters the battleifled with your choice of a +1/+1 counter or haste.)
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1); // Creature {1}{W}  2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        setChoice(playerA, true); // yes - counter

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);
        assertAbility(playerA, "Silvercoat Lion", HasteAbility.getInstance(), false);
        assertAbility(playerA, "Silvercoat Lion", new RiotAbility(), true);
    }

    @Test
    public void RiotRhythmOfTheWildDoubleBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Creature spells you control can't be countered.
        // Nontoken creatures you control have riot.
        addCard(Zone.BATTLEFIELD, playerA, "Rhythm of the Wild", 2);

        // Riot (This creature enters the battleifled with your choice of a +1/+1 counter or haste.)
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1); // Creature {1}{W}  2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        setChoice(playerA, "Rhythm of the Wild"); // choose replacement effect
        setChoice(playerA, true); // yes - counter
        setChoice(playerA, true); // yes - counter

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2 + 2, 2 + 2);
        assertAbility(playerA, "Silvercoat Lion", HasteAbility.getInstance(), false);
        assertAbility(playerA, "Silvercoat Lion", new RiotAbility(), true);
    }

    @Test
    public void RiotRhythmOfTheWildHaste() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Creature spells you control can't be countered.
        // Nontoken creatures you control have riot.
        addCard(Zone.BATTLEFIELD, playerA, "Rhythm of the Wild", 1);

        // Riot (This creature enters the battleifled with your choice of a +1/+1 counter or haste.)
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1); // Creature {1}{W}  2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        setChoice(playerA, false); // no - haste

        setStopAt(2, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertAbility(playerA, "Silvercoat Lion", HasteAbility.getInstance(), true);
        assertAbility(playerA, "Silvercoat Lion", new RiotAbility(), true);
    }

    @Test
    public void RiotRhythmOfTheWildNotCastBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // Creature spells you control can't be countered.
        // Nontoken creatures you control have riot.
        addCard(Zone.BATTLEFIELD, playerA, "Rhythm of the Wild", 1);

        // Riot (This creature enters the battleifled with your choice of a +1/+1 counter or haste.)
        addCard(Zone.HAND, playerA, "Exhume", 1); // Each player returns a creature card from their graveyard to the battlefield
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 1); // Creature {1}{W}  2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhume");
        setChoice(playerA, true); // yes - counter

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);
        assertAbility(playerA, "Silvercoat Lion", HasteAbility.getInstance(), false);
        assertAbility(playerA, "Silvercoat Lion", new RiotAbility(), true);
    }

    @Test
    public void RiotRhythmOfTheWildNotCastHaste() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // Creature spells you control can't be countered.
        // Nontoken creatures you control have riot.
        addCard(Zone.BATTLEFIELD, playerA, "Rhythm of the Wild", 1);

        // Riot (This creature enters the battleifled with your choice of a +1/+1 counter or haste.)
        addCard(Zone.HAND, playerA, "Exhume", 1); // Each player returns a creature card from their graveyard to the battlefield
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 1); // Creature {1}{W}  2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhume");
        setChoice(playerA, false); // no - haste

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertAbility(playerA, "Silvercoat Lion", HasteAbility.getInstance(), true);
        assertAbility(playerA, "Silvercoat Lion", new RiotAbility(), true);
    }
}
