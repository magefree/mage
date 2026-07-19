package mage.cards.v;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
public final class VibraniumStrikeGauntlets extends CardImpl {

    public VibraniumStrikeGauntlets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this Equipment enters, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +3/+0 and has trample and "Whenever this creature deals combat damage to a player, draw a card."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 0));
        Ability subAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(
            new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText("and has trample")
        );
        ability.addEffect(new GainAbilityAttachedEffect(
            subAbility, AttachmentType.EQUIPMENT
        ).setText("and \"" + subAbility.getRule("this creature") + "\"").concatBy(""));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private VibraniumStrikeGauntlets(final VibraniumStrikeGauntlets card) {
        super(card);
    }

    @Override
    public VibraniumStrikeGauntlets copy() {
        return new VibraniumStrikeGauntlets(this);
    }
}
