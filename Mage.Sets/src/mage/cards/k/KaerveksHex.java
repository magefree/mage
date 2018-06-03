
package mage.cards.k;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class KaerveksHex extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("green creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filter2.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public KaerveksHex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Kaervek's Hex deals 1 damage to each nonblack creature and an additional 1 damage to each green creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter));
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter2).setText("and an additional 1 damage to each green creature"));
    }

    public KaerveksHex(final KaerveksHex card) {
        super(card);
    }

    @Override
    public KaerveksHex copy() {
        return new KaerveksHex(this);
    }
}
