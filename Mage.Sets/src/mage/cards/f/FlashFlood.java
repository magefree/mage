
package mage.cards.f;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class FlashFlood extends CardImpl {

    private static final FilterPermanent filter1 = new FilterPermanent("red permanent");
    private static final FilterPermanent filter2 = new FilterPermanent("Mountain");

    static {
        filter1.add(new ColorPredicate(ObjectColor.RED));
        filter2.add(SubType.MOUNTAIN.getPredicate());
    }

    public FlashFlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Choose one - Destroy target red permanent;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter1));
        // or return target Mountain to its owner's hand.
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetPermanent(filter2));
        this.getSpellAbility().addMode(mode);
    }

    private FlashFlood(final FlashFlood card) {
        super(card);
    }

    @Override
    public FlashFlood copy() {
        return new FlashFlood(this);
    }
}
