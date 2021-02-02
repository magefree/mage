
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class SibsigHost extends CardImpl {

    public SibsigHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // When Sibsig Host enters the battlefield, each player puts the top three cards of their library into their graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsEachPlayerEffect(3, TargetController.ANY)));
    }

    private SibsigHost(final SibsigHost card) {
        super(card);
    }

    @Override
    public SibsigHost copy() {
        return new SibsigHost(this);
    }
}
