
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class ManipulateFate extends CardImpl {

    public ManipulateFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");


        // Search your library for three cards, exile them, then shuffle your library.
        this.getSpellAbility().addEffect(new ManipulateFateEffect());
        
        // Draw a card.
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("Draw a card.");
        this.getSpellAbility().addEffect(effect);
    }

    public ManipulateFate(final ManipulateFate card) {
        super(card);
    }

    @Override
    public ManipulateFate copy() {
        return new ManipulateFate(this);
    }
}

class ManipulateFateEffect extends SearchEffect {

    ManipulateFateEffect() {
        super(new TargetCardInLibrary(3, new FilterCard()), Outcome.Benefit);
        staticText = "Search your library for three cards, exile them, then shuffle your library.";
    }

    ManipulateFateEffect(final ManipulateFateEffect effect) {
        super(effect);
    }

    @Override
    public ManipulateFateEffect copy() {
        return new ManipulateFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null) {
            if (player.searchLibrary(target, game)) {
                for (UUID targetId : getTargets()) {
                    Card card = player.getLibrary().getCard(targetId, game);
                    if (card != null) {
                        card.moveToExile(null, null, targetId, game);
                    }
                }
                return true;
            }
            player.shuffleLibrary(source, game);
        }
        return false;
    }

    public List<UUID> getTargets() {
        return target.getTargets();
    }

}