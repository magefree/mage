package org.mage.test.cards.copy;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
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
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
    }

    @Test
    public void testAuraToken() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Holy Strength");

        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

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

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dead Weight", "Centaur Courser");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Centaur Courser", 1);
        assertPowerToughness(playerA, "Centaur Courser", 1, 1);
        assertPermanentCount(playerA, "Hill Giant", 1);
        assertPowerToughness(playerA, "Hill Giant", 1, 1);
        assertPermanentCount(playerA, "Dead Weight", 2);
    }

    @Ignore // currently fails
    @Test
    public void testKickerTrigger() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Goblin Bushwhacker");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Bushwhacker");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Goblin Bushwhacker", 2);
        assertPowerToughness(playerA, "Grizzly Bears", 4, 2);
    }

    @Ignore // currently fails
    @Test
    public void testKickerReplacement() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Aether Figment");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Figment");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Aether Figment", 2);
        assertPowerToughness(playerA, "Aether Figment", 3, 3, Filter.ComparisonScope.All);
    }

    @Ignore // currently fails
    @Test
    public void testSurgeTrigger() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Memnite");
        addCard(Zone.HAND, playerA, "Reckless Bushwhacker");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reckless Bushwhacker with surge");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Reckless Bushwhacker", 2);
        assertPowerToughness(playerA, "Memnite", 3, 1, Filter.ComparisonScope.All);
    }

    @Test
    public void testBestow() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Nimbus Naiad");

        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nimbus Naiad using bestow", "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, "Grizzly Bears", 6, 6);
        assertPermanentCount(playerA, "Nimbus Naiad", 2);
    }

    @Test
    public void testBestowRedirect() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Nimbus Naiad");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nimbus Naiad using bestow", "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, "Grizzly Bears", 4, 4);
        assertAbility(playerA, "Grizzly Bears", FlyingAbility.getInstance(), true);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertAbility(playerA, "Silvercoat Lion", FlyingAbility.getInstance(), true);

        assertPermanentCount(playerA, "Nimbus Naiad", 2);
    }

    @Ignore // currently fails
    @Test
    public void testBestowFallOff() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Murder", 1);
        addCard(Zone.HAND, playerA, "Nimbus Naiad");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nimbus Naiad using bestow", "Grizzly Bears");

        setChoice(playerA, false);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Murder", "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);

        assertPermanentCount(playerA, "Nimbus Naiad", 2);
    }

    @Ignore // currently fails
    @Test
    public void testBestowRedirectFallOff() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Murder", 1);
        addCard(Zone.HAND, playerA, "Nimbus Naiad");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nimbus Naiad using bestow", "Grizzly Bears");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Murder", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertPermanentCount(playerA, "Nimbus Naiad", 2);
    }

    @Ignore // currently fails
    @Test
    public void testBestowIllegalTarget() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Murder", 1);
        addCard(Zone.HAND, playerA, "Nimbus Naiad");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nimbus Naiad using bestow", "Grizzly Bears");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);

        assertPermanentCount(playerA, "Nimbus Naiad", 2);
    }
}
