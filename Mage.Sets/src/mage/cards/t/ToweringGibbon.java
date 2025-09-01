package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToweringGibbon extends CardImpl {

    public ToweringGibbon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.APE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Towering Gibbon's power is equal to the greatest mana value among creatures you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerSourceEffect(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_CREATURES)
                        .setText("{this}'s power is equal to the greatest mana value among creatures you control")
        ));
    }

    private ToweringGibbon(final ToweringGibbon card) {
        super(card);
    }

    @Override
    public ToweringGibbon copy() {
        return new ToweringGibbon(this);
    }
}