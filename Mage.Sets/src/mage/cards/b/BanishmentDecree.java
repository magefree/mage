

package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class BanishmentDecree extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or enchantment");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    public BanishmentDecree (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}{W}");

        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private BanishmentDecree(final BanishmentDecree card) {
        super(card);
    }

    @Override
    public BanishmentDecree copy() {
        return new BanishmentDecree(this);
    }

}
