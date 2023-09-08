
package mage.cards.m;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class MoralityShift extends CardImpl {

    public MoralityShift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");
        

        // Exchange your graveyard and library. Then shuffle your library.
        this.getSpellAbility().addEffect(new MoralityShiftEffect());
        
    }

    private MoralityShift(final MoralityShift card) {
        super(card);
    }

    @Override
    public MoralityShift copy() {
        return new MoralityShift(this);
    }
}

class MoralityShiftEffect extends OneShotEffect {

   MoralityShiftEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Exchange your graveyard and library. Then shuffle your library.";
    }

    private MoralityShiftEffect(final MoralityShiftEffect effect) {
        super(effect);
    }

    @Override
    public MoralityShiftEffect copy() {
        return new MoralityShiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> copyLibrary = new HashSet<>();
            List<Card> listCopyLibrary = controller.getLibrary().getCards(game);
            listCopyLibrary.forEach((card) -> {
                copyLibrary.add(card);
            });
            Set<Card> copyGraveyard = controller.getGraveyard().getCards(game);
            controller.getLibrary().clear();
            controller.getGraveyard().clear();
            controller.moveCards(copyLibrary, Zone.GRAVEYARD, source, game);
            controller.moveCards(copyGraveyard, Zone.LIBRARY, source, game);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
