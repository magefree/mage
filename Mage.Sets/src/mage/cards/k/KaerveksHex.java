package mage.cards.k;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class KaerveksHex extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public KaerveksHex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Kaervek's Hex deals 1 damage to each nonblack creature and an additional 1 damage to each green creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter).setText("and an additional 1 damage to each green creature"));
    }

    private KaerveksHex(final KaerveksHex card) {
        super(card);
    }

    @Override
    public KaerveksHex copy() {
        return new KaerveksHex(this);
    }
}
