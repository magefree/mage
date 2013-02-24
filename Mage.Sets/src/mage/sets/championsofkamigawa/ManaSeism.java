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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class ManaSeism extends CardImpl<ManaSeism> {

    public ManaSeism(UUID ownerId) {
        super(ownerId, 179, "Mana Seism", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{R}");
        this.expansionSetCode = "CHK";

        this.color.setRed(true);

        // Sacrifice any number of lands. Add {1} to your mana pool for each land sacrificed this way.
        this.getSpellAbility().addEffect(new ManaSeismEffect());
        
    }

    public ManaSeism(final ManaSeism card) {
        super(card);
    }

    @Override
    public ManaSeism copy() {
        return new ManaSeism(this);
    }
}

class ManaSeismEffect extends OneShotEffect<ManaSeismEffect> {

    public ManaSeismEffect() {
        super(Outcome.Neutral);
        staticText  = "Sacrifice any number of lands. Add {1} to your mana pool for each land sacrificed this way";
    }

    public ManaSeismEffect(final ManaSeismEffect effect) {
        super(effect);
    }

    @Override
    public ManaSeismEffect copy() {
        return new ManaSeismEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null){
            return false;
        }
        int amount = 0;
        TargetControlledPermanent sacrificeLand = new TargetControlledPermanent(0, Integer.MAX_VALUE, new FilterControlledLandPermanent(), true);
        if(player.chooseTarget(Outcome.Sacrifice, sacrificeLand, source, game)){
            for(Object uuid : sacrificeLand.getTargets()){
                Permanent land = game.getPermanent((UUID)uuid);
                if(land != null){
                    land.sacrifice(source.getId(), game);
                    amount++;
                }
            }
        }
        player.getManaPool().addMana(Mana.ColorlessMana(amount), game, source);
        return true;
    }

}
