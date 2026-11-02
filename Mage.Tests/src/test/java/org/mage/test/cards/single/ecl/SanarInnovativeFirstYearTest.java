package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author adams-shaun
 */
public class SanarInnovativeFirstYearTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SanarInnovativeFirstYear Sanar, Innovative First-Year} {2}{U/R}{U/R}
     * Legendary Creature - Goblin Sorcerer 2/4
     * Vivid -- At the beginning of your first main phase, reveal cards from the top of your library until you reveal
     * X nonland cards, where X is the number of colors among permanents you control. For each of those colors,
     * you may exile a card of that color from among the revealed cards. Then shuffle.
     * You may cast the exiled cards this turn.
     */
    private static final String sanar = "Sanar, Innovative First-Year";
    private static final String blue = "Jace Beleren";
    private static final String green = "Grizzly Bears";

    private void prepare() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, sanar);
        addCard(Zone.LIBRARY, playerA, green);
        addCard(Zone.LIBRARY, playerA, blue);
    }

    @Test
    public void test_ChooseACardOfAControlledColor() {
        prepare();

        setChoice(playerA, blue); // nothing else can be chosen after it

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, blue, 1);
        assertExileCount(playerA, green, 0);
    }

    @Test
    public void test_ChooseOneCardForEachColor() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, sanar);
        addCard(Zone.LIBRARY, playerA, "Goblin Piker");
        addCard(Zone.LIBRARY, playerA, blue);

        setChoice(playerA, blue);
        setChoice(playerA, "Goblin Piker");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, blue, 1);
        assertExileCount(playerA, "Goblin Piker", 1);
    }

    @Test
    public void test_OtherColorsCanNotBeChosen() {
        prepare();

        // no scripted choice: the computer takes every card it is offered
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, blue, 1);
        assertExileCount(playerA, green, 0);
    }
}
