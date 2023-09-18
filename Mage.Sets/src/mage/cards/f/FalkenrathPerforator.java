package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FalkenrathPerforator extends CardImpl {

    public FalkenrathPerforator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Falkenrath Perforator attacks, it deals 1 damage to defending player.
        this.addAbility(new AttacksTriggeredAbility(new DamageTargetEffect(
                1, true, "defending player", "it"
        ), false, null, SetTargetPointer.PLAYER));
    }

    private FalkenrathPerforator(final FalkenrathPerforator card) {
        super(card);
    }

    @Override
    public FalkenrathPerforator copy() {
        return new FalkenrathPerforator(this);
    }
}
