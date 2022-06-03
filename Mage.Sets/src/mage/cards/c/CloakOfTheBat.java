package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloakOfTheBat extends CardImpl {

    public CloakOfTheBat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has flying and haste.
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and haste"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private CloakOfTheBat(final CloakOfTheBat card) {
        super(card);
    }

    @Override
    public CloakOfTheBat copy() {
        return new CloakOfTheBat(this);
    }
}
