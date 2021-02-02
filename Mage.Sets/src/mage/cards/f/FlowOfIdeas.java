
package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author North
 */
public final class FlowOfIdeas extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Island you control");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public FlowOfIdeas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{U}");


        // Draw a card for each Island you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)));
    }

    private FlowOfIdeas(final FlowOfIdeas card) {
        super(card);
    }

    @Override
    public FlowOfIdeas copy() {
        return new FlowOfIdeas(this);
    }
}
