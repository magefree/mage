
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author North
 */
public final class BarterInBlood extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("creature");
    
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public BarterInBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");


        // Each player sacrifices two creatures.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(2, filter));
    }

    public BarterInBlood(final BarterInBlood card) {
        super(card);
    }

    @Override
    public BarterInBlood copy() {
        return new BarterInBlood(this);
    }
}