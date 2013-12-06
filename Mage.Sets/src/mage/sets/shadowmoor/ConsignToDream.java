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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 *
 */
public class ConsignToDream extends CardImpl<ConsignToDream> {

    public ConsignToDream(UUID ownerId) {
        super(ownerId, 32, "Consign to Dream", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "SHM";

        this.color.setBlue(true);

        // Return target permanent to its owner's hand. If that permanent is red or green, put it on top of its owner's library instead.
        this.getSpellAbility().addEffect(new ConsignToDreamEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(true));

    }

    public ConsignToDream(final ConsignToDream card) {
        super(card);
    }

    @Override
    public ConsignToDream copy() {
        return new ConsignToDream(this);
    }
}

class ConsignToDreamEffect extends OneShotEffect<ConsignToDreamEffect> {

    boolean applied = false;

    public ConsignToDreamEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target permanent to its owner's hand. If that permanent is red or green, put it on top of its owner's library instead";
    }

    public ConsignToDreamEffect(final ConsignToDreamEffect effect) {
        super(effect);
    }

    @Override
    public ConsignToDreamEffect copy() {
        return new ConsignToDreamEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            if (target.getColor().contains(ObjectColor.RED)
                    || target.getColor().contains(ObjectColor.GREEN)) {
                applied = target.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            } else {
                applied = target.moveToZone(Zone.HAND, source.getSourceId(), game, false);
            }
        }
        return applied;
    }
}