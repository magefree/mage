

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 * @author spjspj
 */
public final class NahiriTheLithomancerEquipmentToken extends TokenImpl {

    public NahiriTheLithomancerEquipmentToken() {
        super("Stoneforged Blade", "colorless Equipment artifact token named Stoneforged Blade with indestructible, \"Equipped creature gets +5/+5 and has double strike,\" and equip {0}");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.EQUIPMENT);

        this.addAbility(IndestructibleAbility.getInstance());

        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(5, 5));
        ability.addEffect(new GainAbilityAttachedEffect(DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield, "and has double strike"));
        this.addAbility(ability);

        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(0)));
    }

    protected NahiriTheLithomancerEquipmentToken(final NahiriTheLithomancerEquipmentToken token) {
        super(token);
    }

    public NahiriTheLithomancerEquipmentToken copy() {
        return new NahiriTheLithomancerEquipmentToken(this);
    }
}
