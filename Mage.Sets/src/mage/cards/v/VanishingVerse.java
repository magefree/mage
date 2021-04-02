package mage.cards.v;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.MonocoloredPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VanishingVerse extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("monocolored permanent");

    static {
        filter.add(MonocoloredPredicate.instance);
    }

    public VanishingVerse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{B}");

        // Exile target monocolored permanent.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private VanishingVerse(final VanishingVerse card) {
        super(card);
    }

    @Override
    public VanishingVerse copy() {
        return new VanishingVerse(this);
    }
}
