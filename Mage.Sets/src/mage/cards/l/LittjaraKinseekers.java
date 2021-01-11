package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.*;

/**
 * @author TheElk801
 */
public final class LittjaraKinseekers extends CardImpl {

    public LittjaraKinseekers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Changeling
        this.setIsAllCreatureTypes(true);
        this.addAbility(ChangelingAbility.getInstance());

        // When Littjara Kinseekers enters the battlefield, if you control three or more creatures that share a creature type, put a +1/+1 counter on Littjara Kinseekers, then scry 1.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                ), LittjaraKinseekersCondition.instance, "When {this} enters the battlefield, " +
                "if you control three or more creatures that share a creature type, " +
                "put a +1/+1 counter on {this}, then scry 1."
        );
        ability.addEffect(new ScryEffect(1));
        this.addAbility(ability);
    }

    private LittjaraKinseekers(final LittjaraKinseekers card) {
        super(card);
    }

    @Override
    public LittjaraKinseekers copy() {
        return new LittjaraKinseekers(this);
    }
}

enum LittjaraKinseekersCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanentList = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source.getSourceId(), game
        );
        permanentList.removeIf(Objects::isNull);
        long changelings = permanentList
                .stream()
                .filter(Objects::nonNull)
                .filter(MageObject::isAllCreatureTypes)
                .count();
        if (changelings > 2) {
            return true;
        }
        permanentList.removeIf(MageObject::isAllCreatureTypes);
        Map<SubType, Integer> typeMap = new HashMap<>();
        return permanentList
                .stream()
                .map(permanent -> permanent.getSubtype(game))
                .flatMap(Collection::stream)
                .mapToInt(subType -> typeMap.compute(subType, (s, i) -> i == null ? 1 : Integer.sum(i, 1)))
                .anyMatch(x -> x + changelings > 2);
    }
}