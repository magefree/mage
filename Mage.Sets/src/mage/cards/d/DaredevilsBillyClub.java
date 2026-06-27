package mage.cards.d;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetAndYouEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DaredevilsBillyClub extends CardImpl {

    public DaredevilsBillyClub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, it deals 2 damage to any target and 1 damage to you.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetAndYouEffect(2, 1));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Equipped creature gets +1/+0 and has menace.
        Ability ability2 = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability2.addEffect(new GainAbilityAttachedEffect(
            new MenaceAbility(), AttachmentType.EQUIPMENT
        ).setText("and has menace"));
        this.addAbility(ability2);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private DaredevilsBillyClub(final DaredevilsBillyClub card) {
        super(card);
    }

    @Override
    public DaredevilsBillyClub copy() {
        return new DaredevilsBillyClub(this);
    }
}
