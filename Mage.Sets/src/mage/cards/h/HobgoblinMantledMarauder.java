package mage.cards.h;

import mage.MageInt;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HobgoblinMantledMarauder extends CardImpl {

    public HobgoblinMantledMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you discard a card, Hobgoblin gets +2/+0 until end of turn.
        this.addAbility(new DiscardCardControllerTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), false));
    }

    private HobgoblinMantledMarauder(final HobgoblinMantledMarauder card) {
        super(card);
    }

    @Override
    public HobgoblinMantledMarauder copy() {
        return new HobgoblinMantledMarauder(this);
    }
}
