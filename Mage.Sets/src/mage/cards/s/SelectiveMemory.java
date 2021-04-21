
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class SelectiveMemory extends CardImpl {

    public SelectiveMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");


        // Search your library for any number of nonland cards and exile them. Then shuffle your library.
        this.getSpellAbility().addEffect(new SelectiveMemoryEffect());
    }

    private SelectiveMemory(final SelectiveMemory card) {
        super(card);
    }

    @Override
    public SelectiveMemory copy() {
        return new SelectiveMemory(this);
    }
}

class SelectiveMemoryEffect extends OneShotEffect {

    public SelectiveMemoryEffect() {
        super(Outcome.Exile);
        this.staticText = "Search your library for any number of nonland cards, exile them, then shuffle";
    }

    public SelectiveMemoryEffect(final SelectiveMemoryEffect effect) {
        super(effect);
    }

    @Override
    public SelectiveMemoryEffect copy() {
        return new SelectiveMemoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, new FilterNonlandCard());
            if (player.searchLibrary(target, source, game)) {
                for (UUID targetId : target.getTargets()) {
                    Card card = player.getLibrary().remove(targetId, game);
                    if (card != null) {
                        card.moveToExile(null, "", source, game);
                    }
                }
            }

            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
