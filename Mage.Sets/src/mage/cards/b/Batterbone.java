package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Batterbone extends CardImpl {

    public Batterbone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +1/+1 and has vigilance and lifelink.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has vigilance"));
        ability.addEffect(new GainAbilityAttachedEffect(
                LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and lifelink"));
        this.addAbility(ability);

        // Equip {5}
        this.addAbility(new EquipAbility(5, false));
    }

    private Batterbone(final Batterbone card) {
        super(card);
    }

    @Override
    public Batterbone copy() {
        return new Batterbone(this);
    }
}
