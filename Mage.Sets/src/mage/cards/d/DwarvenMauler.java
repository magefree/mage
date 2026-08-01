package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.ReduceCostEquipTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class DwarvenMauler extends CardImpl {

    public DwarvenMauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Equip abilities you activate that target this creature cost {2} less to activate.
        this.addAbility(new SimpleStaticAbility(new ReduceCostEquipTargetSourceEffect(2)));
    }

    private DwarvenMauler(final DwarvenMauler card) {
        super(card);
    }

    @Override
    public DwarvenMauler copy() {
        return new DwarvenMauler(this);
    }
}
