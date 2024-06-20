package mage.cards.l;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author notgreat
 */
public final class LastNightTogether extends CardImpl {

    public LastNightTogether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{G}");

        // Choose two target creatures. Untap them. Put two +1/+1 counters on each of them. They gain vigilance, indestructible, and haste until end of turn. After this main phase, there is an additional combat phase. Only the chosen creatures can attack during that combat phase.
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Choose two target creatures. Untap them"));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)).setText("Put two +1/+1 counters on each of them"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance()).setText("They gain vigilance"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()).setText("indestructible").concatBy(","));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()).setText("haste until end of turn").concatBy(", and"));
        this.getSpellAbility().addEffect(new LastNightTogetherEffect());

        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));

    }

    private LastNightTogether(final LastNightTogether card) {
        super(card);
    }

    @Override
    public LastNightTogether copy() {
        return new LastNightTogether(this);
    }
}

class LastNightTogetherEffect extends OneShotEffect {

    LastNightTogetherEffect() {
        super(Outcome.Benefit);
        staticText = "After this main phase, there is an additional combat phase. Only the chosen creatures can attack during that combat phase";
    }

    private LastNightTogetherEffect(final LastNightTogetherEffect effect) {
        super(effect);
    }

    @Override
    public LastNightTogetherEffect copy() {
        return new LastNightTogetherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // 15.07.2006 If it's somehow not a main phase when Fury of the Horde resolves, all it does is untap all creatures that attacked that turn. No new phases are created.
        // Same ruling applies here
        if (game.getTurnPhaseType() == TurnPhase.PRECOMBAT_MAIN || game.getTurnPhaseType() == TurnPhase.POSTCOMBAT_MAIN) {
            // At the start of that combat, add a restriction effect preventing other creatures from attacking.
            TurnMod combat = new TurnMod(game.getState().getActivePlayerId()).withExtraPhase(TurnPhase.COMBAT);
            game.getState().getTurnMods().add(combat);
            List<UUID> targets = source.getTargets().get(0).getTargets();
            DelayedCantAttackAbility delayedTriggeredAbility = new DelayedCantAttackAbility(targets, game, combat.getId());
            game.addDelayedTriggeredAbility(delayedTriggeredAbility, source);
            return true;
        }
        return true;
    }

}

class DelayedCantAttackAbility extends DelayedTriggeredAbility {

    private UUID connectedTurnMod;

    public DelayedCantAttackAbility(List<UUID> targets, Game game, UUID connectedTurnMod) {
        super(new LastNightTogetherRestrictionEffect(targets, game), Duration.EndOfCombat);
        this.usesStack = false; // don't show this to the user
        this.connectedTurnMod = connectedTurnMod;
    }

    public DelayedCantAttackAbility(DelayedCantAttackAbility ability) {
        super(ability);
        this.connectedTurnMod = ability.connectedTurnMod;
    }

    @Override
    public DelayedCantAttackAbility copy() {
        return new DelayedCantAttackAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_CHANGED || event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.PHASE_CHANGED && this.connectedTurnMod.equals(event.getSourceId()));
    }

    @Override
    public String getRule() {
        return "Only the chosen creatures can attack during that combat phase";
    }
}

class LastNightTogetherRestrictionEffect extends RestrictionEffect {

    private final List<MageObjectReference> targets;

    public LastNightTogetherRestrictionEffect(List<UUID> targets, Game game) {
        super(Duration.EndOfCombat, Outcome.Benefit);
        this.targets = targets.stream().map(x -> new MageObjectReference(x, game)).collect(Collectors.toList());
        staticText = "Only the chosen creatures can attack during that combat phase";
    }

    private LastNightTogetherRestrictionEffect(final LastNightTogetherRestrictionEffect effect) {
        super(effect);
        this.targets = effect.targets;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return targets.stream().noneMatch(x -> x.refersTo(permanent, game));
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public LastNightTogetherRestrictionEffect copy() {
        return new LastNightTogetherRestrictionEffect(this);
    }

}