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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class GoblinGoon extends CardImpl {

    public GoblinGoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add("Goblin");
        this.subtype.add("Mutant");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Goblin Goon can't attack unless you control more creatures than defending player.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GoblinGoonCantAttackEffect()));
        
        // Goblin Goon can't block unless you control more creatures than attacking player.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GoblinGoonCantBlockEffect()));
    }

    public GoblinGoon(final GoblinGoon card) {
        super(card);
    }

    @Override
    public GoblinGoon copy() {
        return new GoblinGoon(this);
    }
}

class GoblinGoonCantAttackEffect extends RestrictionEffect {

    GoblinGoonCantAttackEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you control more creatures than defending player";
    }

    GoblinGoonCantAttackEffect(final GoblinGoonCantAttackEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game) {
        UUID defendingPlayerId;
        Player defender = game.getPlayer(defenderId);
        if (defender == null) {
            Permanent permanent = game.getPermanent(defenderId);
            if (permanent != null) {
                defendingPlayerId = permanent.getControllerId();
            }
            else {
                return false;
            }
        }
        else {
            defendingPlayerId = defenderId;
        }
        if (defendingPlayerId != null) {
            return game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), source.getControllerId(), game) > game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), defendingPlayerId, game);
        }
        else {
            return true;
        }
    }

    @Override
    public GoblinGoonCantAttackEffect copy() {
        return new GoblinGoonCantAttackEffect(this);
    }
}

class GoblinGoonCantBlockEffect extends RestrictionEffect {

    GoblinGoonCantBlockEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't block unless you control more creatures than attacking player";
    }

    GoblinGoonCantBlockEffect(final GoblinGoonCantBlockEffect effect) {
        super(effect);
    }

    @Override
    public GoblinGoonCantBlockEffect copy() {
        return new GoblinGoonCantBlockEffect(this);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        UUID attackingPlayerId = attacker.getControllerId();
        if (attackingPlayerId != null) {
            return game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), source.getControllerId(), game) > game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), attackingPlayerId, game);
        }
        return true;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }
}
