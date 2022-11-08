package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.BearToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TitaniasCommand extends CardImpl {

    public TitaniasCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Exile target player's graveyard. You gain 1 life for each card exiled this way.
        this.getSpellAbility().addEffect(new TitaniasCommandEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // * Search your library for up to two land cards, put them onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addMode(new Mode(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_LANDS), true
        )));

        // * Create two 2/2 green Bear creature tokens.
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new BearToken(), 2)));

        // * Put two +1/+1 counters on each creature you control.
        this.getSpellAbility().addMode(new Mode(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(2), StaticFilters.FILTER_CONTROLLED_CREATURE
        )));
    }

    private TitaniasCommand(final TitaniasCommand card) {
        super(card);
    }

    @Override
    public TitaniasCommand copy() {
        return new TitaniasCommand(this);
    }
}

class TitaniasCommandEffect extends OneShotEffect {

    TitaniasCommandEffect() {
        super(Outcome.Benefit);
        staticText = "exile target player's graveyard. You gain 1 life for each card exiled this way";
    }

    private TitaniasCommandEffect(final TitaniasCommandEffect effect) {
        super(effect);
    }

    @Override
    public TitaniasCommandEffect copy() {
        return new TitaniasCommandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getGraveyard());
        if (cards.isEmpty()) {
            return false;
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        player.gainLife(cards.size(), game, source);
        return true;
    }
}
