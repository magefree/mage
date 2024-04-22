package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClearTheMind extends CardImpl {

    public ClearTheMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Target player shuffles their graveyard into their library.
        this.getSpellAbility().addEffect(new ClearTheMindEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ClearTheMind(final ClearTheMind card) {
        super(card);
    }

    @Override
    public ClearTheMind copy() {
        return new ClearTheMind(this);
    }
}

class ClearTheMindEffect extends OneShotEffect {

    ClearTheMindEffect() {
        super(Outcome.Benefit);
        staticText = "Target player shuffles their graveyard into their library";
    }

    private ClearTheMindEffect(final ClearTheMindEffect effect) {
        super(effect);
    }

    @Override
    public ClearTheMindEffect copy() {
        return new ClearTheMindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        player.putCardsOnBottomOfLibrary(
                new CardsImpl(player.getGraveyard().getCards(game)),
                game, source, false
        );
        player.shuffleLibrary(source, game);
        return true;
    }
}