
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class GoblinWarWagon extends CardImpl {

    public GoblinWarWagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Goblin War Wagon doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));
        // At the beginning of your upkeep, you may pay {2}. If you do, untap Goblin War Wagon.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DoIfCostPaid(
            new UntapSourceEffect(), new ManaCostsImpl<>("{2}")), TargetController.YOU, false));
    }

    private GoblinWarWagon(final GoblinWarWagon card) {
        super(card);
    }

    @Override
    public GoblinWarWagon copy() {
        return new GoblinWarWagon(this);
    }
}
