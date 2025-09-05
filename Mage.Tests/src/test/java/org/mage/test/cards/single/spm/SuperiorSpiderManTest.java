package org.mage.test.cards.single.spm;

import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class SuperiorSpiderManTest extends CardTestPlayerBase {

    /*
    Superior Spider-Man
    {2}{U}{B}
    Legendary Creature - Spider Human Hero
    Mind Swap -- You may have Superior Spider- Man enter as a copy of any creature card in a graveyard, except his name is Superior Spider-Man and he's a 4/4 Spider Human Hero in addition to his other types. When you do, exile that card.
    4/4
    */
    private static final String superiorSpiderMan = "Superior Spider-Man";

    /*
    Adelbert Steiner
    {1}{W}
    Legendary Creature - Human Knight
    Lifelink
    Adelbert Steiner gets +1/+1 for each Equipment you control.
    2/1
    */
    private static final String adelbertSteiner = "Adelbert Steiner";

    @Test
    public void testSuperiorSpiderMan() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, superiorSpiderMan);
        addCard(Zone.GRAVEYARD, playerA, adelbertSteiner);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, superiorSpiderMan);
        setChoice(playerA, true);
        setChoice(playerA, adelbertSteiner);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerA, 1);
        assertSubtype(superiorSpiderMan, SubType.KNIGHT);
        assertSubtype(superiorSpiderMan, SubType.SPIDER);
        assertSubtype(superiorSpiderMan, SubType.HERO);
        assertSubtype(superiorSpiderMan, SubType.HUMAN);
        assertAbility(playerA, superiorSpiderMan, LifelinkAbility.getInstance(), true);
    }
}