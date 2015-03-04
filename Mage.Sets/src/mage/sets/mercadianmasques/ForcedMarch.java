/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.Rarity;

/**
 *
 * @author nick.myers
 */
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;


public class ForcedMarch extends CardImpl {
    
    public ForcedMarch(UUID ownerId) {
        super(ownerId, 136, "Forced March", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{B}{B}{B}");
        this.expansionSetCode = "MMQ";
        
        // Destroy all creatures with converted mana cost X or less
        this.getSpellAbility().addEffect(new ForcedMarchEffect());
    }
    
    public ForcedMarch(final ForcedMarch card) {
        super(card);
    }
    
    @Override
    public ForcedMarch copy() {
        return new ForcedMarch(this);
    }    
}

class ForcedMarchEffect extends OneShotEffect {
    public ForcedMarchEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy all creatures with converted mana cost X or less";
    }
    
    public ForcedMarchEffect(final ForcedMarchEffect effect) {
        super(effect);
    }
    
    @Override
    public ForcedMarchEffect copy() {
        return new ForcedMarchEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        
       // for(Permanent permanent : game.getBattlefield().getAllActivePermanents(CardType.CREATURE)) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), 
                                                                                source.getControllerId(), 
                                                                                source.getSourceId(), 
                                                                                game)) {
            if (permanent.getManaCost().convertedManaCost() <= source.getManaCostsToPay().getX()) {
                permanent.destroy(source.getSourceId(), game, false);
            }
        }
        return true;
    }
}
