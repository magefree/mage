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
package mage.sets.eventide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public class DreamFracture extends CardImpl<DreamFracture> {

    public DreamFracture(UUID ownerId) {
        super(ownerId, 19, "Dream Fracture", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        this.expansionSetCode = "EVE";

        this.color.setBlue(true);

        // Counter target spell. Its controller draws a card.
        this.getSpellAbility().addEffect(new DreamFractureEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

    }

    public DreamFracture(final DreamFracture card) {
        super(card);
    }

    @Override
    public DreamFracture copy() {
        return new DreamFracture(this);
    }
}

class DreamFractureEffect extends OneShotEffect<DreamFractureEffect> {

    public DreamFractureEffect() {
        super(Outcome.Neutral);
        this.staticText = "Counter target spell.  Its controller draws a card";
    }

    public DreamFractureEffect(final DreamFractureEffect effect) {
        super(effect);
    }

    @Override
    public DreamFractureEffect copy() {
        return new DreamFractureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        Player controller = null;
        boolean countered = false;
        if (targetId != null) {
            controller = game.getPlayer(game.getControllerId(targetId));
        }
        if (targetId != null
                && game.getStack().counter(targetId, source.getId(), game)) {
            countered = true;
        }
        if (controller != null) {
            controller.drawCards(1, game);
        }
        return countered;
    }
}