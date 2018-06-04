
package mage.cards.s;

import java.util.UUID;
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
import mage.constants.TargetController;
import mage.constants.TurnPhase;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class SpinalEmbrace extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public SpinalEmbrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}{B}");

        // Cast Spinal Embrace only during combat.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT));

        // Untap target creature you don't control and gain control of it. It gains haste until end of turn. At the beginning of the next end step, sacrifice it. If you do, you gain life equal to its toughness.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        Effect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setText("and gain control of it");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("It gains haste until end of turn"));
        this.getSpellAbility().addEffect(new SpinalEmbraceAddDelayedEffect());
    }

    public SpinalEmbrace(final SpinalEmbrace card) {
        super(card);
    }

    @Override
    public SpinalEmbrace copy() {
        return new SpinalEmbrace(this);
    }
}

class SpinalEmbraceAddDelayedEffect extends OneShotEffect {

    public SpinalEmbraceAddDelayedEffect() {
        super(Outcome.Sacrifice);
        staticText = "At the beginning of the next end step, sacrifice it. If you do, you gain life equal to its toughness";
    }

    public SpinalEmbraceAddDelayedEffect(final SpinalEmbraceAddDelayedEffect effect) {
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

    public SpinalEmbraceSacrificeEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice it. If you do, you gain life equal to its toughness";
    }

    public SpinalEmbraceSacrificeEffect(final SpinalEmbraceSacrificeEffect effect) {
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
                permanent.sacrifice(source.getSourceId(), game);
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
