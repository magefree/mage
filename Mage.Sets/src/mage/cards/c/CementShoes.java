package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.AttachmentType;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class CementShoes extends CardImpl {

    public CementShoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+3 and has "At the beginning of your end step, tap this creature."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 3));
        ability.addEffect(new GainAbilityAttachedEffect(
                new BeginningOfYourEndStepTriggeredAbility(new TapSourceEffect(), false),
                AttachmentType.EQUIPMENT
        ).setText("and has \"At the beginning of your end step, tap this creature.\""));

        // Equipped creature doesn't untap during its controller's untap step.
        ability.addEffect(new DontUntapInControllersUntapStepEnchantedEffect()
                .setText("Equipped creature doesn't untap during its controller's untap step")
        );
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private CementShoes(final CementShoes card) {
        super(card);
    }

    @Override
    public CementShoes copy() {
        return new CementShoes(this);
    }
}
