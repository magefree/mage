package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromAnywhereThisTurnPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
* @author Alex-Vasile
*/
public class MyojinOfGrimBetrayal extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard();
    static { filter.add(PutIntoGraveFromAnywhereThisTurnPredicate.instance); }
    private static final DynamicValue xValue = new CardsInAllGraveyardsCount(filter);
    private static final Hint hint = new ValueHint("Permanents put into the graveyard this turn", xValue);

    public MyojinOfGrimBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Myojin of Grim Betrayal enters the battlefield with an indestructible counter on it if you cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.INDESTRUCTIBLE.createInstance()),
                CastFromHandSourcePermanentCondition.instance, null,
                "with an indestructible counter on it if you cast it from your hand"
        ), new CastFromHandWatcher());

        // Remove an indestructible counter from Myojin of Grim Betrayal:
        // Put onto the battlefield under your control all creature cards in all graveyards that were put there from anywhere this turn.
        Ability ability = new SimpleActivatedAbility(
                new MyojinOfGrimBetrayalEffect(filter),
                new RemoveCountersSourceCost(CounterType.INDESTRUCTIBLE.createInstance())
        ).addHint(hint);
        ability.addWatcher(new CardsPutIntoGraveyardWatcher());
        this.addAbility(ability);
    }
    
    private MyojinOfGrimBetrayal(final MyojinOfGrimBetrayal card) { super(card); }
    
    @Override
    public MyojinOfGrimBetrayal copy() {return new MyojinOfGrimBetrayal(this); }
}

class MyojinOfGrimBetrayalEffect extends OneShotEffect {

    private final FilterCreatureCard filter;

    MyojinOfGrimBetrayalEffect(FilterCreatureCard filter) {
        super(Outcome.PutCardInPlay);
        this.filter = filter;
        this.staticText = "Put onto the battlefield under your control all creature cards in all graveyards " +
                "that were put there from anywhere this turn";
    }

    private MyojinOfGrimBetrayalEffect(final MyojinOfGrimBetrayalEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
        if (controller == null || watcher == null) { return false; }

        Cards cards = new CardsImpl(watcher.getCardsPutIntoGraveyardFromBattlefield(game));
        cards.removeIf(uuid -> !game.getCard(uuid).isCreature(game));

        return controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }

    @Override
    public MyojinOfGrimBetrayalEffect copy() { return new MyojinOfGrimBetrayalEffect(this); }
}