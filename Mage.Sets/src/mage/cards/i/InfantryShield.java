package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.MobilizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfantryShield extends CardImpl {

    public InfantryShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has menace and mobilize X, where X is its power.
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new MenaceAbility(false), AttachmentType.EQUIPMENT
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                new MobilizeAbility(SourcePermanentPowerValue.NOT_NEGATIVE), AttachmentType.EQUIPMENT
        ).setText("and mobilize X, where X is its power"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private InfantryShield(final InfantryShield card) {
        super(card);
    }

    @Override
    public InfantryShield copy() {
        return new InfantryShield(this);
    }
}
