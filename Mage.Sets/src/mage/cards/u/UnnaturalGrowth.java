package mage.cards.u;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class UnnaturalGrowth extends CardImpl {

    public UnnaturalGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}{G}{G}");

        // At the beginning of each combat, double the power and toughness of each creature you control until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new UnnaturalGrowthEffect(), TargetController.ANY, false));
    }

    private UnnaturalGrowth(final UnnaturalGrowth card) {
        super(card);
    }

    @Override
    public UnnaturalGrowth copy() {
        return new UnnaturalGrowth(this);
    }
}

class UnnaturalGrowthEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    public UnnaturalGrowthEffect() {
        super(Outcome.BoostCreature);
        staticText = "double the power and toughness of each creature you control until end of turn";
    }

    private UnnaturalGrowthEffect(final UnnaturalGrowthEffect effect) {
        super(effect);
    }

    @Override
    public UnnaturalGrowthEffect copy() {
        return new UnnaturalGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            ContinuousEffect effect = new BoostTargetEffect(permanent.getPower().getValue(), permanent.getToughness().getValue());
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
