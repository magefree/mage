package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScholarOfNewHorizons extends CardImpl {

    public ScholarOfNewHorizons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Scholar of New Horizons enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                "with a +1/+1 counter on it"
        ));

        // {T}, Remove a counter from a permanent you control: Search your library for a Plains card and reveal it. If an opponent controls more lands than you, you may put that card onto the battlefield tapped. If you don't put the card onto the battlefield, put it into your hand. Then shuffle.
        Ability ability = new SimpleActivatedAbility(new ScholarOfNewHorizonsEffect(), new TapSourceCost());
        ability.addCost(new RemoveCounterCost(new TargetControlledPermanent()));
        this.addAbility(ability);
    }

    private ScholarOfNewHorizons(final ScholarOfNewHorizons card) {
        super(card);
    }

    @Override
    public ScholarOfNewHorizons copy() {
        return new ScholarOfNewHorizons(this);
    }
}

class ScholarOfNewHorizonsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterLandCard("Plains card");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_LAND);

    ScholarOfNewHorizonsEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a Plains card and reveal it. If an opponent " +
                "controls more lands than you, you may put that card onto the battlefield tapped. " +
                "If you don't put the card onto the battlefield, put it into your hand. Then shuffle";
    }

    private ScholarOfNewHorizonsEffect(final ScholarOfNewHorizonsEffect effect) {
        super(effect);
    }

    @Override
    public ScholarOfNewHorizonsEffect copy() {
        return new ScholarOfNewHorizonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        player.revealCards(source, new CardsImpl(card), game);
        if (card == null
                || !condition.apply(game, source)
                || !player.chooseUse(outcome, "Put the card onto the battlfield tapped?", source, game)
                || !player.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, false, null
        )) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
