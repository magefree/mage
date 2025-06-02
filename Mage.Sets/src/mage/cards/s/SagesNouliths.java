package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SagesNouliths extends CardImpl {

    public SagesNouliths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +1/+0, has "Whenever this creature attacks, untap target attacking creature," and is a Cleric in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        TriggeredAbility triggeredAbility = new AttacksTriggeredAbility(new UntapTargetEffect());
        triggeredAbility.addTarget(new TargetAttackingCreature());
        ability.addEffect(new GainAbilityAttachedEffect(
                triggeredAbility, AttachmentType.EQUIPMENT
        ).setText(", has \"Whenever this creature attacks, untap target attacking creature,\""));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.CLERIC, AttachmentType.EQUIPMENT
        ).setText("and is a Cleric in addition to its other types"));
        this.addAbility(ability);

        // Hagneia -- Equip {3}
        this.addAbility(new EquipAbility(3).withFlavorWord("Hagneia"));
    }

    private SagesNouliths(final SagesNouliths card) {
        super(card);
    }

    @Override
    public SagesNouliths copy() {
        return new SagesNouliths(this);
    }
}
