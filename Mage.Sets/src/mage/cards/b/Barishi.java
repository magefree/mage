package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class Barishi extends CardImpl {

    public Barishi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Barishi dies, exile Barishi, then shuffle all creature cards from your graveyard into your library.
        this.addAbility(new DiesSourceTriggeredAbility(new BarishiEffect(), false));
    }

    private Barishi(final Barishi card) {
        super(card);
    }

    @Override
    public Barishi copy() {
        return new Barishi(this);
    }
}

class BarishiEffect extends OneShotEffect {

    public BarishiEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile {this}, then shuffle all creature cards from your graveyard into your library";
    }

    private BarishiEffect(final BarishiEffect effect) {
        super(effect);
    }

    @Override
    public BarishiEffect copy() {
        return new BarishiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new ExileSourceEffect().apply(game, source);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        controller.putCardsOnTopOfLibrary(cards, game, source, false);
        controller.shuffleLibrary(source, game);
        return true;
    }
}
