
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class SaprazzanRaider extends CardImpl {

    public SaprazzanRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Saprazzan Raider becomes blocked, return it to its owner's hand.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new ReturnToHandSourceEffect(), false));
    }

    private SaprazzanRaider(final SaprazzanRaider card) {
        super(card);
    }

    @Override
    public SaprazzanRaider copy() {
        return new SaprazzanRaider(this);
    }
}
