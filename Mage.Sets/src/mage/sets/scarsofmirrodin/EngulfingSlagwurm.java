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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class EngulfingSlagwurm extends CardImpl<EngulfingSlagwurm> {

    public EngulfingSlagwurm (UUID ownerId) {
        super(ownerId, 118, "Engulfing Slagwurm", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Wurm");
        this.color.setGreen(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Whenever Engulfing Slagwurm blocks or becomes blocked by a creature, destroy that creature. You gain life equal to that creature's toughness.
        Ability ability = new BlocksOrBecomesBlockedByCreatureTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addEffect(new EngulfingSlagwurmEffect());
        this.addAbility(ability);
    }

    public EngulfingSlagwurm (final EngulfingSlagwurm card) {
        super(card);
    }

    @Override
    public EngulfingSlagwurm copy() {
        return new EngulfingSlagwurm(this);
    }

}

class EngulfingSlagwurmEffect extends OneShotEffect<EngulfingSlagwurmEffect> {
    EngulfingSlagwurmEffect() {
        super(Constants.Outcome.GainLife);
        staticText = "You gain life equal to that creature's toughness";
    }

    EngulfingSlagwurmEffect(final EngulfingSlagwurmEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject c = game.getLastKnownInformation(targetPointer.getFirst(game, source), Constants.Zone.BATTLEFIELD);
        if (c != null && controller != null) {
            controller.gainLife(c.getPower().getValue(), game);
        }
        return false;
    }

    @Override
    public EngulfingSlagwurmEffect copy() {
        return new EngulfingSlagwurmEffect(this);
    }

}
