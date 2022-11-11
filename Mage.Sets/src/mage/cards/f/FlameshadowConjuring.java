
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class FlameshadowConjuring extends CardImpl {

    public FlameshadowConjuring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Whenever a nontoken creature enters the battlefield under your control, you may pay {R}. If you do, create a token that's a copy of that creature. That token gains haste. Exile it at the beginning of the next end step.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new DoIfCostPaid(
                new FlameshadowConjuringEffect(), new ManaCostsImpl<>("{R}"), "Pay {R} to create a token that's a copy of that creature that entered the battlefield?"),
                StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, false, SetTargetPointer.PERMANENT,
                "Whenever a nontoken creature enters the battlefield under your control, "
                + "you may pay {R}. If you do, create a token that's a copy of that creature. "
                + "That token gains haste. Exile it at the beginning of the next end step");
        this.addAbility(ability);
    }

    private FlameshadowConjuring(final FlameshadowConjuring card) {
        super(card);
    }

    @Override
    public FlameshadowConjuring copy() {
        return new FlameshadowConjuring(this);
    }
}

class FlameshadowConjuringEffect extends OneShotEffect {

    public FlameshadowConjuringEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of that creature. That token gains haste. Exile it at the beginning of the next end step";
    }

    public FlameshadowConjuringEffect(final FlameshadowConjuringEffect effect) {
        super(effect);
    }

    @Override
    public FlameshadowConjuringEffect copy() {
        return new FlameshadowConjuringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, null, true);
            effect.setTargetPointer(getTargetPointer());
            if (effect.apply(game, source)) {
                for (Permanent tokenPermanent : effect.getAddedPermanents()) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
                return true;
            }
        }

        return false;
    }
}
