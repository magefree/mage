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
package mage.sets.urzaslegacy;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class Rebuild extends CardImpl<Rebuild> {

    public Rebuild(UUID ownerId) {
        super(ownerId, 40, "Rebuild", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "ULG";

        this.color.setBlue(true);

        // Return all artifacts to their owners' hands.
        this.getSpellAbility().addEffect(new RebuildEffect());
        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));
    }

    public Rebuild(final Rebuild card) {
        super(card);
    }

    @Override
    public Rebuild copy() {
        return new Rebuild(this);
    }
}

class RebuildEffect extends OneShotEffect<RebuildEffect> {
    public RebuildEffect() {
        super(Constants.Outcome.ReturnToHand);
        staticText = "Return all artifacts to their owner's hand.";
    }

    public RebuildEffect(final RebuildEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(new FilterArtifactPermanent(), source.getControllerId(), source.getSourceId(), game)) {
            creature.moveToZone(Constants.Zone.HAND, source.getSourceId(), game, true);
        }
        return true;
    }

    @Override
    public RebuildEffect copy() {
        return new RebuildEffect(this);
    }

}
