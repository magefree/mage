
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class FlameBurst extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                new NamePredicate("Flame Burst"),
                new AbilityPredicate(CountAsFlameBurstAbility.class)
        ));
    }

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            new CardsInAllGraveyardsCount(filter), StaticValue.get(2)
    );

    public FlameBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Flame Burst deals X damage to any target, where X is 2 plus the number of cards named Flame Burst in all graveyards.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue).setText("{this} deals X damage to any target, where X is 2 plus the number of cards named Flame Burst in all graveyards."));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private FlameBurst(final FlameBurst card) {
        super(card);
    }

    @Override
    public FlameBurst copy() {
        return new FlameBurst(this);
    }

    public static Ability getCountAsAbility() {
        return new CountAsFlameBurstAbility();
    }
}

class CountAsFlameBurstAbility extends SimpleStaticAbility {

    public CountAsFlameBurstAbility() {
        super(Zone.GRAVEYARD, new InfoEffect("If {this} is in a graveyard, effects from spells named Flame Burst count it as a card named Flame Burst"));
    }

    private CountAsFlameBurstAbility(CountAsFlameBurstAbility ability) {
        super(ability);
    }

    @Override
    public CountAsFlameBurstAbility copy() {
        return new CountAsFlameBurstAbility(this);
    }
}
