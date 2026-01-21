package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeadersTalent extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public LeadersTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // Whenever you attack, put a +1/+1 counter on target attacking creature.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 1
        );
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);

        // {2}{W}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{2}{W}"));

        // Whenever a creature you control leaves the battlefield, if it had a counter on it, you gain 2 life.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new LeavesBattlefieldAllTriggeredAbility(new GainLifeEffect(2), filter)
                        .setTriggerPhrase("Whenever a creature you control leaves the battlefield, if it had a counter on it, "), 2
        )));

        // {3}{W}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{W}"));

        // Whenever you cast a spell, put a +1/+1 counter on each creature you control.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new SpellCastControllerTriggeredAbility(new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
                ), false), 3
        )));
    }

    private LeadersTalent(final LeadersTalent card) {
        super(card);
    }

    @Override
    public LeadersTalent copy() {
        return new LeadersTalent(this);
    }
}
