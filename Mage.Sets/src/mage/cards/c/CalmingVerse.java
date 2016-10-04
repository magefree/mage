/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class CalmingVerse extends CardImpl {
    
    public CalmingVerse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Destroy all enchantments you don't control. Then, if you control an untapped land, destroy all enchantments you control.
        this.getSpellAbility().addEffect(new CalmingVerseEffect());
        
    }

    public CalmingVerse(final CalmingVerse card) {
        super(card);
    }

    @Override
    public CalmingVerse copy() {
        return new CalmingVerse(this);
    }
}

class CalmingVerseEffect extends OneShotEffect {
    
    private static final FilterPermanent untappedLandFilter = new FilterPermanent("If you control an untapped land");
    static {
        untappedLandFilter.add(new CardTypePredicate(CardType.LAND));
        untappedLandFilter.add(Predicates.not(new TappedPredicate()));
    }
  
    private static final FilterEnchantmentPermanent opponentEnchantmentsFilter = new FilterEnchantmentPermanent("enchantments you don't control");
    static {
        opponentEnchantmentsFilter.add(new ControllerPredicate(TargetController.OPPONENT));
    }
    
    private static final FilterControlledEnchantmentPermanent controlledEnchantmentsFilter = new FilterControlledEnchantmentPermanent("enchantments you control");


    public CalmingVerseEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy all enchantments you don't control. Then, if you control an untapped land, destroy all enchantments you control";
    }

    public CalmingVerseEffect(final CalmingVerseEffect effect) {
        super(effect);
    }

    @Override
    public CalmingVerseEffect copy() {
        return new CalmingVerseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Destroy all other enchantments
        for (Permanent permanent : game.getBattlefield().getActivePermanents(opponentEnchantmentsFilter, source.getControllerId(), source.getSourceId(), game)) {
            permanent.destroy(source.getSourceId(), game, false);
        }
        
        // Then if you control an untapped land, destroy all own enchantments
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            
            if (game.getState().getBattlefield().countAll(untappedLandFilter, controller.getId(), game) > 0) {                
                for (Permanent permanent : game.getBattlefield().getActivePermanents(controlledEnchantmentsFilter, source.getControllerId(), source.getSourceId(), game)) {
                    permanent.destroy(source.getSourceId(), game, false);
                }
            }
            
        }
        return true;
    }
}
