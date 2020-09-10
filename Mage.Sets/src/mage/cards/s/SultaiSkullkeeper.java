
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class SultaiSkullkeeper extends CardImpl {

    public SultaiSkullkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Sultai Skullkeeper enters the battlefield, put the top two cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2)));
    }

    public SultaiSkullkeeper(final SultaiSkullkeeper card) {
        super(card);
    }

    @Override
    public SultaiSkullkeeper copy() {
        return new SultaiSkullkeeper(this);
    }
}
