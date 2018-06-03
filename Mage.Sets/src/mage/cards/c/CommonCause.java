
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class CommonCause extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Nonartifact creatures");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
    }

    public CommonCause(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Nonartifact creatures get +2/+2 as long as they all share a color.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, filter, false),
                new AllColorCondition(),
                "nonartifact creatures get +2/+2 as long as they all share a color.")));
    }

    public CommonCause(final CommonCause card) {
        super(card);
    }

    @Override
    public CommonCause copy() {
        return new CommonCause(this);
    }
}

class AllColorCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent("Nonartifact creatures");
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
        ObjectColor allColor = new ObjectColor("WUBRG");
        for (Permanent thing : game.getBattlefield().getAllActivePermanents(filter, game)) {
             allColor = allColor.intersection(thing.getColor(game));
        }
        return !allColor.isColorless();
    }
}
