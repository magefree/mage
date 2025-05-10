package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.AttachmentType;
import mage.constants.SubType;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

/**
 * @author balazskristof
 */
public final class BlackMagesRod extends CardImpl {

    public BlackMagesRod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +1/+0, has "Whenever you cast a noncreature spell, this creature deals 1 damage to each opponent," and is a Wizard in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                new SpellCastControllerTriggeredAbility(
                        new DamagePlayersEffect(1, TargetController.OPPONENT, "this creature"),
                        StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
                ),
                AttachmentType.EQUIPMENT
        ).setText(", has \"Whenever you cast a noncreature spell, this creature deals 1 damage to each opponent,\""));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.WIZARD,
                AttachmentType.EQUIPMENT
        ).setText("is a Wizard in addition to its other types").concatBy("and"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private BlackMagesRod(final BlackMagesRod card) {
        super(card);
    }

    @Override
    public BlackMagesRod copy() {
        return new BlackMagesRod(this);
    }
}
