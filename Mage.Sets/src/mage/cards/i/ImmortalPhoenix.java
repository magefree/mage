package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ImmortalPhoenix extends CardImpl {

    public ImmortalPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Immortal Phoenix dies, return it to its owner’s hand.
        this.addAbility(new DiesSourceTriggeredAbility(new ReturnToHandTargetEffect()
                .setText("return it to its owner's hand"), false, SetTargetPointer.CARD));    }

    private ImmortalPhoenix(final ImmortalPhoenix card) {
        super(card);
    }

    @Override
    public ImmortalPhoenix copy() {
        return new ImmortalPhoenix(this);
    }
}
