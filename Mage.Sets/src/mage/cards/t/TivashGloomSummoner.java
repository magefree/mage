package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.DemonFlyingToken;
import mage.players.Player;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TivashGloomSummoner extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0);

    public TivashGloomSummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, if you gained life this turn, you may pay X life, where X is the amount of life you gained this turn. If you do, create an X/X black Demon creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new TivashGloomSummonerEffect(),
                TargetController.YOU, condition, false
        ).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private TivashGloomSummoner(final TivashGloomSummoner card) {
        super(card);
    }

    @Override
    public TivashGloomSummoner copy() {
        return new TivashGloomSummoner(this);
    }
}

class TivashGloomSummonerEffect extends OneShotEffect {

    TivashGloomSummonerEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay X life, where X is the amount of life you gained this turn. " +
                "If you do, create an X/X black Demon creature token with flying";
    }

    private TivashGloomSummonerEffect(final TivashGloomSummonerEffect effect) {
        super(effect);
    }

    @Override
    public TivashGloomSummonerEffect copy() {
        return new TivashGloomSummonerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        PlayerGainedLifeWatcher watcher = game.getState().getWatcher(PlayerGainedLifeWatcher.class);
        if (player == null || watcher == null) {
            return false;
        }
        int lifeGained = watcher.getLifeGained(source.getControllerId());
        Cost cost = new PayLifeCost(lifeGained);
        if (!cost.canPay(
                source, source, source.getControllerId(), game
        ) || !player.chooseUse(
                Outcome.PutCreatureInPlay, "Pay " + lifeGained + " life?", source, game
        ) || !cost.pay(
                source, game, source, source.getControllerId(), true
        )) {
            return false;
        }
        new DemonFlyingToken(lifeGained).putOntoBattlefield(1, game, source, source.getControllerId());
        return true;
    }
}
