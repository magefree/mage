package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BootsOfSpeed extends CardImpl {

    public BootsOfSpeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has haste.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.AURA
        ).setText("and has haste"));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private BootsOfSpeed(final BootsOfSpeed card) {
        super(card);
    }

    @Override
    public BootsOfSpeed copy() {
        return new BootsOfSpeed(this);
    }
}
