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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author cbt33
 */
public class LaquatussCreativity extends CardImpl<LaquatussCreativity> {

    public LaquatussCreativity(UUID ownerId) {
        super(ownerId, 88, "Laquatus's Creativity", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{U}");
        this.expansionSetCode = "ODY";

        this.color.setBlue(true);

        // Target player draws cards equal to the number of cards in his or her hand, then discards that many cards.
        this.getSpellAbility().addEffect(new LaquatussCreativityEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(true));
    }

    public LaquatussCreativity(final LaquatussCreativity card) {
        super(card);
    }

    @Override
    public LaquatussCreativity copy() {
        return new LaquatussCreativity(this);
    }
}

class LaquatussCreativityEffect extends OneShotEffect<LaquatussCreativityEffect> {
    
    public LaquatussCreativityEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player draws cards equal to the number of cards in his or her hand, then discards that many cards.";
    }
    
    public LaquatussCreativityEffect(final LaquatussCreativityEffect effect) {
        super(effect);
    }
    
    @Override
    public LaquatussCreativityEffect copy() {
        return new LaquatussCreativityEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        int handCount = player.getHand().count(new FilterCard(), game);
        player.drawCards(handCount, game);
        player.discard(handCount, source, game);
        return false;
    }
}
