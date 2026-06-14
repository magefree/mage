package mage.cards.e;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ExperimentalArmor extends CardImpl {

    public ExperimentalArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has flying and haste.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has flying"));
        ability.addEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and haste"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private ExperimentalArmor(final ExperimentalArmor card) {
        super(card);
    }

    @Override
    public ExperimentalArmor copy() {
        return new ExperimentalArmor(this);
    }
}
