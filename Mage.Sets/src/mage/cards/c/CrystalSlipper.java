package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 *
 * @author jmharmon
 */

public final class CrystalSlipper extends CardImpl {

    public CrystalSlipper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.EQUIPMENT)));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));
    }

    public CrystalSlipper(final CrystalSlipper card) {
        super(card);
    }

    @Override
    public CrystalSlipper copy() {
        return new CrystalSlipper(this);
    }
}
