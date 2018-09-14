package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;

/**
 *
 * @author TheElk801
 */
public final class RitualOfSoot extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures with converted mana cost 3 or less");

    static {
        filter.add(new ConvertedManaCostPredicate(
                ComparisonType.FEWER_THAN, 4
        ));
    }

    public RitualOfSoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Destroy all creatures with converted mana cost 3 or less.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    public RitualOfSoot(final RitualOfSoot card) {
        super(card);
    }

    @Override
    public RitualOfSoot copy() {
        return new RitualOfSoot(this);
    }
}
