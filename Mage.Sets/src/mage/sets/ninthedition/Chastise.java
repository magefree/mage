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
package mage.sets.ninthedition;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author Plopman
 */
public class Chastise extends CardImpl<Chastise> {

    public Chastise(UUID ownerId) {
        super(ownerId, 9, "Chastise", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.expansionSetCode = "9ED";

        this.color.setWhite(true);

        // Destroy target attacking creature. You gain life equal to its power.
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
        this.getSpellAbility().addEffect(new ChastiseEffect());
    }

    public Chastise(final Chastise card) {
        super(card);
    }

    @Override
    public Chastise copy() {
        return new Chastise(this);
    }
}

class ChastiseEffect extends OneShotEffect<ChastiseEffect> {


    public ChastiseEffect() {
        super(Constants.Outcome.DestroyPermanent);
    }



    public ChastiseEffect(final ChastiseEffect effect) {
        super(effect);
    }

    @Override
    public ChastiseEffect copy() {
        return new ChastiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            int power = permanent.getPower().getValue();
            permanent.destroy(source.getId(), game, true);
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(power, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Destroy target attacking creature. You gain life equal to its power";
    }

}