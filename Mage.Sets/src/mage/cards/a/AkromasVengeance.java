
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author Loki
 */
public final class AkromasVengeance extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts, creatures, and enchantments");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public AkromasVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");

        this.addAbility(new CyclingAbility(new ManaCostsImpl("{3}")));
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    public AkromasVengeance(final AkromasVengeance card) {
        super(card);
    }

    @Override
    public AkromasVengeance copy() {
        return new AkromasVengeance(this);
    }
}
