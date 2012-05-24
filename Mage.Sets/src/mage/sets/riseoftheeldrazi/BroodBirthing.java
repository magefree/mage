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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.EldraziSpawnToken;

/**
 *
 * @author North
 */
public class BroodBirthing extends CardImpl<BroodBirthing> {

    public BroodBirthing(UUID ownerId) {
        super(ownerId, 138, "Brood Birthing", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{R}");
        this.expansionSetCode = "ROE";

        this.color.setRed(true);

        this.getSpellAbility().addEffect(new BroodBirthingEffect());
    }

    public BroodBirthing(final BroodBirthing card) {
        super(card);
    }

    @Override
    public BroodBirthing copy() {
        return new BroodBirthing(this);
    }
}

class BroodBirthingEffect extends OneShotEffect<BroodBirthingEffect> {

    public BroodBirthingEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "If you control an Eldrazi Spawn, put three 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add {1} to your mana pool.\" Otherwise, put one of those tokens onto the battlefield";
    }

    public BroodBirthingEffect(final BroodBirthingEffect effect) {
        super(effect);
    }

    @Override
    public BroodBirthingEffect copy() {
        return new BroodBirthingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Eldrazi Spawn");
        filter.getSubtype().add("Eldrazi");
        filter.getSubtype().add("Spawn");
        filter.setScopeSubtype(ComparisonScope.All);

        EldraziSpawnToken token = new EldraziSpawnToken();
        int count = game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0 ? 3 : 1;
        token.putOntoBattlefield(count, game, source.getSourceId(), source.getControllerId());
        return true;
    }
}
