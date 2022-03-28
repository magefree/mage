
package mage.cards.m;

import java.util.UUID;
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
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.CastFromHandWatcher;

/**
 * @author LevelX
 */
public final class MyojinOfLifesWeb extends CardImpl {

    public MyojinOfLifesWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        this.getSpellAbility().addWatcher(new CastFromHandWatcher());

        // Myojin of Life's Web enters the battlefield with a divinity counter on it if you cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.DIVINITY.createInstance()), CastFromHandSourcePermanentCondition.instance, ""), "{this} enters the battlefield with a divinity counter on it if you cast it from your hand"));
        // Myojin of Life's Web is indestructible as long as it has a divinity counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                        new SourceHasCounterCondition(CounterType.DIVINITY), "{this} is indestructible as long as it has a divinity counter on it")));
        // Remove a divinity counter from Myojin of Life's Web: Put any number of creature cards from your hand onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MyojinOfLifesWebPutCreatureOnBattlefieldEffect(), new RemoveCountersSourceCost(CounterType.DIVINITY.createInstance()));

        this.addAbility(ability);
    }

    private MyojinOfLifesWeb(final MyojinOfLifesWeb card) {
        super(card);
    }

    @Override
    public MyojinOfLifesWeb copy() {
        return new MyojinOfLifesWeb(this);
    }
}

class MyojinOfLifesWebPutCreatureOnBattlefieldEffect extends OneShotEffect {

    public MyojinOfLifesWebPutCreatureOnBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put any number of creature cards from your hand onto the battlefield";
    }

    public MyojinOfLifesWebPutCreatureOnBattlefieldEffect(final MyojinOfLifesWebPutCreatureOnBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public MyojinOfLifesWebPutCreatureOnBattlefieldEffect copy() {
        return new MyojinOfLifesWebPutCreatureOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, new FilterCreatureCard("creature cards from your hand to put onto the battlefield"));
        if (controller.choose(Outcome.PutCreatureInPlay, target, source, game)) {
            return controller.moveCards(new CardsImpl(target.getTargets()).getCards(game),
                    Zone.BATTLEFIELD, source, game, false, false, false, null);
        }
        return false;
    }
}
