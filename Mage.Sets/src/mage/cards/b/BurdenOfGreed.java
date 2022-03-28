
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.target.TargetPlayer;

/**
 *
 * @author wetterlicht
 */
public final class BurdenOfGreed extends CardImpl {

    public BurdenOfGreed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}");

        // Target player loses 1 life for each tapped artifact they control.
        getSpellAbility().addTarget(new TargetPlayer());
        getSpellAbility().addEffect(new LoseLifeTargetEffect(new BurdenOfGreedCount()));

    }

    private BurdenOfGreed(final BurdenOfGreed card) {
        super(card);
    }

    @Override
    public BurdenOfGreed copy() {
        return new BurdenOfGreed(this);
    }
}

class BurdenOfGreedCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility.getFirstTarget() == null) {
            return 0;
        }
        FilterArtifactPermanent filter = new FilterArtifactPermanent();
        filter.add(TappedPredicate.TAPPED);
        filter.add(new ControllerIdPredicate(sourceAbility.getFirstTarget()));
        return game.getBattlefield().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
    }

    @Override
    public BurdenOfGreedCount copy() {
        return new BurdenOfGreedCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "tapped artifact they control";
    }

}
