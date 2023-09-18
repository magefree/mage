
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.RallyTheHordeWarriorToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class RallyTheHorde extends CardImpl {

    public RallyTheHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}");

        // Exile the top card of your library. Exile the top card of your library. Exile the top card of your library. If the last card exiled isn't a land, repeat this process. Create a 1/1 red Warrior creature token for each nonland card exiled this way.
        this.getSpellAbility().addEffect(new RallyTheHordeEffect());
    }

    private RallyTheHorde(final RallyTheHorde card) {
        super(card);
    }

    @Override
    public RallyTheHorde copy() {
        return new RallyTheHorde(this);
    }
}

class RallyTheHordeEffect extends OneShotEffect {

    public RallyTheHordeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile the top card of your library. Exile the top card of your library. Exile the top card of your library. If the last card exiled isn't a land card, repeat this process. Create a 1/1 red Warrior creature token for each nonland card exiled this way.";
    }

    private RallyTheHordeEffect(final RallyTheHordeEffect effect) {
        super(effect);
    }

    @Override
    public RallyTheHordeEffect copy() {
        return new RallyTheHordeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int nonLandCardsExiled = 0;
            while (controller.getLibrary().hasCards()) {
                nonLandCardsExiled += checkIfNextLibCardIsNonLandAndExile(controller, source, game);
                if (controller.getLibrary().hasCards()) {
                    nonLandCardsExiled += checkIfNextLibCardIsNonLandAndExile(controller, source, game);
                }
                if (controller.getLibrary().hasCards()) {
                    int nonLands = checkIfNextLibCardIsNonLandAndExile(controller, source, game);
                    if (nonLands == 0) {
                        break;
                    }
                    nonLandCardsExiled += nonLands;
                }
            }
            return new CreateTokenEffect(new RallyTheHordeWarriorToken(), nonLandCardsExiled).apply(game, source);

        }
        return false;
    }

    private int checkIfNextLibCardIsNonLandAndExile(Player controller, Ability source, Game game) {
        Card card = controller.getLibrary().getFromTop(game);
        if (card != null) {
            controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.LIBRARY, true);
            return card.isLand(game) ? 0 : 1;
        }
        return 0;
    }
}
