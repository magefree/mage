package mage.cards.r;

import mage.abilities.condition.common.BlightedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.BlightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RequitingHex extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with mana value 2 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public RequitingHex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // As an additional cost to cast this spell, you may blight 1.
        this.addAbility(new BlightAbility(1));

        // Destroy target creature with mana value 2 or less. If this spell's additional cost was paid, you gain 2 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(2), BlightedCondition.instance,
                "If this spell's additional cost was paid, you gain 2 life"
        ));
    }

    private RequitingHex(final RequitingHex card) {
        super(card);
    }

    @Override
    public RequitingHex copy() {
        return new RequitingHex(this);
    }
}
