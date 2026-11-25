package org.mage.test.combat;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * 506.3a
 * If an effect would put a noncreature permanent onto the battlefield attacking or blocking, the permanent does enter
 * the battlefield but it’s never considered to be an attacking or blocking permanent.
 * <p>
 * 506.3b
 * If an effect would put a creature onto the battlefield attacking under the control of any player except an attacking
 * player, that creature does enter the battlefield, but it’s never considered to be an attacking creature.
 * <p>
 * 506.3c
 * If an effect would put a creature onto the battlefield attacking either a player not in the game or a permanent
 * that’s no longer on the battlefield or isn’t either a planeswalker or a battle, that creature does enter
 * the battlefield, but it’s never considered to be an attacking creature. See rule 508.4a.
 * <p>
 * 508.4
 * If a creature is put onto the battlefield attacking, its controller chooses which defending player, planeswalker
 * a defending player controls, or battle a defending player protects it’s attacking as it enters the battlefield
 * (unless the effect that put it onto the battlefield specifies what it’s attacking). Similarly, if an effect states
 * that a creature is attacking, its controller chooses which defending player, planeswalker a defending player
 * controls, or battle a defending player protects it’s attacking (unless the effect has already specified). Such
 * creatures are “attacking” but, for the purposes of trigger events and effects, they never “attacked.” They remain
 * attacking creatures until they’re removed from combat or the combat phase ends, whichever comes first.
 *
 * @author JayDi85
 */
public class EnterAttackingTest extends CardTestPlayerBase {

    @Test
    public void test_EnterAttacking_ControllerChoosesDefender() {
        // During combat, permanents enter tapped and attacking.
        addCustomCardWithAbility("enter attacking", playerA, new SimpleStaticAbility(new EnterAttackingTestEffect(null)));
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.HAND, playerA, "Ambush Viper"); // {1}{G}, 2/1, flash
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Ambush Viper");
        addTarget(playerA, "Jace Beleren"); // defender for Ambush Viper

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Ambush Viper", true);
        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 3 - 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_EnterAttacking_EffectSetsPlaneswalker() {
        // During combat, permanents enter tapped and attacking Jace Beleren.
        addCustomCardWithAbility("enter attacking", playerA, new SimpleStaticAbility(new EnterAttackingTestEffect("Jace Beleren")));
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.HAND, playerA, "Ambush Viper"); // {1}{G}, 2/1, flash
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Ambush Viper");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Ambush Viper", true);
        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 3 - 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_EnterAttacking_EffectSetsPlayer() {
        // During combat, permanents enter tapped and attacking PlayerB.
        addCustomCardWithAbility("enter attacking", playerA, new SimpleStaticAbility(new EnterAttackingTestEffect("PlayerB")));
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.HAND, playerA, "Ambush Viper"); // {1}{G}, 2/1, flash
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Ambush Viper");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Ambush Viper", true);
        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 3);
        assertLife(playerB, 20 - 2 - 2);
    }

    @Test
    public void test_EnterAttacking_EffectSetsPlaneswalker_Tokens() {
        // During combat, permanents enter tapped and attacking Jace Beleren.
        addCustomCardWithAbility("enter attacking", playerA, new SimpleStaticAbility(new EnterAttackingTestEffect("Jace Beleren")));
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        // Create two 1/1 white Soldier creature tokens.
        addCard(Zone.HAND, playerA, "Raise the Alarm"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Raise the Alarm");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTappedCount("Soldier Token", true, 2);
        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 3 - 1 - 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_EnterAttackingButNoncreature() {
        // During combat, permanents enter tapped and attacking.
        addCustomCardWithAbility("enter attacking", playerA, new SimpleStaticAbility(new EnterAttackingTestEffect(null)));
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        // As an additional cost to cast this spell, discard a card.
        // Draw two cards and create two Treasure tokens.
        addCard(Zone.HAND, playerA, "Big Score"); // {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Island"); // to discard

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Big Score");
        setChoice(playerA, "Island"); // discard cost

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTappedCount("Treasure Token", true, 2);
        assertAttacking("Treasure Token", false);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_EnterAttackingButNotAttackingPlayer() {
        // During combat, permanents enter tapped and attacking.
        addCustomCardWithAbility("enter attacking", playerA, new SimpleStaticAbility(new EnterAttackingTestEffect(null)));
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.HAND, playerB, "Ambush Viper"); // {1}{G}, 2/1, flash
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerB, "Ambush Viper");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Ambush Viper", true);
        assertAttacking("Ambush Viper", false);
        assertLife(playerB, 20 - 2);
        assertLife(playerA, 20);
    }

    @Test
    public void test_EnterAttackingButNotPlaneswalkerOrBattle() {
        // During combat, permanents enter tapped and attacking Silvercoat Lion.
        addCustomCardWithAbility("enter attacking", playerA, new SimpleStaticAbility(new EnterAttackingTestEffect("Silvercoat Lion")));
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.HAND, playerA, "Ambush Viper"); // {1}{G}, 2/1, flash
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // creature 2/2

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Ambush Viper");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Ambush Viper", true);
        assertAttacking("Ambush Viper", false);
        assertLife(playerB, 20 - 2);
    }
}

/**
 * During combat, permanents enter tapped and attacking.
 * Defender: null - the controller chooses it, a player name or a permanent name.
 */
class EnterAttackingTestEffect extends ReplacementEffectImpl {

    private final String defender;

    EnterAttackingTestEffect(String defender) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false);
        this.defender = defender;
        staticText = "during combat, permanents enter tapped and attacking" + (defender == null ? "" : " " + defender);
    }

    private EnterAttackingTestEffect(final EnterAttackingTestEffect effect) {
        super(effect);
        this.defender = effect.defender;
    }

    @Override
    public EnterAttackingTestEffect copy() {
        return new EnterAttackingTestEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getTurnPhaseType() == TurnPhase.COMBAT;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        permanent.setTapped(true);
        permanent.setEntersAttacking(findDefenders(permanent, game));
        return false;
    }

    private Set<UUID> findDefenders(Permanent permanent, Game game) {
        if (defender == null) {
            return game.getCombat().getPossibleDefenders(permanent.getId(), null, game);
        }
        for (Player player : game.getPlayers().values()) {
            if (player.getName().equals(defender)) {
                return Collections.singleton(player.getId());
            }
        }
        return Collections.singleton(game.getBattlefield().getAllPermanents()
                .stream()
                .filter(p -> p.getName().equals(defender))
                .map(Permanent::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Can't find " + defender)));
    }
}
