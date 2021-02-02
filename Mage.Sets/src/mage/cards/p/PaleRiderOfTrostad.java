
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class PaleRiderOfTrostad extends CardImpl {

    public PaleRiderOfTrostad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Skulk
        this.addAbility(new SkulkAbility());

        // When Pale Rider of Trostad enters the battlefield, discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardControllerEffect(1), false));
    }

    private PaleRiderOfTrostad(final PaleRiderOfTrostad card) {
        super(card);
    }

    @Override
    public PaleRiderOfTrostad copy() {
        return new PaleRiderOfTrostad(this);
    }
}
