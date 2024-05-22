package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jimga150
 */
public final class CleopatraExiledPharaoh extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target legendary creatures");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("a legendary creature with counters on it");

    static {
        filter2.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(CounterAnyPredicate.instance);
    }

    private static final DynamicValue xValue = CountersDiedCreatureCount.instance;

    public CleopatraExiledPharaoh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Allies -- At the beginning of your end step, put a +1/+1 counter on each of up to two other target legendary creatures.
        // Based on Angelic Quartermaster
        Ability ability = new BeginningOfEndStepTriggeredAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()), TargetController.YOU, false).withFlavorWord("Allies");
        ability.addTarget(new TargetPermanent(0, 2, filter));
        this.addAbility(ability);

        // Betrayal -- Whenever a legendary creature with counters on it dies, draw a card for each counter on it. You lose 2 life.
        Ability ability2 = new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(xValue).setText("draw a card for each counter on it"),
                false, filter2).withFlavorWord("Betrayal");
        Effect effect = new LoseLifeSourceControllerEffect(2);
        effect.setText("You lose 2 life");
        ability2.addEffect(effect);
        this.addAbility(ability2);
    }

    private CleopatraExiledPharaoh(final CleopatraExiledPharaoh card) {
        super(card);
    }

    @Override
    public CleopatraExiledPharaoh copy() {
        return new CleopatraExiledPharaoh(this);
    }
}

enum CountersDiedCreatureCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility == null || effect == null) {
            return 0;
        }
        Permanent creatureDied = (Permanent) effect.getValue("creatureDied");
        if (creatureDied == null) {
            return 0;
        }
        return creatureDied
                .getCounters(game)
                .values()
                .stream()
                .mapToInt(Counter::getCount).sum();
    }

    @Override
    public DynamicValue copy() {
        return CountersDiedCreatureCount.instance;
    }

    @Override
    public String getMessage() {
        return "counters on creature that died";
    }

    @Override
    public String toString() {
        return "";
    }
}
