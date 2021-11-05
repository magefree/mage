package mage.cards.t;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThrabenExorcism extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Spirit, creature with disturb, or enchantment");

    static {
        filter.add(Predicates.or(
                SubType.SPIRIT.getPredicate(),
                Predicates.and(
                        CardType.CREATURE.getPredicate(),
                        new AbilityPredicate(DisturbAbility.class)
                ),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public ThrabenExorcism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target Spirit, creature with disturb, or enchantment.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private ThrabenExorcism(final ThrabenExorcism card) {
        super(card);
    }

    @Override
    public ThrabenExorcism copy() {
        return new ThrabenExorcism(this);
    }
}
