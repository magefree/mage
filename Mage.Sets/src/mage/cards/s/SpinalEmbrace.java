package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SpinalEmbrace extends CardImpl {

    public SpinalEmbrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}{B}");

        // Cast Spinal Embrace only during combat.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT));

        // Untap target creature you don't control and gain control of it. It gains haste until end of turn. At the beginning of the next end step, sacrifice it. If you do, you gain life equal to its toughness.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        Effect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setText("and gain control of it");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("It gains haste until end of turn"));
        this.getSpellAbility().addEffect(new SpinalEmbraceAddDelayedEffect());
    }

    private SpinalEmbrace(final SpinalEmbrace card) {
        super(card);
    }

    @Override
    public SpinalEmbrace copy() {
        return new SpinalEmbrace(this);
    }
}

class SpinalEmbraceAddDelayedEffect extends OneShotEffect {

    SpinalEmbraceAddDelayedEffect() {
        super(Outcome.Sacrifice);
        staticText = "At the beginning of the next end step, sacrifice it. If you do, you gain life equal to its toughness";
    }

    private SpinalEmbraceAddDelayedEffect(final SpinalEmbraceAddDelayedEffect effect) {
        super(effect);
    }

    @Override
    public SpinalEmbraceAddDelayedEffect copy() {
        return new SpinalEmbraceAddDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SpinalEmbraceSacrificeEffect sacrificeEffect = new SpinalEmbraceSacrificeEffect();
        sacrificeEffect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}

class SpinalEmbraceSacrificeEffect extends OneShotEffect {

    SpinalEmbraceSacrificeEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice it. If you do, you gain life equal to its toughness";
    }

    private SpinalEmbraceSacrificeEffect(final SpinalEmbraceSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public SpinalEmbraceSacrificeEffect copy() {
        return new SpinalEmbraceSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                permanent.sacrifice(source, game);
                affectedTargets++;
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    controller.gainLife(permanent.getPower().getValue(), game, source);
                }
            }
        }
        return affectedTargets > 0;
    }
}
