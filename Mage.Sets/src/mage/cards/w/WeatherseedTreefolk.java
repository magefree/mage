
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class WeatherseedTreefolk extends CardImpl {

    public WeatherseedTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Weatherseed Treefolk dies, return it to its owner's hand.
        this.addAbility(new DiesSourceTriggeredAbility(new ReturnToHandSourceEffect()));
    }

    private WeatherseedTreefolk(final WeatherseedTreefolk card) {
        super(card);
    }

    @Override
    public WeatherseedTreefolk copy() {
        return new WeatherseedTreefolk(this);
    }
}
