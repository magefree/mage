
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class ReturnedReveler extends CardImpl {

    public ReturnedReveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SATYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Returned Reveler dies, each player puts the top three cards of their library into their graveyard.
        this.addAbility(new DiesSourceTriggeredAbility(new MillCardsEachPlayerEffect(3, TargetController.ANY)));
    }

    public ReturnedReveler(final ReturnedReveler card) {
        super(card);
    }

    @Override
    public ReturnedReveler copy() {
        return new ReturnedReveler(this);
    }
}
