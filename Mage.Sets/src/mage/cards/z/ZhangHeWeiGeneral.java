
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox
 */
public final class ZhangHeWeiGeneral extends CardImpl {

    public ZhangHeWeiGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
        // Whenever Zhang He, Wei General attacks, each other creature you control gets +1/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true), false));
    }

    private ZhangHeWeiGeneral(final ZhangHeWeiGeneral card) {
        super(card);
    }

    @Override
    public ZhangHeWeiGeneral copy() {
        return new ZhangHeWeiGeneral(this);
    }
}
