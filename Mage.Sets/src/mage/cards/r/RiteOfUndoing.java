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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author fireshoes
 */
public class RiteOfUndoing extends CardImpl {
    
    private static final FilterNonlandPermanent filterControlled = new FilterNonlandPermanent("nonland permanent you control");
    private static final FilterNonlandPermanent filterNotControlled = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filterControlled.add(new ControllerPredicate(TargetController.YOU));
        filterNotControlled.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public RiteOfUndoing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}");

        // Delve
        this.addAbility(new DelveAbility());
        
        // Return target nonland permanent you control and target nonland permanent you don't control to their owners' hands.
        this.getSpellAbility().addEffect(new RiteOfUndoingEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(filterControlled));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(filterNotControlled));
    }

    public RiteOfUndoing(final RiteOfUndoing card) {
        super(card);
    }

    @Override
    public RiteOfUndoing copy() {
        return new RiteOfUndoing(this);
    }
}

class RiteOfUndoingEffect extends OneShotEffect {

    public RiteOfUndoingEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target nonland permanent you control and target nonland permanent you don't control to their owners' hands";
    }

    public RiteOfUndoingEffect(final RiteOfUndoingEffect effect) {
        super(effect);
    }

    @Override
    public RiteOfUndoingEffect copy() {
        return new RiteOfUndoingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;

        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            result |= permanent.moveToZone(Zone.HAND, source.getSourceId(), game, false);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            result |= permanent.moveToZone(Zone.HAND, source.getSourceId(), game, false);
        }

        return result;
    }
}