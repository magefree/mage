
package org.mage.test.cards.replacement.entersBattlefield;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HardenedScaleTest extends CardTestPlayerBase {

    /**
     * Reported bug: Hangarback interaciton with Hardened Scales and Metallic Mimic on board is incorrect.
     */
    @Test
    public void hangarBackHardenedScalesMetallicMimicTest() {

        /*
          Hangarback Walker {X}{X}
        Artifact Creature — Construct 0/0
        Hangarback Walker enters the battlefield with X +1/+1 counters on it.
        When Hangarback Walker dies, create a 1/1 colorless Thopter artifact creature token with flying for each +1/+1 counter on Hangarback Walker.
        {1}, {T}: Put a +1/+1 counter on Hangarback Walker.
         */
        String hWalker = "Hangarback Walker";

        /*
        Hardened Scales {G}
        Enchantment
        If one or more +1/+1 counters would be placed on a creature you control, that many plus one +1/+1 counters are placed on it instead.
         */
        String hScales = "Hardened Scales";

        /*
        Metallic Mimic {2}
        Artifact Creature — Shapeshifter 2/1
        As Metallic Mimic enters the battlefield, choose a creature type.
        Metallic Mimic is the chosen type in addition to its other types.
        Each other creature you control of the chosen type enters the battlefield with an additional +1/+1 counter on it.
         */
        String mMimic = "Metallic Mimic";

        addCard(Zone.BATTLEFIELD, playerA, hScales);
        addCard(Zone.HAND, playerA, mMimic);
        addCard(Zone.HAND, playerA, hWalker);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mMimic);
        setChoice(playerA, "Construct");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hWalker);
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, mMimic, 1);
        assertPermanentCount(playerA, hWalker, 1);
        assertCounterCount(playerA, hWalker, CounterType.P1P1, 3);
        assertPowerToughness(playerA, hWalker, 3, 3);
    }

    @Test
    public void testWithVigorMortis() {

        /*
        Vigor Mortis {2}{B}{B}
        Sorcery
        Return target creature card from your graveyard to the battlefield. If {G} was spent to cast Vigor Mortis,
        that creature enters the battlefield with an additional +1/+1 counter on it.
         */
        String vMortis = "Vigor Mortis";

        /*
        Hardened Scales {G}
        Enchantment
        If one or more +1/+1 counters would be placed on a creature you control, that many plus one +1/+1 counters are placed on it instead.
         */
        String hScales = "Hardened Scales";

        /*
         Metallic Mimic {2}
        Artifact Creature — Shapeshifter 2/1
        As Metallic Mimic enters the battlefield, choose a creature type.
        Metallic Mimic is the chosen type in addition to its other types.
        Each other creature you control of the chosen type enters the battlefield with an additional +1/+1 counter on it.
         */
        String mMimic = "Metallic Mimic";

        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, hScales);
        addCard(Zone.HAND, playerA, mMimic);
        addCard(Zone.HAND, playerA, vMortis);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mMimic);
        setChoice(playerA, "Cat");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, vMortis, "Silvercoat Lion");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, mMimic, 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 3);
        assertPowerToughness(playerA, "Silvercoat Lion", 5, 5); // Hardened Scales is only once applied to EntersTheBattlefield event
        assertGraveyardCount(playerA, vMortis, 1);
    }
}
