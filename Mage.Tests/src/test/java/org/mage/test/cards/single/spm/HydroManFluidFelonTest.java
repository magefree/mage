package org.mage.test.cards.single.spm;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class HydroManFluidFelonTest extends CardTestPlayerBase {

    /*
    Hydro-Man, Fluid Felon
    {U}{U}
    Legendary Creature - Elemental Villain
    Whenever you cast a blue spell, if Hydro-Man is a creature, he gets +1/+1 until end of turn.
    At the beginning of your end step, untap Hydro-Man. Until your next turn, he becomes a land and gains "{T}: Add {U}."
    2/2
    */
    private static final String hydroManFluidFelon = "Hydro-Man, Fluid Felon";

    /*
    Fugitive Wizard
    {U}
    Creature - Human Wizard

    1/1
    */
    private static final String fugitiveWizard = "Fugitive Wizard";

    @Test
    public void testHydroManFluidFelon() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, fugitiveWizard);
        addCard(Zone.BATTLEFIELD, playerA, hydroManFluidFelon);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fugitiveWizard, true);

        checkPT("Hydro-Man is boosted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, hydroManFluidFelon, 3, 3);

        attack(1, playerA, hydroManFluidFelon);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertType(hydroManFluidFelon, CardType.LAND, true);
        assertNotType(hydroManFluidFelon, CardType.CREATURE);
        assertTapped(hydroManFluidFelon, false);
    }
}