
package mage.cards.h;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Plopman
 */
public final class Hibernation extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("green permanents");
    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public Hibernation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");


        // Return all green permanents to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(filter));
    }

    private Hibernation(final Hibernation card) {
        super(card);
    }

    @Override
    public Hibernation copy() {
        return new Hibernation(this);
    }
}
