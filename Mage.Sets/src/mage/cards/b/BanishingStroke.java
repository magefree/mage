package mage.cards.b;

import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class BanishingStroke extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or enchantment");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public BanishingStroke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{W}");


        // Put target artifact, creature, or enchantment on the bottom of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Miracle {W}
        this.addAbility(new MiracleAbility("{W}"));
    }

    private BanishingStroke(final BanishingStroke card) {
        super(card);
    }

    @Override
    public BanishingStroke copy() {
        return new BanishingStroke(this);
    }
}
