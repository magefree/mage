package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Styxo
 */
public final class HonoredCropCaptain extends CardImpl {

    public HonoredCropCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Honored Crop-Captain attacks, other attacking creatures get +1/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(1, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, true), false));

    }

    private HonoredCropCaptain(final HonoredCropCaptain card) {
        super(card);
    }

    @Override
    public HonoredCropCaptain copy() {
        return new HonoredCropCaptain(this);
    }
}
