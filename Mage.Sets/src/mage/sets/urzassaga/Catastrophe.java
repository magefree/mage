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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class Catastrophe extends CardImpl {

    public Catastrophe(UUID ownerId) {
        super(ownerId, 6, "Catastrophe", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");
        this.expansionSetCode = "USG";


        // Destroy all lands or all creatures. Creatures destroyed this way can't be regenerated.
        this.getSpellAbility().addEffect(new CatastropheEffect());
    }

    public Catastrophe(final Catastrophe card) {
        super(card);
    }

    @Override
    public Catastrophe copy() {
        return new Catastrophe(this);
    }
}

class CatastropheEffect extends OneShotEffect {

    public CatastropheEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy all lands or all creatures. Creatures destroyed this way can't be regenerated";
    }

    public CatastropheEffect(final CatastropheEffect effect) {
        super(effect);
    }

    @Override
    public CatastropheEffect copy() {
        return new CatastropheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(outcome, "Destroy all lands? (otherwise all creatures are destroyed)", game)) {
                for (Permanent permanent:  game.getBattlefield().getActivePermanents(new FilterLandPermanent(), controller.getId(), source.getSourceId(), game)) {
                    permanent.destroy(source.getSourceId(), game, permanent.getCardType().contains(CardType.CREATURE));
                }
            } else {
                for (Permanent permanent:  game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), controller.getId(), source.getSourceId(), game)) {
                    permanent.destroy(source.getSourceId(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
