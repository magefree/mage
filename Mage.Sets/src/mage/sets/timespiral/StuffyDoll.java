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
package mage.sets.timespiral;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author North
 */
public class StuffyDoll extends CardImpl<StuffyDoll> {

    public StuffyDoll(UUID ownerId) {
        super(ownerId, 264, "Stuffy Doll", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Construct");
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // As Stuffy Doll enters the battlefield, choose a player.
        this.addAbility(new AsEntersBattlefieldAbility(new StuffyDollChoosePlayerEffect()));
        // Stuffy Doll is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());
        // Whenever Stuffy Doll is dealt damage, it deals that much damage to the chosen player.
        this.addAbility(new StuffyDollTriggeredAbility());
        // {tap}: Stuffy Doll deals 1 damage to itself.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageSelfEffect(), new TapSourceCost()));
    }

    public StuffyDoll(final StuffyDoll card) {
        super(card);
    }

    @Override
    public StuffyDoll copy() {
        return new StuffyDoll(this);
    }
}

class StuffyDollChoosePlayerEffect extends OneShotEffect<StuffyDollChoosePlayerEffect> {

    public StuffyDollChoosePlayerEffect() {
        super(Outcome.Detriment);
        this.staticText = "choose a player";
    }

    public StuffyDollChoosePlayerEffect(final StuffyDollChoosePlayerEffect effect) {
        super(effect);
    }

    @Override
    public StuffyDollChoosePlayerEffect copy() {
        return new StuffyDollChoosePlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            TargetPlayer target = new TargetPlayer();
            target.setRequired(true);
            if (player.choose(this.outcome, target, source.getId(), game)) {
                Player chosenPlayer = game.getPlayer(target.getFirstTarget());
                if (chosenPlayer != null) {
                    game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + chosenPlayer.getName());
                    game.getState().setValue(permanent.getId() + "_player", target.getFirstTarget());
                    return true;
                }
            }
        }
        return false;
    }
}

class StuffyDollTriggeredAbility extends TriggeredAbilityImpl<StuffyDollTriggeredAbility> {

    public StuffyDollTriggeredAbility() {
        super(Zone.BATTLEFIELD, new StuffyDollGainLifeEffect());
    }

    public StuffyDollTriggeredAbility(final StuffyDollTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public StuffyDollTriggeredAbility copy() {
        return new StuffyDollTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE && event.getTargetId().equals(this.sourceId)) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt damage, " + super.getRule();
    }
}

class StuffyDollGainLifeEffect extends OneShotEffect<StuffyDollGainLifeEffect> {

    public StuffyDollGainLifeEffect() {
        super(Outcome.GainLife);
        staticText = "it deals that much damage to the chosen player";
    }

    public StuffyDollGainLifeEffect(final StuffyDollGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public StuffyDollGainLifeEffect copy() {
        return new StuffyDollGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + "_player");
        Player player = game.getPlayer(playerId);
        if (player != null) {
            player.damage((Integer) this.getValue("damageAmount"), source.getId(), game, false, true);
        }
        return true;
    }
}

class DamageSelfEffect extends OneShotEffect<DamageSelfEffect> {

    public DamageSelfEffect() {
        super(Outcome.Damage);
        this.staticText = "Stuffy Doll deals 1 damage to itself";
    }

    public DamageSelfEffect(final DamageSelfEffect effect) {
        super(effect);
    }

    @Override
    public DamageSelfEffect copy() {
        return new DamageSelfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.damage(1, source.getId(), game, true, false);
            return true;
        }
        return false;
    }
}
