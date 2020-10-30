
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author noxx
 */
public final class NecromancersAssistant extends CardImpl {

    public NecromancersAssistant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Necromancer's Assistant enters the battlefield, put the top three cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3)));
    }

    public NecromancersAssistant(final NecromancersAssistant card) {
        super(card);
    }

    @Override
    public NecromancersAssistant copy() {
        return new NecromancersAssistant(this);
    }
}
