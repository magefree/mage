
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class HazoretsFavor extends CardImpl {

    public HazoretsFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At the beginning of combat on your turn, you may have target creature you control get +2/+0 and gain haste until end of turn.
        // If you do, sacrifice it at the beginning of the next end step.
        Effect effect = new BoostTargetEffect(2, 0, Duration.EndOfTurn);
        effect.setText("you may have target creature you control get +2/+0");
        Ability ability = new BeginningOfCombatTriggeredAbility(effect, TargetController.YOU, true);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText(" and gain haste until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addEffect(new HazoretsFavorSacrificeEffect());
        this.addAbility(ability);
    }

    private HazoretsFavor(final HazoretsFavor card) {
        super(card);
    }

    @Override
    public HazoretsFavor copy() {
        return new HazoretsFavor(this);
    }

    private static class HazoretsFavorSacrificeEffect extends OneShotEffect {

        public HazoretsFavorSacrificeEffect() {
            super(Outcome.Sacrifice);
            this.staticText = "If you do, sacrifice it at the beginning of the next end step";
        }

        private HazoretsFavorSacrificeEffect(final HazoretsFavorSacrificeEffect effect) {
            super(effect);
        }

        @Override
        public HazoretsFavorSacrificeEffect copy() {
            return new HazoretsFavorSacrificeEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (creature != null) {
                SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("Sacrifice boosted " + creature.getName(), source.getControllerId());
                sacrificeEffect.setTargetPointer(new FixedTarget(creature, game));
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
    }
}
