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
package mage.sets.commander2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public class IntellectualOffering extends CardImpl {

    public IntellectualOffering(UUID ownerId) {
        super(ownerId, 15, "Intellectual Offering", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{4}{U}");
        this.expansionSetCode = "C14";


        // Choose an opponent. You and that player each draw three cards.
        this.getSpellAbility().addEffect(new IntellectualOfferingDrawEffect());
        
        // Choose an opponent. Untap all nonland permanents you control and all nonland permanents that player controls.
        this.getSpellAbility().addEffect(new IntellectualOfferingUntapEffect());
    }

    public IntellectualOffering(final IntellectualOffering card) {
        super(card);
    }

    @Override
    public IntellectualOffering copy() {
        return new IntellectualOffering(this);
    }
}

class IntellectualOfferingDrawEffect extends OneShotEffect {
    
    IntellectualOfferingDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Choose an opponent. You and that player each draw three cards";
    }
    
    IntellectualOfferingDrawEffect(final IntellectualOfferingDrawEffect effect) {
        super(effect);
    }
    
    @Override
    public IntellectualOfferingDrawEffect copy() {
        return new IntellectualOfferingDrawEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.DrawCard, source.getControllerId(), source.getSourceId(), game);
            player.drawCards(3, game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                opponent.drawCards(3, game);
                return true;
            }
        }
        return false;
    }
}

class IntellectualOfferingUntapEffect extends OneShotEffect {
    
    IntellectualOfferingUntapEffect() {
        super(Outcome.Untap);
        this.staticText = "<br><br>Choose an opponent. Untap all nonland permanents you control and all nonland permanents that player controls";
    }
    
    IntellectualOfferingUntapEffect(final IntellectualOfferingUntapEffect effect) {
        super(effect);
    }
    
    @Override
    public IntellectualOfferingUntapEffect copy() {
        return new IntellectualOfferingUntapEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.Untap, source.getControllerId(), source.getSourceId(), game);
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterNonlandPermanent(), player.getId(), game)) {
                permanent.untap(game);
            }
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterNonlandPermanent(), opponent.getId(), game)) {
                    permanent.untap(game);
                }
                return true;
            }
        }
        return false;
    }
}
