package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThiefsKnife extends CardImpl {

    public ThiefsKnife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +1/+1, has "Whenever this creature deals combat damage to a player, draw a card," and is a Rogue in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new DrawCardSourceControllerEffect(1)
                ), AttachmentType.EQUIPMENT
        ).setText(", has \"Whenever this creature deals combat damage to a player, draw a card,\""));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.ROGUE, AttachmentType.EQUIPMENT
        ).setText("and is a Rogue in addition to its other types"));
        this.addAbility(ability);

        // Equip {4}
        this.addAbility(new EquipAbility(4));
    }

    private ThiefsKnife(final ThiefsKnife card) {
        super(card);
    }

    @Override
    public ThiefsKnife copy() {
        return new ThiefsKnife(this);
    }
}
