
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author TheElk801
 */
public final class RockSlide extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking or blocking creatures without flying");

    static {
        filter.add(Predicates.or(
                new AttackingPredicate(),
                new BlockingPredicate()
        ));
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public RockSlide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // Rock Slide deals X damage divided as you choose among any number of target attacking or blocking creatures without flying.
        DynamicValue xValue = new ManacostVariableValue();
        this.getSpellAbility().addEffect(new DamageMultiEffect(xValue));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(xValue, filter));
    }

    public RockSlide(final RockSlide card) {
        super(card);
    }

    @Override
    public RockSlide copy() {
        return new RockSlide(this);
    }
}
