
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class WaterWurm extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ISLAND, "Island");

    public WaterWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Water Wurm gets +0/+1 as long as an opponent controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(0, 1, Duration.WhileOnBattlefield),
            new OpponentControlsPermanentCondition(filter),
            "{this} gets +0/+1 as long as an opponent controls an Island")));
    }

    private WaterWurm(final WaterWurm card) {
        super(card);
    }

    @Override
    public WaterWurm copy() {
        return new WaterWurm(this);
    }
}
