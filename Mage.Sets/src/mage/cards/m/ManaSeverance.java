package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author nick.myers
 */
public final class ManaSeverance extends CardImpl {
    
    public ManaSeverance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");
        
        // Search your library for any number of land cards and remove them from the game.
        // Shuffle your library afterwards.
        this.getSpellAbility().addEffect(new ManaSeveranceEffect());
    }
    
    private ManaSeverance(final ManaSeverance card) {
        super(card);
    }
    
    @Override
    public ManaSeverance copy() {
        return new ManaSeverance(this);
    }
    
}

class ManaSeveranceEffect extends SearchEffect {
    
    public ManaSeveranceEffect() {
        super(new TargetCardInLibrary(0, Integer.MAX_VALUE, new FilterLandCard()), Outcome.Exile);
        this.staticText = "search your library for any number of land cards, exile them, then shuffle";
    }
    
    public ManaSeveranceEffect(final ManaSeveranceEffect effect) {
        super(effect);
    }
    
    @Override
    public ManaSeveranceEffect copy() {
        return new ManaSeveranceEffect(this);
    }
    
     @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    for (UUID cardId : target.getTargets()) {
                        Card card = controller.getLibrary().getCard(cardId, game);
                        if (card != null) {
                            controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.LIBRARY, true);
                        }
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;

        }
        return false;
    }
}
