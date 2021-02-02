package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;

/**
 *
 * @author TheElk801
 */
public final class CitywideBust extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures with toughness 4 or greater");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 3));
    }

    public CitywideBust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Destroy all creatures with toughness 4 or greater.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private CitywideBust(final CitywideBust card) {
        super(card);
    }

    @Override
    public CitywideBust copy() {
        return new CitywideBust(this);
    }
}
