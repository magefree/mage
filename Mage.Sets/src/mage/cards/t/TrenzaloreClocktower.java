package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Cguy7777
 */
public final class TrenzaloreClocktower extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.TIME_LORD, "you control a Time Lord");

    public TrenzaloreClocktower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add {U}. Put a time counter on Trenzalore Clocktower.
        Ability manaAbility = new BlueManaAbility();
        manaAbility.addEffect(new AddCountersSourceEffect(CounterType.TIME.createInstance()));
        this.addAbility(manaAbility);

        // {1}{U}, {T}, Remove twelve time counters from Trenzalore Clocktower and exile it:
        // Shuffle your graveyard and hand into your library, then draw seven cards.
        // Activate only if you control a Time Lord.
        Ability wheelAbility = new ConditionalActivatedAbility(
                new TrenzaloreClocktowerEffect(),
                new ManaCostsImpl<>("{1}{U}"),
                new PermanentsOnTheBattlefieldCondition(filter));
        wheelAbility.addCost(new TapSourceCost());
        wheelAbility.addCost(new RemoveCountersSourceCost(CounterType.TIME.createInstance(12)));
        wheelAbility.addCost(new ExileSourceCost().setText("and exile it"));
        wheelAbility.addEffect(new DrawCardSourceControllerEffect(7).concatBy(", then"));
        this.addAbility(wheelAbility);
    }

    private TrenzaloreClocktower(final TrenzaloreClocktower card) {
        super(card);
    }

    @Override
    public TrenzaloreClocktower copy() {
        return new TrenzaloreClocktower(this);
    }
}

class TrenzaloreClocktowerEffect extends OneShotEffect {

    TrenzaloreClocktowerEffect() {
        super(Outcome.Benefit);
        staticText = "shuffle your graveyard and hand into your library";
    }

    private TrenzaloreClocktowerEffect(final TrenzaloreClocktowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(player.getGraveyard());
        cards.addAll(player.getHand());
        player.putCardsOnTopOfLibrary(cards, game, source, false);
        player.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public TrenzaloreClocktowerEffect copy() {
        return new TrenzaloreClocktowerEffect(this);
    }
}
