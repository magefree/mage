package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.constants.AttachmentType;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class ProstheticInjector extends CardImpl {

    public ProstheticInjector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +0/+2 and has toxic 1.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(0, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                new ToxicAbility(1), AttachmentType.EQUIPMENT
        ).setText("and has toxic 1"));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));
    }

    private ProstheticInjector(final ProstheticInjector card) {
        super(card);
    }

    @Override
    public ProstheticInjector copy() {
        return new ProstheticInjector(this);
    }
}
