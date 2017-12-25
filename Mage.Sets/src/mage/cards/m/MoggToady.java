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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000 & L_J
 */
public class MoggToady extends CardImpl {

    public MoggToady(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Mogg Toady can't attack unless you control more creatures than defending player.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MoggToadyCantAttackEffect()));
        
        // Mogg Toady can't block unless you control more creatures than attacking player.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MoggToadyCantBlockEffect()));
    }

    public MoggToady(final MoggToady card) {
        super(card);
    }

    @Override
    public MoggToady copy() {
        return new MoggToady(this);
    }
}

class MoggToadyCantAttackEffect extends RestrictionEffect {

    MoggToadyCantAttackEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you control more creatures than defending player";
    }

    MoggToadyCantAttackEffect(final MoggToadyCantAttackEffect effect) {
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
    public MoggToadyCantAttackEffect copy() {
        return new MoggToadyCantAttackEffect(this);
    }
}

class MoggToadyCantBlockEffect extends RestrictionEffect {

    MoggToadyCantBlockEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't block unless you control more creatures than attacking player";
    }

    MoggToadyCantBlockEffect(final MoggToadyCantBlockEffect effect) {
        super(effect);
    }

    @Override
    public MoggToadyCantBlockEffect copy() {
        return new MoggToadyCantBlockEffect(this);
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
