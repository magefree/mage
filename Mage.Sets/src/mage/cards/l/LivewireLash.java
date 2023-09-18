package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author North
 */
public final class LivewireLash extends CardImpl {

    public LivewireLash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and has "Whenever this creature becomes the target of a spell, this creature deals 2 damage to any target."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        Ability ability2 = new SourceBecomesTargetTriggeredAbility(
                new DamageTargetEffect(2, "it"), StaticFilters.FILTER_SPELL_A
        ).setTriggerPhrase("Whenever this creature becomes the target of a spell, ");
        ability2.addTarget(new TargetAnyTarget());
        ability.addEffect(new GainAbilityAttachedEffect(ability2, AttachmentType.EQUIPMENT)
                .setText("and has \"Whenever this creature becomes the target of a spell, this creature deals 2 damage to any target.\""));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private LivewireLash(final LivewireLash card) {
        super(card);
    }

    @Override
    public LivewireLash copy() {
        return new LivewireLash(this);
    }
}
