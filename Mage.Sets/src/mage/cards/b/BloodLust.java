
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author jeffwadsworth
 */
public final class BloodLust extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 4));
    }

    public BloodLust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // If target creature has toughness 5 or greater, it gets +4/-4 until end of turn. Otherwise, it gets +4/-X until end of turn, where X is its toughness minus 1.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostTargetEffect(4, -4, Duration.EndOfTurn),
                new BoostTargetEffect(StaticValue.get(4), new SignInversionDynamicValue(TargetPermanentToughnessMinus1Value.instance), Duration.WhileOnBattlefield),
                new TargetMatchesFilterCondition(filter),
                "If target creature has toughness 5 or greater, it gets +4/-4 until end of turn. Otherwise, it gets +4/-X until end of turn, where X is its toughness minus 1"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private BloodLust(final BloodLust card) {
        super(card);
    }

    @Override
    public BloodLust copy() {
        return new BloodLust(this);
    }
}

class TargetMatchesFilterCondition implements Condition {

    private final FilterPermanent filter;

    public TargetMatchesFilterCondition(FilterPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getBattlefield().getPermanent(source.getFirstTarget());
        if (target != null) {
            if (filter.match(target, source.getControllerId(), source, game)) {
                return true;
            }
        }
        return false;
    }
}

enum TargetPermanentToughnessMinus1Value implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent target = game.getPermanent(sourceAbility.getFirstTarget());
        if (target != null) {
            return target.getToughness().getValue() - 1;
        }
        return 0;
    }

    @Override
    public TargetPermanentToughnessMinus1Value copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "target creature's toughness minus 1";
    }
}
