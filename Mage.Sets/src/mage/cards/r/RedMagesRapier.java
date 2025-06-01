package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RedMagesRapier extends CardImpl {

    public RedMagesRapier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature has "Whenever you cast a noncreature spell, this creature gets +2/+0 until end of turn" and is a Wizard in addition to its other types.
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new SpellCastControllerTriggeredAbility(
                        new BoostSourceEffect(2, 0, Duration.EndOfTurn),
                        StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
                ), AttachmentType.EQUIPMENT
        ).setText("equipped creature has \"Whenever you cast a noncreature spell, " +
                "this creature gets +2/+0 until end of turn\""));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.WIZARD, AttachmentType.EQUIPMENT
        ).setText("and is a Wizard in addition to its other types"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private RedMagesRapier(final RedMagesRapier card) {
        super(card);
    }

    @Override
    public RedMagesRapier copy() {
        return new RedMagesRapier(this);
    }
}
