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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class AEtherTradewinds extends CardImpl<AEtherTradewinds> {
    
    private static final FilterPermanent filter = new FilterPermanent("permanent you don't control");
    
    static {
        filter.add(new ControllerPredicate(Constants.TargetController.NOT_YOU));
    }

    public AEtherTradewinds(UUID ownerId) {
        super(ownerId, 24, "AEther Tradewinds", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "WWK";

        this.color.setBlue(true);

        // Return target permanent you control and target permanent you don't control to their owners' hands.
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new AEtherTradewindsEffect());
        
    }

    public AEtherTradewinds(final AEtherTradewinds card) {
        super(card);
    }

    @Override
    public AEtherTradewinds copy() {
        return new AEtherTradewinds(this);
    }
}

class AEtherTradewindsEffect extends OneShotEffect<AEtherTradewindsEffect> {

    public AEtherTradewindsEffect() {
        super(Constants.Outcome.ReturnToHand);
        this.staticText = "Return target permanent you control and target permanent you don't control to their owners' hands";
    }

    public AEtherTradewindsEffect(final AEtherTradewindsEffect effect) {
        super(effect);
    }

    @Override
    public AEtherTradewindsEffect copy() {
        return new AEtherTradewindsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;

        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            result |= permanent.moveToZone(Constants.Zone.HAND, source.getId(), game, false);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            result |= permanent.moveToZone(Constants.Zone.HAND, source.getId(), game, false);
        }

        return result;
    }
}
