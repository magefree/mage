

package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.TargetSpell;

/**
 * @author Loki
 */
public final class Thoughtbind extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with converted mana cost 4 or less");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 5));
    }

    public Thoughtbind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    public Thoughtbind(final Thoughtbind card) {
        super(card);
    }

    @Override
    public Thoughtbind copy() {
        return new Thoughtbind(this);
    }

}
