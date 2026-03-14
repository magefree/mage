package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WanderingMusicians extends CardImpl {

    public WanderingMusicians(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R/W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever this creature attacks, creatures you control get +1/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn)));
    }

    private WanderingMusicians(final WanderingMusicians card) {
        super(card);
    }

    @Override
    public WanderingMusicians copy() {
        return new WanderingMusicians(this);
    }
}
