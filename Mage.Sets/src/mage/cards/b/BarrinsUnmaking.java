
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MostCommonColorCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class BarrinsUnmaking extends CardImpl {

    public BarrinsUnmaking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target permanent to its owner's hand if that permanent shares a color with the most common color among all permanents or a color tied for most common.
        this.getSpellAbility().addEffect(new BarrinsUnmakingEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private BarrinsUnmaking(final BarrinsUnmaking card) {
        super(card);
    }

    @Override
    public BarrinsUnmaking copy() {
        return new BarrinsUnmaking(this);
    }
}

class BarrinsUnmakingEffect extends OneShotEffect {

    public BarrinsUnmakingEffect() {
        super(Outcome.Detriment);
        this.staticText = "Return target permanent to its owner's hand if that permanent shares a color with the most common color among all permanents or a color tied for most common.";
    }

    private BarrinsUnmakingEffect(final BarrinsUnmakingEffect effect) {
        super(effect);
    }

    @Override
    public BarrinsUnmakingEffect copy() {
        return new BarrinsUnmakingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            Condition condition = new MostCommonColorCondition(permanent.getColor(game));
            if (condition.apply(game, source)) {
                Effect effect = new ReturnToHandTargetEffect();
                effect.setTargetPointer(new FixedTarget(permanent, game));
                return effect.apply(game, source);
            }
        }
        return false;
    }
}
