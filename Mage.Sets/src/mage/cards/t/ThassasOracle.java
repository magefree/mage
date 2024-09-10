package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThassasOracle extends CardImpl {

    public ThassasOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Thassa's Oracle enters the battlefield, look at the top X cards of your library, where X is your devotion to blue.
        // Put up to one of them on top of your library and the rest on the bottom of your library in a random order. 
        // If X is greater than or equal to the number of cards in your library, you win the game. 
        // (Each Blue in the mana costs of permanents you control counts toward your devotion to blue.)
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ThassasOracleEffect())
                .addHint(DevotionCount.U.getHint()));
    }

    private ThassasOracle(final ThassasOracle card) {
        super(card);
    }

    @Override
    public ThassasOracle copy() {
        return new ThassasOracle(this);
    }
}

class ThassasOracleEffect extends OneShotEffect {

    ThassasOracleEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top X cards of your library, where X is your devotion to blue. " +
                "Put up to one of them on top of your library and the rest on the bottom of your library in a random order. " +
                "If X is greater than or equal to the number of cards in your library, you win the game";
    }

    private ThassasOracleEffect(final ThassasOracleEffect effect) {
        super(effect);
    }

    @Override
    public ThassasOracleEffect copy() {
        return new ThassasOracleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.getLibrary().size() == 0) {
            player.won(game);
            return true;
        }
        int xValue = DevotionCount.U.calculate(game, source, this);
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, xValue));
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD);
        player.choose(Outcome.DrawCard, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        cards.remove(card);
        player.putCardsOnTopOfLibrary(new CardsImpl(card), game, source, false);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        if (xValue >= player.getLibrary().size()) {
            player.won(game);
        }
        return true;
    }
}