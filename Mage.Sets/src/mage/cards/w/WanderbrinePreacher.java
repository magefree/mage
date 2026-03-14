package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WanderbrinePreacher extends CardImpl {

    public WanderbrinePreacher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever this creature becomes tapped, you gain 2 life.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new GainLifeEffect(2)));
    }

    private WanderbrinePreacher(final WanderbrinePreacher card) {
        super(card);
    }

    @Override
    public WanderbrinePreacher copy() {
        return new WanderbrinePreacher(this);
    }
}
