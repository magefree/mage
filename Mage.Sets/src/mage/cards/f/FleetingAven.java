
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleAllTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class FleetingAven extends CardImpl {

    public FleetingAven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a player cycles a card, return Fleeting Aven to its owner's hand.
        this.addAbility(new CycleAllTriggeredAbility(new ReturnToHandSourceEffect(true), false));
    }

    private FleetingAven(final FleetingAven card) {
        super(card);
    }

    @Override
    public FleetingAven copy() {
        return new FleetingAven(this);
    }
}
