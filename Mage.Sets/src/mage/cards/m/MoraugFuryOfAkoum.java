package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.watchers.Watcher;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoraugFuryOfAkoum extends CardImpl {

    public MoraugFuryOfAkoum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Each creature you control gets +1/+0 for each time it has attacked this turn.
        this.addAbility(new SimpleStaticAbility(new MoraugFuryOfAkoumBoostEffect()), new AttackedThisTurnWatcher());

        // Landfall — Whenever a land enters the battlefield under your control, if it's your main phase, there's an additional combat phase after this phase. At the beginning of that combat, untap all creatures you control.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new LandfallAbility(new MoraugFuryOfAkoumCombatEffect()), MoraugFuryOfAkoumCondition.instance,
                "<i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, " +
                        "if it's your main phase, there's an additional combat phase after this phase. " +
                        "At the beginning of that combat, untap all creatures you control."
        ), new MoraugFuryOfAkoumWatcher());
    }

    private MoraugFuryOfAkoum(final MoraugFuryOfAkoum card) {
        super(card);
    }

    @Override
    public MoraugFuryOfAkoum copy() {
        return new MoraugFuryOfAkoum(this);
    }
}

enum MoraugFuryOfAkoumCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.isActivePlayer(source.getControllerId()) && game.getPhase().getType().isMain();
    }
}

class MoraugFuryOfAkoumBoostEffect extends ContinuousEffectImpl {

    MoraugFuryOfAkoumBoostEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "each creature you control gets +1/+0 for each time it has attacked this turn";
    }

    private MoraugFuryOfAkoumBoostEffect(final MoraugFuryOfAkoumBoostEffect effect) {
        super(effect);
    }

    @Override
    public MoraugFuryOfAkoumBoostEffect copy() {
        return new MoraugFuryOfAkoumBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (permanent == null) {
                continue;
            }
            permanent.addPower(watcher.getAttackCount(permanent, game));
        }
        return true;
    }
}

class MoraugFuryOfAkoumCombatEffect extends OneShotEffect {

    MoraugFuryOfAkoumCombatEffect() {
        super(Outcome.Benefit);
    }

    private MoraugFuryOfAkoumCombatEffect(final MoraugFuryOfAkoumCombatEffect effect) {
        super(effect);
    }

    @Override
    public MoraugFuryOfAkoumCombatEffect copy() {
        return new MoraugFuryOfAkoumCombatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TurnPhase turnPhase = game.getPhase().getType();
        for (TurnMod turnMod : game.getState().getTurnMods()) {
            if ("moraug".equals(turnMod.getNote())
                    && turnMod.getPlayerId().equals(source.getControllerId())
                    && turnMod.getAfterPhase() == turnPhase) {
                turnPhase = TurnPhase.COMBAT;
                turnMod.setNote("moraugIgnore");
                break;
            }
        }
        TurnMod combat = new TurnMod(source.getControllerId(), TurnPhase.COMBAT, turnPhase, false);
        combat.setNote("moraug");
        game.getState().getTurnMods().add(combat);
        game.addDelayedTriggeredAbility(new MoraugFuryOfAkoumDelayedTriggeredAbility(combat.getId()), source);
        return true;
    }
}

class MoraugFuryOfAkoumDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID connectedTurnMod;
    private boolean enabled;

    MoraugFuryOfAkoumDelayedTriggeredAbility(UUID connectedTurnMod) {
        super(new UntapAllControllerEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ), Duration.EndOfTurn, true, false);
        this.connectedTurnMod = connectedTurnMod;
    }

    private MoraugFuryOfAkoumDelayedTriggeredAbility(MoraugFuryOfAkoumDelayedTriggeredAbility ability) {
        super(ability);
        this.connectedTurnMod = ability.connectedTurnMod;
        this.enabled = ability.enabled;
    }

    @Override
    public MoraugFuryOfAkoumDelayedTriggeredAbility copy() {
        return new MoraugFuryOfAkoumDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_CHANGED
                || event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.PHASE_CHANGED
                && this.connectedTurnMod.equals(event.getSourceId())) {
            enabled = true;
            return false;
        }
        if (event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE && enabled) {
            enabled = false;
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of that combat, untap all creatures you control";
    }
}

class MoraugFuryOfAkoumWatcher extends Watcher {

    MoraugFuryOfAkoumWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.POSTCOMBAT_MAIN_PHASE_PRE) {
            return;
        }
        for (TurnMod turnMod : game.getState().getTurnMods()) {
            if ("moraug".equals(turnMod.getNote())) {
                turnMod.setNote("moraugIgnore");
            }
        }
    }
}
