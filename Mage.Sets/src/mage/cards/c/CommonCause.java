
package mage.cards.c;

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
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommonCause extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Nonartifact creatures");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public CommonCause(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Nonartifact creatures get +2/+2 as long as they all share a color.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, filter, false),
                AllColorCondition.instance,
                "nonartifact creatures get +2/+2 as long as they all share a color.")
        ));
    }

    private CommonCause(final CommonCause card) {
        super(card);
    }

    @Override
    public CommonCause copy() {
        return new CommonCause(this);
    }
}

enum AllColorCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent("Nonartifact creatures");
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        ObjectColor allColor = new ObjectColor("WUBRG");
        for (Permanent thing : game.getBattlefield().getAllActivePermanents(filter, game)) {
            allColor = allColor.intersection(thing.getColor(game));
        }
        return !allColor.isColorless();
    }
}
