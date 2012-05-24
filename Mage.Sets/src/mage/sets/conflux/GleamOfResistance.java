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
package mage.sets.conflux;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public class GleamOfResistance extends CardImpl<GleamOfResistance> {

    public GleamOfResistance(UUID ownerId) {
        super(ownerId, 8, "Gleam of Resistance", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{4}{W}");
        this.expansionSetCode = "CON";

        this.color.setWhite(true);

        // Creatures you control get +1/+2 until end of turn. Untap those creatures.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 2, Constants.Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GleamOfResistanceEffect());
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl("{1}{W}")));
    }

    public GleamOfResistance(final GleamOfResistance card) {
        super(card);
    }

    @Override
    public GleamOfResistance copy() {
        return new GleamOfResistance(this);
    }
}

class GleamOfResistanceEffect extends OneShotEffect<GleamOfResistanceEffect> {
    GleamOfResistanceEffect() {
        super(Constants.Outcome.Untap);
        staticText = "Untap those creatures";
    }

    GleamOfResistanceEffect(final GleamOfResistanceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm: game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
            if (perm.isTapped()) {
                perm.untap(game);
            }
        }
        return true;
    }

    @Override
    public GleamOfResistanceEffect copy() {
        return new GleamOfResistanceEffect(this);
    }
}
