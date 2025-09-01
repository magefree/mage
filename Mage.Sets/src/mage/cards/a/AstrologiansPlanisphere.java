package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AstrologiansPlanisphere extends CardImpl {

    public AstrologiansPlanisphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature is a Wizard in addition to its other types and has "Whenever you cast a noncreature spell and whenever you draw your third card each turn, put a +1/+1 counter on this creature."
        Ability ability = new SimpleStaticAbility(
                new AddCardSubtypeAttachedEffect(SubType.WIZARD, AttachmentType.EQUIPMENT)
                        .setText("equipped creature is a Wizard in addition to its other types")
        );
        ability.addEffect(new GainAbilityAttachedEffect(new OrTriggeredAbility(
                Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new SpellCastControllerTriggeredAbility(null, StaticFilters.FILTER_SPELL_A_NON_CREATURE, false),
                new DrawNthCardTriggeredAbility(null, false, 3)
        ), AttachmentType.EQUIPMENT).setText("and has \"Whenever you cast a noncreature spell " +
                "and whenever you draw your third card each turn, put a +1/+1 counter on this creature.\""));
        this.addAbility(ability);

        // Diana -- Equip {2}
        this.addAbility(new EquipAbility(2).withFlavorWord("Diana"));
    }

    private AstrologiansPlanisphere(final AstrologiansPlanisphere card) {
        super(card);
    }

    @Override
    public AstrologiansPlanisphere copy() {
        return new AstrologiansPlanisphere(this);
    }
}
