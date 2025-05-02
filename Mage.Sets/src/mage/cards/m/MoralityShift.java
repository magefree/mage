package mage.cards.m;

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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
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
        if (controller == null) {
            return false;
        }
        Set<Card> copyLibrary = new LinkedHashSet<>(controller.getLibrary().getCards(game));
        Set<Card> copyGraveyard = new LinkedHashSet<>(controller.getGraveyard().getCards(game));
        controller.moveCards(copyGraveyard, Zone.LIBRARY, source, game);
        controller.moveCards(copyLibrary, Zone.GRAVEYARD, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }
}
