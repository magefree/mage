package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.AttachmentType;
import mage.constants.SubType;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MedicsKitesail extends CardImpl {

    public MedicsKitesail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has flying and "Whenever this creature attacks, you gain 1 life."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
            FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has flying"));
        ability.addEffect(new GainAbilityAttachedEffect(
            new AttacksTriggeredAbility(new GainLifeEffect(1)),
            AttachmentType.EQUIPMENT
        ).setText("and \"Whenever this creature attacks, you gain 1 life.\""));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private MedicsKitesail(final MedicsKitesail card) {
        super(card);
    }

    @Override
    public MedicsKitesail copy() {
        return new MedicsKitesail(this);
    }
}
