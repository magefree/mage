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
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class SpreadingPlague extends CardImpl {
    
    private static final String RULE = "Whenever a creature enters the battlefield, destroy all other creatures that share a color with it. They can't be regenerated.";

    public SpreadingPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{B}");

        // Whenever a creature enters the battlefield, destroy all other creatures that share a color with it. They can't be regenerated.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new SpreadingPlagueEffect(), new FilterCreaturePermanent(), false, SetTargetPointer.PERMANENT, RULE));
        
    }

    public SpreadingPlague(final SpreadingPlague card) {
        super(card);
    }

    @Override
    public SpreadingPlague copy() {
        return new SpreadingPlague(this);
    }
}

class SpreadingPlagueEffect extends OneShotEffect {
    static final FilterPermanent FILTER = new FilterPermanent("creature");

    static {
        FILTER.add(new CardTypePredicate(CardType.CREATURE));
    }

    SpreadingPlagueEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy all other creatures that share a color with it. They can't be regenerated";
    }

    SpreadingPlagueEffect(final SpreadingPlagueEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature != null) {
            ObjectColor color = creature.getColor(game);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(FILTER, source.getControllerId(), game)) {
                if (permanent.getColor(game).shares(color)
                        && permanent != creature) {
                    permanent.destroy(source.getSourceId(), game, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SpreadingPlagueEffect copy() {
        return new SpreadingPlagueEffect(this);
    }
}