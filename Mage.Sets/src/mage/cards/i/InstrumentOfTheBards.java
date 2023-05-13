package mage.cards.i;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author weirddan455
 */
public final class InstrumentOfTheBards extends CardImpl {

    public InstrumentOfTheBards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{G}");

        this.supertype.add(SuperType.LEGENDARY);

        // At the beginning of your upkeep, you may put a harmony counter on Instrument of Bards.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.HARMONY.createInstance()),
                TargetController.YOU, true
        ));

        // {3}{G}, {T}: Search your library for a creature card with mana value equal to the number of harmony
        // counters on Instrument of Bards, reveal it, and put it into your hand.
        // If that card is legendary, create a Treasure token. Then shuffle.
        Ability ability = new SimpleActivatedAbility(new InstrumentOfTheBardsEffect(), new ManaCostsImpl<>("{3}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private InstrumentOfTheBards(final InstrumentOfTheBards card) {
        super(card);
    }

    @Override
    public InstrumentOfTheBards copy() {
        return new InstrumentOfTheBards(this);
    }
}

class InstrumentOfTheBardsEffect extends OneShotEffect {

    public InstrumentOfTheBardsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for a creature card with mana value equal to the number of harmony " +
                "counters on {this}, reveal it, and put it into your hand. " +
                "If that card is legendary, create a Treasure token. Then shuffle";
    }

    private InstrumentOfTheBardsEffect(final InstrumentOfTheBardsEffect effect) {
        super(effect);
    }

    @Override
    public InstrumentOfTheBardsEffect copy() {
        return new InstrumentOfTheBardsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller == null || permanent == null) {
            return false;
        }
        int counters = permanent.getCounters(game).getCount(CounterType.HARMONY);
        FilterCreatureCard filter = new FilterCreatureCard("creature card with mana value " + counters);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, counters));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.revealCards(permanent.getIdName(), new CardsImpl(card), game);
                controller.moveCards(card, Zone.HAND, source, game);
                if (card.isLegendary(game)) {
                    new TreasureToken().putOntoBattlefield(1, game, source, source.getControllerId());
                }
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
