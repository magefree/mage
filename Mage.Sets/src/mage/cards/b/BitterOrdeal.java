package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.GravestormAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class BitterOrdeal extends CardImpl {

    public BitterOrdeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Search target player's library for a card and exile it. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new BitterOrdealEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Gravestorm
        this.addAbility(new GravestormAbility());
    }

    private BitterOrdeal(final BitterOrdeal card) {
        super(card);
    }

    @Override
    public BitterOrdeal copy() {
        return new BitterOrdeal(this);
    }
}

class BitterOrdealEffect extends OneShotEffect {

    BitterOrdealEffect() {
        super(Outcome.Exile);
        staticText = "Search target player's library for a card and exile it. Then that player shuffles.";
    }

    BitterOrdealEffect(final BitterOrdealEffect effect) {
        super(effect);
    }

    @Override
    public BitterOrdealEffect copy() {
        return new BitterOrdealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller == null || targetPlayer == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary();
        controller.searchLibrary(target, source, game, targetPlayer.getId());
        Card card = targetPlayer.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            controller.moveCards(card, Zone.EXILED, source, game);
        }
        targetPlayer.shuffleLibrary(source, game);
        return true;
    }
}
