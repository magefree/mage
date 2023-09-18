package mage.cards.a;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.AddCardColorAttachedEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author noxx
 */
public final class AngelicArmaments extends CardImpl {

    public AngelicArmaments(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2, has flying, and is a white Angel in addition to its other colors and types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText(", has flying"));
        ability.addEffect(new AddCardColorAttachedEffect(
                ObjectColor.WHITE, AttachmentType.EQUIPMENT
        ).setText(","));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.ANGEL, AttachmentType.EQUIPMENT
        ).setText("is a white Angel in addition to its other colors and types").concatBy("and"));
        this.addAbility(ability);

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4)));
    }

    private AngelicArmaments(final AngelicArmaments card) {
        super(card);
    }

    @Override
    public AngelicArmaments copy() {
        return new AngelicArmaments(this);
    }
}
