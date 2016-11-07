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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.SliverToken;
import mage.players.Player;

/**
 *
 * @author cbt33, LevelX2 (Ogre Slumlord)
 */
public class BroodSliver extends CardImpl {

    public BroodSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add("Sliver");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Sliver deals combat damage to a player, its controller may create a 1/1 colorless Sliver creature token.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(new BroodSliverEffect(),
                new FilterCreaturePermanent("Sliver", "a Sliver"), false, SetTargetPointer.PLAYER, true));
    }

    public BroodSliver(final BroodSliver card) {
        super(card);
    }

    @Override
    public BroodSliver copy() {
        return new BroodSliver(this);
    }
}

class BroodSliverEffect extends OneShotEffect {

    public BroodSliverEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "its controller may create a 1/1 colorless Sliver creature token";
    }

    public BroodSliverEffect(final BroodSliverEffect effect) {
        super(effect);
    }

    @Override
    public BroodSliverEffect copy() {
        return new BroodSliverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player permanentController = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (permanentController != null) {
            if (permanentController.chooseUse(outcome, "create a 1/1 colorless Sliver creature token", source, game)) {
                return new SliverToken().putOntoBattlefield(1, game, source.getSourceId(), permanentController.getId());
            }
            return true;
        }
        return false;
    }
}
