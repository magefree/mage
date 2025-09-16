package org.mage.test.cards.single.spm;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapAllEffect;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class ScarletSpiderBenReillyTest extends CardTestPlayerBase {

    /*
    Scarlet Spider, Ben Reilly
    {1}{R}{G}
    Legendary Creature - Spider Human Hero
    Web-slinging {R}{G}
    Trample
    Sensational Save -- If Scarlet Spider was cast using web-slinging, he enters with X +1/+1 counters on him, where X is the mana value of the returned creature.
    4/3
    */
    private static final String scarletSpiderBenReilly = "Scarlet Spider, Ben Reilly";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear
    
    2/2
    */
    private static final String bearCub = "Bear Cub";

    @Test
    public void testScarletSpiderBenReilly() {
        setStrictChooseMode(true);

        addCustomCardWithAbility("tap all creatures", playerA, new SimpleActivatedAbility(
                new TapAllEffect(new FilterCreaturePermanent(SubType.BEAR, "bears")),
                new ManaCostsImpl<>("")
        ));

        addCard(Zone.HAND, playerA, scarletSpiderBenReilly);
        addCard(Zone.BATTLEFIELD, playerA, bearCub, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "tap all"); // tap bears, addCard command isn't working to set tapped
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, scarletSpiderBenReilly + " with Web-slinging");
        setChoice(playerA, bearCub);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, scarletSpiderBenReilly, CounterType.P1P1, 2);
        assertHandCount(playerA, bearCub, 1);
    }
}