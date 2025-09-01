package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PaladinsArms extends CardImpl {

    public PaladinsArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +2/+1, has ward {1}, and is a Knight in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new WardAbility(new GenericManaCost(1)), AttachmentType.EQUIPMENT
        ).setText(", has ward {1}"));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.KNIGHT, AttachmentType.EQUIPMENT
        ).setText(", and is a Knight in addition to its other types"));
        this.addAbility(ability);

        // Lightbringer and Hero's Shield -- Equip {4}
        this.addAbility(new EquipAbility(4).withFlavorWord("Lightbringer and Hero's Shield"));
    }

    private PaladinsArms(final PaladinsArms card) {
        super(card);
    }

    @Override
    public PaladinsArms copy() {
        return new PaladinsArms(this);
    }
}
