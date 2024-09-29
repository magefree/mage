
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class DrogskolShieldmate extends CardImpl {

    public DrogskolShieldmate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Drogskol Shieldmate enters the battlefield, other creatures you control get +0/+1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new BoostControlledEffect(0, 1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES, true), false));
    }

    private DrogskolShieldmate(final DrogskolShieldmate card) {
        super(card);
    }

    @Override
    public DrogskolShieldmate copy() {
        return new DrogskolShieldmate(this);
    }
}
