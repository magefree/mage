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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetNonlandPermanent;


/**
 *
 * @author LevelX2
 */
public class CyclonicRift extends CardImpl<CyclonicRift> {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public CyclonicRift(UUID ownerId) {
        super(ownerId, 35, "Cyclonic Rift", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "RTR";

        this.color.setBlue(true);

        // Return target nonland permanent you don't control to its owner's hand.
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(filter));
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());

        // Overload {6}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        this.addAbility(new OverloadAbility(this, new CyclonicRiftEffect(), new ManaCostsImpl("{6}{U}")));
    }

    public CyclonicRift(final CyclonicRift card) {
        super(card);
    }

    @Override
    public CyclonicRift copy() {
        return new CyclonicRift(this);
    }
}

class CyclonicRiftEffect extends OneShotEffect<CyclonicRiftEffect> {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();

    public CyclonicRiftEffect() {
        super(Constants.Outcome.ReturnToHand);
        staticText = "Return each nonland permanent you don't control to its owner's hand";
    }

    public CyclonicRiftEffect(final CyclonicRiftEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            if (!creature.getControllerId().equals(source.getControllerId())) {
                creature.moveToZone(Constants.Zone.HAND, source.getSourceId(), game, true);
            }
            
        }
        return true;
    }

    @Override
    public CyclonicRiftEffect copy() {
        return new CyclonicRiftEffect(this);
    }
}