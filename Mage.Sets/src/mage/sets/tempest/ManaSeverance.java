/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author nick.myers
 */
public class ManaSeverance extends CardImpl {
    
    public ManaSeverance(UUID ownerId) {
        super(ownerId, 73, "Mana Severance", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{U}");
        this.expansionSetCode = "TMP";
        
        // Search your library for any number of land cards and remove the from the game.
        // Shuffle your library afterwards.
        this.getSpellAbility().addEffect(new ManaSeveranceEffect());
    }
    
    public ManaSeverance(final ManaSeverance card) {
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
        this.staticText = "Search your library for any number of land cards and remove them from the game. Shuffle your library afterwards.";
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
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            if (you.searchLibrary(target, game)) {
                UUID exileZone = CardUtil.getCardExileZoneId(game, source);
                if (target.getTargets().size() > 0) {
                    for (UUID cardId : target.getTargets()) {
                        Card card = you.getLibrary().getCard(cardId, game);
                        if (card != null) {
                            card.moveToExile(exileZone, "Mana Severance", source.getSourceId(), game);
                        }
                    }
                }
            }
            you.shuffleLibrary(game);
            return true;

        }
        return false;
    }
}
