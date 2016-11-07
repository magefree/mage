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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SquirrelToken;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LoneFox
 */
public class AcornCatapult extends CardImpl {

    public AcornCatapult(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {1}, {tap}: Acorn Catapult deals 1 damage to target creature or player. That creature's controller or that player creates a 1/1 green Squirrel creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AcornCatapultEffect());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public AcornCatapult(final AcornCatapult card) {
        super(card);
    }

    @Override
    public AcornCatapult copy() {
        return new AcornCatapult(this);
    }
}

class AcornCatapultEffect extends OneShotEffect {

    public AcornCatapultEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "that creature's controller or that player creates a 1/1 green Squirrel creature token";
    }

    public AcornCatapultEffect(final AcornCatapultEffect effect) {
        super(effect);
    }

    @Override
    public AcornCatapultEffect copy() {
        return new AcornCatapultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        Player player = game.getPlayer(targetId);
        if (player == null) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                player = game.getPlayer(permanent.getControllerId());
            }
        }

        if (player != null) {
            new SquirrelToken().putOntoBattlefield(1, game, source.getSourceId(), player.getId());
            return true;
        }

        return false;
    }
}
