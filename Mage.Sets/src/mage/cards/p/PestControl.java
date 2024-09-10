package mage.cards.p;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PestControl extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanents with mana value 1 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public PestControl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{B}");

        // Destroy all nonland permanents with mana value 1 or less.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private PestControl(final PestControl card) {
        super(card);
    }

    @Override
    public PestControl copy() {
        return new PestControl(this);
    }
}
