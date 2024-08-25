package mage.cards.f;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FinneasAceArcher extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("other creature you control that's a token or a Rabbit");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                TokenPredicate.TRUE,
                SubType.RABBIT.getPredicate()
        ));
    }

    public FinneasAceArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Finneas, Ace Archer attacks, put a +1/+1 counter on each other creature you control that's a token or a Rabbit. Then if creatures you control have total power 10 or greater, draw a card.
        Ability ability = new AttacksTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                FinneasAceArcherCondition.instance
        ).concatBy("Then"));
        this.addAbility(ability);
    }

    private FinneasAceArcher(final FinneasAceArcher card) {
        super(card);
    }

    @Override
    public FinneasAceArcher copy() {
        return new FinneasAceArcher(this);
    }
}

enum FinneasAceArcherCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum() >= 10;
    }

    @Override
    public String toString() {
        return "creatures you control have total power 10 or greater";
    }
}
