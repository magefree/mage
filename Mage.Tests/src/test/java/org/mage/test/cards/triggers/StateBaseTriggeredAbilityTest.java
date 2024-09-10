package org.mage.test.cards.triggers;

import mage.abilities.StateTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SoldierToken;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class StateBaseTriggeredAbilityTest extends CardTestPlayerBase {

    static class CustomStateTriggeredAbility extends StateTriggeredAbility {

        public CustomStateTriggeredAbility(boolean usesStack) {
            super(Zone.ALL, new CreateTokenEffect(new SoldierToken()));
            this.usesStack = usesStack;
        }

        private CustomStateTriggeredAbility(final CustomStateTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public CustomStateTriggeredAbility copy() {
            return new CustomStateTriggeredAbility(this);
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return game.getBattlefield().getAllActivePermanents().stream().noneMatch(p -> p.isCreature(game))
                    && game.getBattlefield().getAllActivePermanents().stream().noneMatch(p -> p.isEnchantment(game));
        }

        @Override
        public String getRule() {
            return "If no creates or enchantments on battlefield then create Soldier token";
        }
    }

    @Test
    public void test_WithoutStack() {
        // If no creates or enchantments on battlefield then create Soldier token
        addCustomCardWithAbility("test", playerA, new CustomStateTriggeredAbility(false));

        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // no SBA-triggers
        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soldier Token", 0);

        // destroy creature and activate SBA-trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        checkStackSize("on cast bolt", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkStackObject("on cast bolt", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", 1);
        //
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after SBA resolve", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Soldier Token", 1);
    }

    @Test
    public void test_WithStack() {
        // If no creates or enchantments on battlefield then create Soldier token
        addCustomCardWithAbility("test", playerA, new CustomStateTriggeredAbility(true));

        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // no SBA-triggers
        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soldier Token", 0);

        // destroy creature and activate SBA-trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        checkStackSize("on cast bolt", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkStackObject("on cast bolt", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", 1);
        //
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after bolt resolve - sba", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkStackObject("after bolt resolve - sba", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "If no creates or enchantments", 1);
        //
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after SBA resolve", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Soldier Token", 1);
    }

    @Test
    @Ignore // TODO: enable after non-stack delayed triggers reworked, search: state.addTriggeredAbility
    public void test_GraspOfFate_DelayedTriggerMustResolveImmediately() {
        // checking rule:
        // The exiled cards return to the battlefield immediately after Grasp of Fate leaves the battlefield.
        // Nothing happens between the two events, including state-based actions.
        // (2015-11-04)

        // If no creates or enchantments on battlefield then create Soldier token
        addCustomCardWithAbility("test", playerA, new CustomStateTriggeredAbility(false));

        // When Grasp of Fate enters the battlefield, for each opponent, exile up to one target nonland permanent
        // that player controls until Grasp of Fate leaves the battlefield. (Those permanents return under their
        // owners’ control.)
        addCard(Zone.HAND, playerA, "Grasp of Fate", 1); // {1}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        //
        // Destroy target enchantment
        addCard(Zone.HAND, playerA, "Collective Effort", 1); // {1}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // no SBA-triggers
        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soldier Token", 0);

        // cast and exile permanent (no SBA-trigger)
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grasp of Fate");
        addTarget(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerA, "Soldier Token", 0);
        checkPermanentCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Grizzly Bears", 0);

        // destroy grasp and return bears (no SBA-trigger, see related rules)
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Collective Effort");
        setModeChoice(playerA, "2"); // Destroy target enchantment
        addTarget(playerA, "Grasp of Fate");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after return", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerA, "Soldier Token", 0);
        checkPermanentCount("after return", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Grizzly Bears", 1);

        // make sure sba works
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("after sba", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerA, "Soldier Token", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
