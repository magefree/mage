
package mage.cards.v;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author maxlebedev
 */
public final class VernalEquinox extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature and enchantment spells");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public VernalEquinox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Any player may cast creature and enchantment spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter, true)));

    }

    public VernalEquinox(final VernalEquinox card) {
        super(card);
    }

    @Override
    public VernalEquinox copy() {
        return new VernalEquinox(this);
    }
}
