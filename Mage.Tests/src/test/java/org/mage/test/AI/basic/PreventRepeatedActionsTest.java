package org.mage.test.AI.basic;

import mage.abilities.TriggeredAbilityImpl;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LevelX2, JayDi85
 */
public class PreventRepeatedActionsTest extends CardTestPlayerBaseAI {

    /**
     * Check that an equipment is not switched again an again between creatures
     */
    @Test
    public void test_EquipOnlyOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // Equipped creature gets +1/+1.
        // Equip {1}({1}: Attach to target creature you control. Equip only as a sorcery.)
        addCard(Zone.BATTLEFIELD, playerA, "Fireshrieker", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        int tappedLands = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), playerA.getId(), currentGame)) {
            if (permanent.isTapped()) {
                tappedLands++;
            }
        }
        Assert.assertEquals("AI should only used Equipment once", 2, tappedLands);
    }

    /**
     * If the AI on a local server gets control of a Basalt Monolith it will
     * infinite loop taping for three mana and then using the mana to untap lol.
     * Seeing the computer durdle troll is quite the hillarious thing
     */
    @Test
    public void test_BasaltMonolith_1() {
        addCard(Zone.HAND, playerA, "Phyrexian Vault", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // Basalt Monolith doesn't untap during your untap step.
        // {T}: Add {C}{C}{C}.
        // {3}: Untap Basalt Monolith.
        addCard(Zone.BATTLEFIELD, playerA, "Basalt Monolith", 1, true);

        setStopAt(5, PhaseStep.END_TURN);
        execute();

        // {2}, {T}, Sacrifice a creature: Draw a card.
        assertPermanentCount(playerA, "Phyrexian Vault", 1);
        assertTapped("Basalt Monolith", true);
        assertTappedCount("Plains", false, 3);
    }

    @Test
    @Ignore // TODO: enable after repeated actions fix, see #10197
    public void test_BasaltMonolith_2() {
        // workaround to collect all activated/cast abilities
        // AI logs also help to find a problem: SELECTED ACTION for PlayerA: {3}: Untap {this}.
        List<String> usedStats = new ArrayList<>();
        addCustomCardWithAbility("stats", playerA, new CollectUsedAbilitiesStats(usedStats));

        // Basalt Monolith doesn't untap during your untap step.
        // {T}: Add {C}{C}{C}.
        // {3}: Untap Basalt Monolith.
        addCard(Zone.BATTLEFIELD, playerA, "Basalt Monolith", 2);
        //
        addCard(Zone.HAND, playerA, "Aesthir Glider", 1); // {3}

        // AI must cast Aesthir Glider only

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Aesthir Glider", 1);
        Assert.assertEquals("AI must use only cast ability", "[Cast Aesthir Glider]", usedStats.toString());
    }

    /**
     * AI gets stuck with two Kiora's Followers #1167
     */
    @Test
    public void test_KiorasFollower() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2, true);
        // {T}: Untap another target permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Kiora's Follower", 1, true);
        addCard(Zone.BATTLEFIELD, playerA, "Kiora's Follower", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2);

        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Silvercoat Lion");
        blockSkip(2, playerA);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Kiora's Follower", 2);
        assertPermanentCount(playerB, "Silvercoat Lion", 2);
        assertLife(playerA, 16);
        assertTapped("Kiora's Follower", false);
    }

    static class CollectUsedAbilitiesStats extends TriggeredAbilityImpl {

        private final List<String> usedStats;

        CollectUsedAbilitiesStats(List<String> usedStats) {
            super(Zone.BATTLEFIELD, null);
            usesStack = false;

            this.usedStats = usedStats;
        }

        private CollectUsedAbilitiesStats(final CollectUsedAbilitiesStats ability) {
            super(ability);
            this.usedStats = ability.usedStats; // must be shared, so use ref instead copy
        }

        @Override
        public CollectUsedAbilitiesStats copy() {
            return new CollectUsedAbilitiesStats(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            // must take only real activates (not simulation)
            if (game.isSimulation()) {
                return false;
            }

            return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY
                    || event.getType() == GameEvent.EventType.SPELL_CAST;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
            if (stackObject != null) {
                this.usedStats.add(stackObject.getStackAbility().toString());
            }

            return false;
        }

        @Override
        public String getRule() {
            return "Used abilities stats";
        }
    }
}
