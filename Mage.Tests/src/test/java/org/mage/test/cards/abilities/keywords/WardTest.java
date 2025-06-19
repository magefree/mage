package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author weirddan455
 */
public class WardTest extends CardTestPlayerBase {

    @Test
    public void wardMultipleAbilities() {
        addCard(Zone.HAND, playerA, "Solitude"); // https://github.com/magefree/mage/issues/8378 Test that ward counters correct ability
        addCard(Zone.HAND, playerA, "Healer's Hawk"); // Card to pitch to Solitude
        addCard(Zone.BATTLEFIELD, playerB, "Waterfall Aerialist");  // Flying, Ward 2
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Solitude");
        setChoice(playerA, "Cast with Evoke alternative cost: Exile a white card from your hand (source: Solitude");
        setChoice(playerA, "Healer's Hawk");
        setChoice(playerA, "When {this} enters, exile up to one other target creature"); // Put exile trigger on the stack first (evoke trigger will resolve first)
        addTarget(playerA, "Waterfall Aerialist");
        setChoice(playerA, "No"); // Do not pay Ward cost
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount("Healer's Hawk", 1);
        assertGraveyardCount(playerA, "Solitude", 1);
        assertPermanentCount(playerB, "Waterfall Aerialist", 1);
    }

    @Test
    public void wardPanharmonicon() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        addCard(Zone.BATTLEFIELD, playerA, "Young Red Dragon");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.BATTLEFIELD, playerB, "Roaming Throne");
        addCard(Zone.HAND, playerA, "Scourge of Valkas");

        setChoice(playerB, "Dragon");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scourge of Valkas");
        setChoice(playerA, "Whenever {this} or another Dragon");
        addTarget(playerA, "Roaming Throne");
        addTarget(playerA, "Roaming Throne");
        setChoice(playerB, "ward {2}");
        setChoice(playerA, "Yes");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerB, "Roaming Throne", 1);
        assertDamageReceived(playerB, "Roaming Throne", 2);
    }

    @Test
    public void wardPanharmoniconCounter() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        addCard(Zone.BATTLEFIELD, playerA, "Young Red Dragon");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Roaming Throne");
        addCard(Zone.HAND, playerA, "Scourge of Valkas");

        setChoice(playerB, "Dragon");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scourge of Valkas");
        setChoice(playerA, "Whenever {this} or another Dragon");
        addTarget(playerA, "Roaming Throne");
        addTarget(playerA, "Roaming Throne");
        setChoice(playerB, "ward {2}");
        setChoice(playerA, "No");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerB, "Roaming Throne", 1);
        assertDamageReceived(playerB, "Roaming Throne", 0);
    }

    // Reported bug: #13523 - Ward not properly triggering when re-casting Aura

    private static final String creature = "Owlin Shieldmage"; // 3/3 Flying, Ward - Pay 3 life
    private static final String aura = "Kenrith's Transformation"; // 1G enchanted creature loses all abilities and is 3/3 Elk (also draw card on ETB)
    private static final String spell = "Beast Within"; // 2G destroy permanent, controller gets 3/3 Beast
    private static final String regrowth = "Regrowth"; // 1G return target card from graveyard to hand

    @Test
    public void wardRecastAuraNoPay() {

        addCard(Zone.BATTLEFIELD, playerB, creature);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 9);
        addCard(Zone.HAND, playerA, aura);
        addCard(Zone.HAND, playerA, spell);
        addCard(Zone.HAND, playerA, regrowth);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aura, creature);
        setChoice(playerA, false); // don't pay for ward

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, regrowth, aura);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aura, creature);
        setChoice(playerA, false); // don't pay for ward

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerA, aura, 1);
        assertGraveyardCount(playerA, regrowth, 1);
        assertGraveyardCount(playerA, 2);
        assertHandCount(playerA, spell, 1);
        assertHandCount(playerA, 1);
        assertAbility(playerB, creature, FlyingAbility.getInstance(), true);

    }

    @Test
    public void wardRecastAuraPaySecond() {

        addCard(Zone.BATTLEFIELD, playerB, creature);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 9);
        addCard(Zone.HAND, playerA, aura);
        addCard(Zone.HAND, playerA, spell);
        addCard(Zone.HAND, playerA, regrowth);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aura, creature);
        setChoice(playerA, false); // don't pay for ward

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, regrowth, aura);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aura, creature);
        setChoice(playerA, true); // pay ward

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertGraveyardCount(playerA, aura, 0);
        assertGraveyardCount(playerA, regrowth, 1);
        assertGraveyardCount(playerA, 1);
        assertHandCount(playerA, spell, 1);
        assertHandCount(playerA, 2); // one draw from aura entering
        assertAttachedTo(playerB, aura, creature, true);
        assertAbility(playerB, creature, FlyingAbility.getInstance(), false);

    }

    @Test
    public void wardRecastAuraPayBoth() {

        addCard(Zone.BATTLEFIELD, playerB, creature);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 9);
        addCard(Zone.HAND, playerA, aura);
        addCard(Zone.HAND, playerA, spell);
        addCard(Zone.HAND, playerA, regrowth);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aura, creature);
        setChoice(playerA, true); // pay for ward

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spell, aura); // destroy aura

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, regrowth, aura);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aura, creature);
        setChoice(playerA, true); // pay ward

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 14);
        assertGraveyardCount(playerA, spell, 1);
        assertGraveyardCount(playerA, regrowth, 1);
        assertGraveyardCount(playerA, 2);
        assertHandCount(playerA, 2); // two draws from aura entering
        assertAttachedTo(playerB, aura, creature, true);
        assertAbility(playerB, creature, FlyingAbility.getInstance(), false);

    }

}
