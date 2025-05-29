package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class TuyaBearclaw extends CardImpl {

    public TuyaBearclaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Tuya Bearclaw attacks, it gets +X/+X until end of turn, where X is the greatest power among other creatures you control.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                GreatestAmongPermanentsValue.POWER_OTHER_CONTROLLED_CREATURES,
                GreatestAmongPermanentsValue.POWER_OTHER_CONTROLLED_CREATURES,
                Duration.EndOfTurn
        ), false).addHint(GreatestAmongPermanentsValue.POWER_OTHER_CONTROLLED_CREATURES.getHint()));
    }

    private TuyaBearclaw(final TuyaBearclaw card) {
        super(card);
    }

    @Override
    public TuyaBearclaw copy() {
        return new TuyaBearclaw(this);
    }
}