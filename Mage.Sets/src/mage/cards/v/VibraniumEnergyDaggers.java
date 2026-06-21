package mage.cards.v;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class VibraniumEnergyDaggers extends CardImpl {

    public VibraniumEnergyDaggers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 2)));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private VibraniumEnergyDaggers(final VibraniumEnergyDaggers card) {
        super(card);
    }

    @Override
    public VibraniumEnergyDaggers copy() {
        return new VibraniumEnergyDaggers(this);
    }
}
