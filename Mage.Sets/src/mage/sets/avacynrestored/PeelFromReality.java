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
package mage.sets.avacynrestored;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class PeelFromReality extends CardImpl<PeelFromReality> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.setTargetController(TargetController.NOT_YOU);
    }

    public PeelFromReality(UUID ownerId) {
        super(ownerId, 71, "Peel from Reality", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "AVR";

        this.color.setBlue(true);

        // Return target creature you control and target creature you don't control to their owners' hands.
        this.getSpellAbility().addEffect(new PeelFromRealityEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public PeelFromReality(final PeelFromReality card) {
        super(card);
    }

    @Override
    public PeelFromReality copy() {
        return new PeelFromReality(this);
    }
}

class PeelFromRealityEffect extends OneShotEffect<PeelFromRealityEffect> {

    public PeelFromRealityEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature you control and target creature you don't control to their owners' hands";
    }

    public PeelFromRealityEffect(final PeelFromRealityEffect effect) {
        super(effect);
    }

    @Override
    public PeelFromRealityEffect copy() {
        return new PeelFromRealityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;

        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            result |= permanent.moveToZone(Zone.HAND, source.getId(), game, false);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            result |= permanent.moveToZone(Zone.HAND, source.getId(), game, false);
        }

        return result;
    }
}
