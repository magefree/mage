package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class EDELonesomeEyebot extends CardImpl {

    public EDELonesomeEyebot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // ED-E My Love -- Whenever you attack, if the number of attacking creatures is greater than the number of quest counters on
        // ED-E, Lonesome Eyebot, put a quest counter on it.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new AddCountersSourceEffect(CounterType.QUEST.createInstance())
                        .setText("put a quest counter on it"), 1
        ).withInterveningIf(EDELonesomeEyebotCondition.instance).withFlavorWord("ED-E My Love"));

        // {2}, Sacrifice ED-E: Draw a card, then draw an additional card for each quest counter on ED-E.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(
                        new IntPlusDynamicValue(1, new CountersSourceCount(CounterType.QUEST)))
                        .setText("draw a card, then draw an additional card for each quest counter on {this}"),
                new GenericManaCost(2));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private EDELonesomeEyebot(final EDELonesomeEyebot card) {
        super(card);
    }

    @Override
    public EDELonesomeEyebot copy() {
        return new EDELonesomeEyebot(this);
    }
}

enum EDELonesomeEyebotCondition implements Condition {
    instance;

    private static final DynamicValue attackingCreatureCount
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_ATTACKING_CREATURES);

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null
                && attackingCreatureCount.calculate(game, source, null) > permanent.getCounters(game).getCount(CounterType.QUEST);
    }

    @Override
    public String toString() {
        return "the number of attacking creatures is greater than the number of quest counters on {this}";
    }
}
