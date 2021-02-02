
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class DramaticEntrance extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a green creature card");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public DramaticEntrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}{G}");

        // You may put a green creature card from your hand onto the battlefield.
        this.getSpellAbility().addEffect(new PutCardFromHandOntoBattlefieldEffect(filter));

    }

    private DramaticEntrance(final DramaticEntrance card) {
        super(card);
    }

    @Override
    public DramaticEntrance copy() {
        return new DramaticEntrance(this);
    }
}
