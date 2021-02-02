
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public final class Revive extends CardImpl {

    private static final FilterCard filter = new FilterCard("green card from your graveyard");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public Revive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // Return target green card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private Revive(final Revive card) {
        super(card);
    }

    @Override
    public Revive copy() {
        return new Revive(this);
    }
}
