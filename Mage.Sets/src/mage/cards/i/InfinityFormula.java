package mage.cards.i;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeTargetControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class InfinityFormula extends CardImpl {

    public InfinityFormula(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +1/+2 and has "Whenever this creature attacks, you gain 2 life."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 2));
        AttacksTriggeredAbility attacksAbility = new AttacksTriggeredAbility(new GainLifeTargetControllerEffect(2));
        ability.addEffect(new GainAbilityAttachedEffect(attacksAbility, AttachmentType.EQUIPMENT)
            .setText("and has \"Whenever this creature attacks, you gain 2 life.\""));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));

    }

    private InfinityFormula(final InfinityFormula card) {
        super(card);
    }

    @Override
    public InfinityFormula copy() {
        return new InfinityFormula(this);
    }
}
