package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ColossalDreadmask extends CardImpl {

    public ColossalDreadmask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{G}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +6/+6 and has trample.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(6, 6));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has trample"));
        this.addAbility(ability);

        // Equip {3}{G}{G}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{3}{G}{G}"), false));
    }

    private ColossalDreadmask(final ColossalDreadmask card) {
        super(card);
    }

    @Override
    public ColossalDreadmask copy() {
        return new ColossalDreadmask(this);
    }
}
