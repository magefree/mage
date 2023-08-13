package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LiciaSanguineTribune extends CardImpl {

    public LiciaSanguineTribune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Licia, Sanguine Tribune costs 1 less to cast for each 1 life you gained this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new LiciaSanguineTribuneCostReductionEffect()
        ).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Pay 5 life: Put three +1/+1 counters on Licia. Activate this ability only on your turn and only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                new PayLifeCost(5), 1, MyTurnCondition.instance
        ).addHint(MyTurnHint.instance));
    }

    private LiciaSanguineTribune(final LiciaSanguineTribune card) {
        super(card);
    }

    @Override
    public LiciaSanguineTribune copy() {
        return new LiciaSanguineTribune(this);
    }
}

class LiciaSanguineTribuneCostReductionEffect extends CostModificationEffectImpl {

    LiciaSanguineTribuneCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this spell costs {1} less to cast for each 1 life you gained this turn.";
    }

    LiciaSanguineTribuneCostReductionEffect(LiciaSanguineTribuneCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            CardUtil.reduceCost(abilityToModify, ControllerGainedLifeCount.instance.calculate(game, source, this));
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public LiciaSanguineTribuneCostReductionEffect copy() {
        return new LiciaSanguineTribuneCostReductionEffect(this);
    }
}
