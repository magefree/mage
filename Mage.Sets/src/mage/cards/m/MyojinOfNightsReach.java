package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class MyojinOfNightsReach extends CardImpl {

    public MyojinOfNightsReach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        this.getSpellAbility().addWatcher(new CastFromHandWatcher());

        // Myojin of Night's Reach enters the battlefield with a divinity counter on it if you cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.DIVINITY.createInstance()), CastFromHandSourcePermanentCondition.instance, ""), "with a divinity counter on it if you cast it from your hand"));
        // Myojin of Night's Reach has indestructible as long as it has a divinity counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                new SourceHasCounterCondition(CounterType.DIVINITY), "{this} has indestructible as long as it has a divinity counter on it")));
        // Remove a divinity counter from Myojin of Night's Reach: Each opponent discards their hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MyojinOfNightsReachEffect(), new RemoveCountersSourceCost(CounterType.DIVINITY.createInstance()));
        this.addAbility(ability);
    }

    private MyojinOfNightsReach(final MyojinOfNightsReach card) {
        super(card);
    }

    @Override
    public MyojinOfNightsReach copy() {
        return new MyojinOfNightsReach(this);
    }
}

class MyojinOfNightsReachEffect extends OneShotEffect {

    MyojinOfNightsReachEffect() {
        super(Outcome.Discard);
        staticText = "Each opponent discards their hand";
    }

    private MyojinOfNightsReachEffect(final MyojinOfNightsReachEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.discard(opponent.getHand(), false, source, game);
            }
        }
        return true;
    }

    @Override
    public MyojinOfNightsReachEffect copy() {
        return new MyojinOfNightsReachEffect(this);
    }
}
