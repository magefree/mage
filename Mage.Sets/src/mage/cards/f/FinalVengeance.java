package mage.cards.f;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FinalVengeance extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or enchantment");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public FinalVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast this spell, sacrifice a creature or enchantment.
        this.getSpellAbility().addCost(new SacrificeTargetCost(filter));

        // Exile target creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FinalVengeance(final FinalVengeance card) {
        super(card);
    }

    @Override
    public FinalVengeance copy() {
        return new FinalVengeance(this);
    }
}
