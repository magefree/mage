package mage.cards.b;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Berserk extends CardImpl {

    public Berserk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Cast Berserk only before the combat damage step. (Zone = all because it can be at least graveyard or hand)
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new BerserkReplacementEffect()), new CombatDamageStepStartedWatcher());

        // Target creature gains trample and gets +X/+0 until end of turn, where X is its power.
        // At the beginning of the next end step, destroy that creature if it attacked this turn.
        Effect effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Target creature gains trample");
        this.getSpellAbility().addEffect(effect);
        effect = new BoostTargetEffect(TargetPermanentPowerCount.instance, StaticValue.get(0), Duration.EndOfTurn, true);
        effect.setText("and gets +X/+0 until end of turn, where X is its power");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new BerserkDestroyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addWatcher(new AttackedThisTurnWatcher());

    }

    private Berserk(final Berserk card) {
        super(card);
    }

    @Override
    public Berserk copy() {
        return new Berserk(this);
    }
}

class BerserkReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    BerserkReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast this spell only before the combat damage step";
    }

    BerserkReplacementEffect(final BerserkReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL && event.getSourceId().equals(source.getSourceId())) {
            CombatDamageStepStartedWatcher watcher = game.getState().getWatcher(CombatDamageStepStartedWatcher.class);
            return watcher == null || watcher.conditionMet();
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public BerserkReplacementEffect copy() {
        return new BerserkReplacementEffect(this);
    }
}

class CombatDamageStepStartedWatcher extends Watcher {

    public CombatDamageStepStartedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        // if no damage happens, the first event after is END_COMBAT_STEP_PRE
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRE || event.getType() == GameEvent.EventType.END_COMBAT_STEP_PRE) {
            condition = true;
        }
    }

}

class BerserkDestroyEffect extends OneShotEffect {

    public BerserkDestroyEffect() {
        super(Outcome.Benefit);
        this.staticText = "At the beginning of the next end step, destroy that creature if it attacked this turn";
    }

    public BerserkDestroyEffect(final BerserkDestroyEffect effect) {
        super(effect);
    }

    @Override
    public BerserkDestroyEffect copy() {
        return new BerserkDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            //create delayed triggered ability
            Effect effect = new BerserkDelayedDestroyEffect();
            effect.setTargetPointer(new FixedTarget(this.getTargetPointer().getFirst(game, source), game));
            AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}

class BerserkDelayedDestroyEffect extends OneShotEffect {

    public BerserkDelayedDestroyEffect() {
        super(Outcome.Benefit);
        this.staticText = "destroy that creature if it attacked this turn";
    }

    public BerserkDelayedDestroyEffect(final BerserkDelayedDestroyEffect effect) {
        super(effect);
    }

    @Override
    public BerserkDelayedDestroyEffect copy() {
        return new BerserkDelayedDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
                if (watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(permanent, game))) {
                    return permanent.destroy(source, game, false);
                }
            }
        }
        return false;
    }
}
