package org.mage.test.cards.copy;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class CopyPermanentSpellTest extends CardTestPlayerBase {

    private void makeTester() {
        addCustomCardWithAbility(
                "Forker", playerA,
                new SpellCastControllerTriggeredAbility(
                        new CopyTargetSpellEffect(true),
                        StaticFilters.FILTER_SPELL, false, true
                )
        );
    }

    @Test
    public void testSimpleToken() {
        setStrictChooseMode(true);
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
    }

    @Test
    public void testAuraToken() {
        setStrictChooseMode(true);
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Holy Strength");

        setChoice(playerA, "No");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Holy Strength", 2);
    }

    @Test
    public void testAuraTokenRedirect() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Centaur Courser");
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant");
        addCard(Zone.HAND, playerA, "Dead Weight");

        setChoice(playerA, "Yes");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dead Weight", "Centaur Courser");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Centaur Courser", 1);
        assertPowerToughness(playerA, "Centaur Courser", 1, 1);
        assertPermanentCount(playerA, "Hill Giant", 1);
        assertPowerToughness(playerA, "Hill Giant", 1, 1);
        assertPermanentCount(playerA, "Dead Weight", 2);
    }

    @Ignore // Currently fails
    @Test
    public void testKicker() {
        setStrictChooseMode(true);
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Goblin Bushwhacker");

        setChoice(playerA, "Yes");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Bushwhacker");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Goblin Bushwhacker", 2);
        assertPowerToughness(playerA, "Grizzly Bears", 4, 2);
        assertAbility(playerA, "Goblin Bushwhacker", HasteAbility.getInstance(), true);
    }
}
