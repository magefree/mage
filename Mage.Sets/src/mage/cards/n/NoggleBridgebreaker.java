
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 * @author Loki
 */
public final class NoggleBridgebreaker extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public NoggleBridgebreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U/R}{U/R}");
        this.subtype.add(SubType.NOGGLE);
        this.subtype.add(SubType.ROGUE);


        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Noggle Bridgebreaker enters the battlefield, return a land you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter), false));
    }

    private NoggleBridgebreaker(final NoggleBridgebreaker card) {
        super(card);
    }

    @Override
    public NoggleBridgebreaker copy() {
        return new NoggleBridgebreaker(this);
    }
}
