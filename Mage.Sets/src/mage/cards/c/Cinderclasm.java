package mage.cards.c;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Cinderclasm extends CardImpl {

    public Cinderclasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));

        // Cinderclasm deals 1 damage to each creature. If it was kicked, it deals 2 damage to each creature instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageAllEffect(2, StaticFilters.FILTER_PERMANENT_CREATURE),
                new DamageAllEffect(1, StaticFilters.FILTER_PERMANENT_CREATURE),
                KickedCondition.ONCE, "{this} deals 1 damage to each creature. " +
                "If it was kicked, it deals 2 damage to each creature instead"
        ));
    }

    private Cinderclasm(final Cinderclasm card) {
        super(card);
    }

    @Override
    public Cinderclasm copy() {
        return new Cinderclasm(this);
    }
}
