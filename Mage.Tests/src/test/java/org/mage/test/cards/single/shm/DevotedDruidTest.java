package org.mage.test.cards.single.shm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DevotedDruidTest extends CardTestPlayerBase {

    @Test
    public void test_PutCounter_Normal() {
        // {T}: Add {G}.
        // Put a -1/-1 counter on this creature: Untap this creature.
        addCard(Zone.BATTLEFIELD, playerA, "Devoted Druid", 1); // 0/2

        // prepare
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        checkPermanentTapped("after mana tapped", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Devoted Druid", true, 1);

        // add counter and untap
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Put a -1/-1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentTapped("after untap", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Devoted Druid", false, 1);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_PutCounter_CantPay() {
        // {T}: Add {G}.
        // Put a -1/-1 counter on this creature: Untap this creature.
        addCard(Zone.BATTLEFIELD, playerA, "Devoted Druid", 1); // 0/2
        //
        // Players can’t get counters.
        // Counters can’t be put on artifacts, creatures, enchantments, or lands.
        addCard(Zone.BATTLEFIELD, playerA, "Solemnity", 1);

        // If you can't put -1/-1 counters on Devoted Druid (due to an effect such as that of Solemnity),
        // you can't activate its second ability.
        // ...
        // (2018-12-07)

        //checkPlayableAbility("can't put counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Put a -1/-1", false);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Put a -1/-1");

        setStopAt(1, PhaseStep.END_TURN);
        // TODO: improve PutCountersSourceCost, so it can find real playable ability here instead restriction
        try {
            setStrictChooseMode(true);
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Put a -1/-1")) {
                Assert.fail("Needed error about not being able to use the Devoted Druid's -1/-1 ability, but got:\n" + e.getMessage());
            }
        }
    }

    @Test
    @Ignore // TODO: must fix, see #13583
    public void test_PutCounter_ModifiedToZeroCounters() {
        // {T}: Add {G}.
        // Put a -1/-1 counter on this creature: Untap this creature.
        addCard(Zone.BATTLEFIELD, playerA, "Devoted Druid", 1); // 0/2
        //
        // If one or more -1/-1 counters would be put on a creature you control, that many -1/-1 counters
        // minus one are put on it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Vizier of Remedies", 1);


        // ...
        // If you can put counters on it, but that is modified by an effect (such as that of Vizier of Remedies),
        // you can activate the ability even if paying the cost causes no counters to be put on Devoted Druid.
        // (2018-12-07)

        // prepare
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        checkPermanentTapped("after mana tapped", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Devoted Druid", true, 1);

        // add counter and untap
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Put a -1/-1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentTapped("after untap", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Devoted Druid", false, 1);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
