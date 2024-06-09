package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CommanderGreatestManaValue;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TangleweaveArmor extends CardImpl {

    public TangleweaveArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}{G}");
        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +X/+X, where X is the greatest mana value among your commanders
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new BoostEquippedEffect(CommanderGreatestManaValue.instance, CommanderGreatestManaValue.instance)
        ));

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4), false));
    }

    private TangleweaveArmor(final TangleweaveArmor card) {
        super(card);
    }

    @Override
    public TangleweaveArmor copy() {
        return new TangleweaveArmor(this);
    }
}
