
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jeffwadsworth
 */
public final class Fallowsage extends CardImpl {

    public Fallowsage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Fallowsage becomes tapped, you may draw a card.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), true));
    }

    private Fallowsage(final Fallowsage card) {
        super(card);
    }

    @Override
    public Fallowsage copy() {
        return new Fallowsage(this);
    }
}
