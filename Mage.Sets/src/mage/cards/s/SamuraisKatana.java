package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SamuraisKatana extends CardImpl {

    public SamuraisKatana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +2/+2, has trample and haste, and is a Samurai in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText(", has trample"));
        ability.addEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and haste"));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.SAMURAI, AttachmentType.EQUIPMENT
        ).setText(", and is a Samurai in addition to its other types"));
        this.addAbility(ability);

        // Murasame -- Equip {5}
        this.addAbility(new EquipAbility(5).withFlavorWord("Murasame"));
    }

    private SamuraisKatana(final SamuraisKatana card) {
        super(card);
    }

    @Override
    public SamuraisKatana copy() {
        return new SamuraisKatana(this);
    }
}
