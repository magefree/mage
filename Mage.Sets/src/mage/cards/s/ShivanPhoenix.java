
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class ShivanPhoenix extends CardImpl {

    public ShivanPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.PHOENIX);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Shivan Phoenix dies, return Shivan Phoenix to its owner's hand.
        this.addAbility(new DiesSourceTriggeredAbility(new ReturnToHandSourceEffect()).withRuleTextReplacement(false));
    }

    private ShivanPhoenix(final ShivanPhoenix card) {
        super(card);
    }

    @Override
    public ShivanPhoenix copy() {
        return new ShivanPhoenix(this);
    }
}
