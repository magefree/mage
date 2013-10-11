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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33
 */
public class BlazingSalvo extends CardImpl<BlazingSalvo> {

    public BlazingSalvo(UUID ownerId) {
        super(ownerId, 178, "Blazing Salvo", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "ODY";

        this.color.setRed(true);

        // Blazing Salvo deals 3 damage to target creature unless that creature's controller has Blazing Salvo deal 5 damage to him or her.
    this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
    this.getSpellAbility().addEffect(new BlazingSalvoEffect());
    }

    public BlazingSalvo(final BlazingSalvo card) {
        super(card);
    }

    @Override
    public BlazingSalvo copy() {
        return new BlazingSalvo(this);
    }
}

class BlazingSalvoEffect extends OneShotEffect<BlazingSalvoEffect> {
    
    public BlazingSalvoEffect() {
        super(Outcome.Damage);
        this.staticText = "Blazing Salvo deals 3 damage to target creature unless that creature's controller has Blazing Salvo deal 5 damage to him or her.";
    }
    
    public BlazingSalvoEffect(final BlazingSalvoEffect effect){
        super(effect);
    }
    
    @Override
    public BlazingSalvoEffect copy() {
        return new BlazingSalvoEffect(this);
    }

@Override
public boolean apply(Game game, Ability source) {
    Permanent permanent = game.getPermanent(source.getFirstTarget());
    if (permanent!=null) {
    Player player = game.getPlayer(permanent.getOwnerId());
    String message = "Have Blazing Salvo do 5 damage to you?";
    if (player.chooseUse(Outcome.Damage, message, game)){
        player.damage(5, source.getSourceId(), game, false, true);
    } else {
        permanent.damage(3, source.getSourceId(), game, false, true);
    }
    }
    return false;
}

}
