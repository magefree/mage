package mage.cards.f;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrostBite extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 2);

    public FrostBite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        this.supertype.add(SuperType.SNOW);

        // Frost Bite deals 2 damage to target creature or planeswalker. If you control three or more snow permanents, it deals 3 damage instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(3), new DamageTargetEffect(2),
                condition, "{this} deals 2 damage to target creature or planeswalker. " +
                "If you control three or more snow permanents, it deals 3 damage instead"
        ));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private FrostBite(final FrostBite card) {
        super(card);
    }

    @Override
    public FrostBite copy() {
        return new FrostBite(this);
    }
}
