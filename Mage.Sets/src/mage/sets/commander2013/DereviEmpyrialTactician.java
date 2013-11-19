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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class DereviEmpyrialTactician extends CardImpl<DereviEmpyrialTactician> {

    public DereviEmpyrialTactician(UUID ownerId) {
        super(ownerId, 186, "Derevi, Empyrial Tactician", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Bird");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Derevi, Empyrial Tactician enters the battlefield or a creature you control deals combat damage to a player, you may tap or untap target permanent.
        Ability ability = new DereviEmpyrialTacticianTriggeredAbility(new MayTapOrUntapTargetEffect());
        ability.addTarget(new TargetPermanent(true));
        this.addAbility(ability);
        
        // {1}{G}{W}{U}: Put Derevi onto the battlefield from the command zone.
        this.addAbility(new DereviEmpyrialTacticianAbility());
    }

    public DereviEmpyrialTactician(final DereviEmpyrialTactician card) {
        super(card);
    }

    @Override
    public DereviEmpyrialTactician copy() {
        return new DereviEmpyrialTactician(this);
    }
}

class DereviEmpyrialTacticianTriggeredAbility extends TriggeredAbilityImpl {

    public DereviEmpyrialTacticianTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public DereviEmpyrialTacticianTriggeredAbility(DereviEmpyrialTacticianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && event.getTargetId().equals(getSourceId())) {
            return true;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (((DamagedPlayerEvent) event).isCombatDamage()) {
                Permanent creature = game.getPermanent(event.getSourceId());
                if (creature != null && creature.getControllerId().equals(controllerId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} enters the battlefield or a creature you control deals combat damage to a player, you may tap or untap target permanent";
    }

    @Override
    public DereviEmpyrialTacticianTriggeredAbility copy() {
        return new DereviEmpyrialTacticianTriggeredAbility(this);
    }
}

 class DereviEmpyrialTacticianAbility extends ActivatedAbilityImpl<DereviEmpyrialTacticianAbility> {

    public DereviEmpyrialTacticianAbility() {
        super(Zone.COMMAND, new PutCommanderOnBattlefieldEffect(), new ManaCostsImpl("{1}{G}{W}{U}"));
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        Zone currentZone = game.getState().getZone(this.getSourceId());
        if (currentZone == null || !currentZone.equals(Zone.COMMAND)) {
            return false;
        }
        return super.canActivate(playerId, game);
    }

    public DereviEmpyrialTacticianAbility(DereviEmpyrialTacticianAbility ability) {
        super(ability);
    }

    @Override
    public DereviEmpyrialTacticianAbility copy() {
        return new DereviEmpyrialTacticianAbility(this);
    }

}

class PutCommanderOnBattlefieldEffect extends OneShotEffect<PutCommanderOnBattlefieldEffect> {

    public PutCommanderOnBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put Derevi onto the battlefield from the command zone";
    }

    public PutCommanderOnBattlefieldEffect(final PutCommanderOnBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public PutCommanderOnBattlefieldEffect copy() {
        return new PutCommanderOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            card.putOntoBattlefield(game, Zone.COMMAND, source.getSourceId(), source.getControllerId());
            return true;
        }
        return false;
    }
}
