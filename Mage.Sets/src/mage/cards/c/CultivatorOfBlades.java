
package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FabricateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class CultivatorOfBlades extends CardImpl {

    public CultivatorOfBlades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Fabricate 2
        this.addAbility(new FabricateAbility(2));

        // Whenever Cultivator of Blades attacks, you may have other attacking creatures get +X/+X until end of turn, where X is Cultivator of Blades's power.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(new SourcePermanentPowerCount(), new SourcePermanentPowerCount(), Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, true),
                true, "Whenever Cultivator of Blades attacks, you may have other attacking creatures get +X/+X until end of turn, where X is Cultivator of Blades's power."));
    }

    private CultivatorOfBlades(final CultivatorOfBlades card) {
        super(card);
    }

    @Override
    public CultivatorOfBlades copy() {
        return new CultivatorOfBlades(this);
    }
}
