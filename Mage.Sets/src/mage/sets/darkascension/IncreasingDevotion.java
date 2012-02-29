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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public class IncreasingDevotion extends CardImpl<IncreasingDevotion> {

    public IncreasingDevotion(UUID ownerId) {
        super(ownerId, 11, "Increasing Devotion", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");
        this.expansionSetCode = "DKA";

        this.color.setWhite(true);

        // Put five 1/1 white Human creature tokens onto the battlefield. If Increasing Devotion was cast from a graveyard, put ten of those tokens onto the battlefield instead.
        this.getSpellAbility().addEffect(new IncreasingDevotionEffect());
        
        // Flashback {7}{W}{W}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{7}{W}{W}"), Constants.TimingRule.SORCERY));
    }

    public IncreasingDevotion(final IncreasingDevotion card) {
        super(card);
    }

    @Override
    public IncreasingDevotion copy() {
        return new IncreasingDevotion(this);
    }
}

class IncreasingDevotionEffect extends OneShotEffect<IncreasingDevotionEffect> {
    
    private static HumanToken token = new HumanToken();
    
    public IncreasingDevotionEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        staticText = "Put five 1/1 white Human creature tokens onto the battlefield. If Increasing Devotion was cast from a graveyard, put ten of those tokens onto the battlefield instead";
    }

    public IncreasingDevotionEffect(final IncreasingDevotionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 5;
        Spell spell = (Spell) game.getStack().getStackObject(source.getSourceId());
        if (spell != null) {
            if (spell.getFromZone() == Constants.Zone.GRAVEYARD) {
                amount = 10;
            }
            token.putOntoBattlefield(amount, game, source.getSourceId(), source.getControllerId());
            return true;
        }
        return false;
    }

    @Override
    public IncreasingDevotionEffect copy() {
        return new IncreasingDevotionEffect(this);
    }

}
