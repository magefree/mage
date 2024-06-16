package mage.cards.l;

import mage.MageObjectReference;
import mage.abilities.Ability;
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
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.Phase;
import mage.game.turn.TurnMod;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.Objects;
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
        TurnMod combat = new TurnMod(source.getControllerId()).withExtraPhase(TurnPhase.COMBAT, TurnPhase.POSTCOMBAT_MAIN);
        game.getState().getTurnMods().add(combat);
        List<UUID> targets = source.getTargets().get(0).getTargets();
        Phase phase = game.getTurn().getPhase();
        game.addEffect(new LastNightTogetherRestrictionEffect(targets, phase, game), source);
        return true;
    }

}

class LastNightTogetherRestrictionEffect extends RestrictionEffect {

    private final List<MageObjectReference> targets;
    private final Phase phase;

    public LastNightTogetherRestrictionEffect(List<UUID> targets, Phase phase, Game game) {
        super(Duration.Custom, Outcome.Benefit);
        this.targets = targets.stream().map(x -> new MageObjectReference(x, game)).collect(Collectors.toList());
        this.phase = phase;
        staticText = "Only the chosen creatures can attack during that combat phase";
    }

    private LastNightTogetherRestrictionEffect(final LastNightTogetherRestrictionEffect effect) {
        super(effect);
        this.targets = effect.targets;
        this.phase = effect.phase;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return targets.stream().noneMatch(x -> x.refersTo(permanent, game)) && !Objects.equals(game.getTurn().getPhase(), phase);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        //End the effect at either the end of that combat step, or during the cleanup step (if the combat is skipped)
        if (game.getTurnStepType() == PhaseStep.END_COMBAT || game.getTurnStepType() == PhaseStep.CLEANUP) {
            return !Objects.equals(game.getTurn().getPhase(), phase);
        }
        return false;
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