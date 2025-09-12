package org.mage.test.cards.single.fic;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


public class UriangerAugureltTest extends CardTestPlayerBase {

    private static final String urianger = "Urianger Augurelt";
    private static final String arcaneSignet = "Arcane Signet";
    private static final String howlingMine = "Howling Mine";
    private static final String thoughtVessel = "Thought Vessel";
    private static final String benalishKnight = "Benalish Knight";

    @Test
    public void uriangerAugureltTest() {
        setStrictChooseMode(true);
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);

        Ability ability = new SimpleActivatedAbility(
                Zone.ALL,
                new UntapAllControllerEffect(StaticFilters.FILTER_CONTROLLED_A_CREATURE),
                new ManaCostsImpl<>("")
        );
        addCustomCardWithAbility("Untap creatures", playerA, ability);

        addCard(Zone.BATTLEFIELD, playerA, urianger);
        addCard(Zone.LIBRARY, playerA, "Plains", 3);
        addCard(Zone.LIBRARY, playerA, arcaneSignet);
        addCard(Zone.LIBRARY, playerA, howlingMine);
        addCard(Zone.LIBRARY, playerA, thoughtVessel);
        addCard(Zone.LIBRARY, playerA, benalishKnight);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Draw Arcanum</i>");
        setChoice(playerA, true, 4);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "untap all");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Draw Arcanum</i>");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "untap all");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Draw Arcanum</i>");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "untap all");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Draw Arcanum</i>");


        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Play Arcanum</i>");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, benalishKnight, true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, arcaneSignet, true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, howlingMine, true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, thoughtVessel, true);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, benalishKnight, 1);
        assertPermanentCount(playerA, arcaneSignet, 1);
        assertPermanentCount(playerA, howlingMine, 1);
        assertPermanentCount(playerA, thoughtVessel, 1);
        assertLife(playerA, 20 + 2 * 4);
    }
}
