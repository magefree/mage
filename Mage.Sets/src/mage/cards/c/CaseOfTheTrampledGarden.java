package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.hint.common.CaseSolvedHint;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetPermanentAmount;

/**
 *
 * @author DominionSpy
 */
public final class CaseOfTheTrampledGarden extends CardImpl {

    public CaseOfTheTrampledGarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.CASE);

        // When this Case enters the battlefield, distribute two +1/+1 counters among one or two target creatures you control.
        Ability initialAbility = new EntersBattlefieldTriggeredAbility(new DistributeCountersEffect(CounterType.P1P1, 2,
                "one or two target creatures you control"));
        TargetPermanentAmount target = new TargetPermanentAmount(2, StaticFilters.FILTER_CONTROLLED_CREATURES);
        target.setMinNumberOfTargets(1);
        initialAbility.addTarget(target);
        // To solve -- Creatures you control have total power 8 or greater.
        // Solved -- Whenever you attack, put a +1/+1 counter on target attacking creature. It gains trample until end of turn.
        Ability solvedAbility = new ConditionalTriggeredAbility(
                new AttacksWithCreaturesTriggeredAbility(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 1),
                SolvedSourceCondition.SOLVED, "");
        solvedAbility.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("it gains trample until end of turn"));
        solvedAbility.addTarget(new TargetAttackingCreature());

        this.addAbility(new CaseAbility(initialAbility, FormidableCondition.instance, solvedAbility)
                .addHint(new CaseOfTheTrampledGardenHint(FormidableCondition.instance)));
    }

    private CaseOfTheTrampledGarden(final CaseOfTheTrampledGarden card) {
        super(card);
    }

    @Override
    public CaseOfTheTrampledGarden copy() {
        return new CaseOfTheTrampledGarden(this);
    }
}

class CaseOfTheTrampledGardenHint extends CaseSolvedHint {

    CaseOfTheTrampledGardenHint(Condition condition) {
        super(condition);
    }

    private CaseOfTheTrampledGardenHint(final CaseOfTheTrampledGardenHint hint) {
        super(hint);
    }

    @Override
    public CaseOfTheTrampledGardenHint copy() {
        return new CaseOfTheTrampledGardenHint(this);
    }

    @Override
    public String getConditionText(Game game, Ability ability) {
        int power = game.getBattlefield()
                .getAllActivePermanents(new FilterCreaturePermanent(), ability.getControllerId(), game)
                .stream()
                .map(Permanent::getPower)
                .map(MageInt::getValue)
                .reduce(0, Integer::sum);
        return "Total power: " + power + " (need 8).";
    }
}
