package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BardsBow extends CardImpl {

    public BardsBow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +2/+2, has reach, and is a Bard in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                ReachAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText(", has reach"));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.BARD, AttachmentType.EQUIPMENT
        ).setText(", and is a Bard in addition to its other types"));
        this.addAbility(ability);

        // Perseus's Bow-- Equip {6}
        this.addAbility(new EquipAbility(6).withFlavorWord("Perseus's Bow"));
    }

    private BardsBow(final BardsBow card) {
        super(card);
    }

    @Override
    public BardsBow copy() {
        return new BardsBow(this);
    }
}
