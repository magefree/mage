package mage.cards.a;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Loki
 */
public final class AkromasVengeance extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts, creatures, and enchantments");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public AkromasVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        this.addAbility(new CyclingAbility(new GenericManaCost(3)));
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private AkromasVengeance(final AkromasVengeance card) {
        super(card);
    }

    @Override
    public AkromasVengeance copy() {
        return new AkromasVengeance(this);
    }
}
