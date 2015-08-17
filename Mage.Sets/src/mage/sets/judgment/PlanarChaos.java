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
package mage.sets.judgment;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class PlanarChaos extends CardImpl {

    public PlanarChaos(UUID ownerId) {
        super(ownerId, 97, "Planar Chaos", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "JUD";

        // At the beginning of your upkeep, flip a coin. If you lose the flip, sacrifice Planar Chaos.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new PlanarChaosUpkeepEffect(), TargetController.YOU, false));
        
        // Whenever a player casts a spell, that player flips a coin. If he or she loses the flip, counter that spell.
        this.addAbility(new SpellCastAllTriggeredAbility(new PlanarChaosCastAllEffect(), new FilterSpell("a spell"), false, SetTargetPointer.SPELL));
    }

    public PlanarChaos(final PlanarChaos card) {
        super(card);
    }

    @Override
    public PlanarChaos copy() {
        return new PlanarChaos(this);
    }
}

class PlanarChaosUpkeepEffect extends OneShotEffect {

    PlanarChaosUpkeepEffect() {
        super(Outcome.Damage);
        staticText = "flip a coin. If you lose the flip, sacrifice {this}";
    }

    PlanarChaosUpkeepEffect(final PlanarChaosUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!player.flipCoin(game)) {
                Permanent perm = game.getPermanent(source.getSourceId());
                if (perm != null) {
                    perm.sacrifice(source.getSourceId(), game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public PlanarChaosUpkeepEffect copy() {
        return new PlanarChaosUpkeepEffect(this);
    }
}

class PlanarChaosCastAllEffect extends OneShotEffect {

    public PlanarChaosCastAllEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player flips a coin. If he or she loses the flip, counter that spell";
    }

    public PlanarChaosCastAllEffect(final PlanarChaosCastAllEffect effect) {
        super(effect);
    }

    @Override
    public PlanarChaosCastAllEffect copy() {
        return new PlanarChaosCastAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (!controller.flipCoin(game)) {
                game.informPlayers("Planar Chaos: Spell countered");
                return game.getStack().counter(getTargetPointer().getFirst(game, source), source.getSourceId(), game);
            }
        }
        return false;
    }
}