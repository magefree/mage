package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarforgedSword extends CardImpl {

    public StarforgedSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.EQUIPMENT);

        // Gift a tapped Fish
        this.addAbility(new GiftAbility(this, GiftType.TAPPED_FISH));

        // When Starforged Sword enters, if the gift was promised, attach Starforged Sword to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget().withInterveningIf(GiftWasPromisedCondition.TRUE));

        // Equipped creature gets +3/+3 and loses flying.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 3));
        ability.addEffect(new LoseAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and loses flying"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private StarforgedSword(final StarforgedSword card) {
        super(card);
    }

    @Override
    public StarforgedSword copy() {
        return new StarforgedSword(this);
    }
}
