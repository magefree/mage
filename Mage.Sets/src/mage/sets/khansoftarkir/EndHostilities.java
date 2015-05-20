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
package mage.sets.khansoftarkir;

import java.util.ArrayList;
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

/**
 *
 * @author LevelX2
 */
public class EndHostilities extends CardImpl {

    public EndHostilities(UUID ownerId) {
        super(ownerId, 8, "End Hostilities", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");
        this.expansionSetCode = "KTK";


        // Destroy all creatures and all permanents attached to creatures.
        this.getSpellAbility().addEffect(new EndHostilitiesEffect());
    }

    public EndHostilities(final EndHostilities card) {
        super(card);
    }

    @Override
    public EndHostilities copy() {
        return new EndHostilities(this);
    }
}

class EndHostilitiesEffect extends OneShotEffect {

    public EndHostilitiesEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures and all permanents attached to creatures.";
    }

    public EndHostilitiesEffect(final EndHostilitiesEffect effect) {
        super(effect);
    }

    @Override
    public EndHostilitiesEffect copy() {
        return new EndHostilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ArrayList<Permanent> toDestroy = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(controller.getId(), game)) {
                if (permanent.getCardType().contains(CardType.CREATURE)) {
                    toDestroy.add(permanent);
                } else if (permanent.getAttachedTo() != null) {
                    Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
                    if (attachedTo != null && attachedTo.getCardType().contains(CardType.CREATURE)) {
                        toDestroy.add(permanent);                        
                    }
                }
            }
            for (Permanent permanent : toDestroy){
                permanent.destroy(source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }
}
