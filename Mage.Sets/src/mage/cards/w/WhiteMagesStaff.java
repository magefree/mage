package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.constants.AttachmentType;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author balazskristof
 */
public final class WhiteMagesStaff extends CardImpl {

    public WhiteMagesStaff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +1/+1, has "Whenever this creature attacks, you gain 1 life," and is a Cleric in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new AttacksTriggeredAbility(new GainLifeEffect(1)),
                AttachmentType.EQUIPMENT
        ).setText(", has \"Whenever this creature attacks, you gain 1 life,\""));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.CLERIC,
                AttachmentType.EQUIPMENT
        ).setText("is a Cleric in addition to its other types").concatBy("and"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private WhiteMagesStaff(final WhiteMagesStaff card) {
        super(card);
    }

    @Override
    public WhiteMagesStaff copy() {
        return new WhiteMagesStaff(this);
    }
}
