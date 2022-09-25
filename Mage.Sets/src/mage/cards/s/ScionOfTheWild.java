
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 * @author anonymous
 */
public final class ScionOfTheWild extends CardImpl {

    public ScionOfTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURES), Duration.EndOfGame)));
    }

    private ScionOfTheWild(final ScionOfTheWild card) {
        super(card);
    }

    @Override
    public ScionOfTheWild copy() {
        return new ScionOfTheWild(this);
    }
}
