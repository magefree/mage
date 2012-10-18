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
package mage.sets.returntoravnica;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksEachTurnStaticAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.turn.Step;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class VolatileRig extends CardImpl<VolatileRig> {

    public VolatileRig(UUID ownerId) {
        super(ownerId, 236, "Volatile Rig", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Construct");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Volatile Rig attacks each turn if able.
        this.addAbility(new AttacksEachTurnStaticAbility());

        // Whenever Volatile Rig is dealt damage, flip a coin. If you lose the flip, sacrifice Volatile Rig.
        this.addAbility(new VolatileRigTriggeredAbility());

        // When Volatile Rig dies, flip a coin. If you lose the flip, it deals 4 damage to each creature and each player.
        this.addAbility(new DiesTriggeredAbility(new VolatileRigEffect2()));


    }

    public VolatileRig(final VolatileRig card) {
        super(card);
    }

    @Override
    public VolatileRig copy() {
        return new VolatileRig(this);
    }
}

class VolatileRigTriggeredAbility extends TriggeredAbilityImpl<VolatileRigTriggeredAbility> {

    private Boolean triggerdThisCombatStep = false;

    public VolatileRigTriggeredAbility() {
        super(Zone.BATTLEFIELD, new VolatileRigEffect());
    }

    public VolatileRigTriggeredAbility(final VolatileRigTriggeredAbility effect) {
        super(effect);
        this.triggerdThisCombatStep = effect.triggerdThisCombatStep;
    }

    @Override
    public VolatileRigTriggeredAbility copy() {
        return new VolatileRigTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        /*
         * If Volatile Rig is dealt damage by multiple sources at the same time 
         * (for example, multiple blocking creatures), its first triggered ability
         * will trigger only once.
         */
        if (triggerdThisCombatStep && event.getType() == EventType.COMBAT_DAMAGE_STEP_POST) {
            triggerdThisCombatStep = false;
        }

        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE && event.getTargetId().equals(this.sourceId)) {
            if (game.getPhase().getStep().getType() == Constants.PhaseStep.COMBAT_DAMAGE) {
                if (triggerdThisCombatStep) {
                    return false;
                } else {
                    triggerdThisCombatStep = true;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt damage, " + super.getRule();
    }
}


class VolatileRigEffect extends OneShotEffect<VolatileRigEffect> {

    VolatileRigEffect() {
        super(Outcome.Sacrifice);
        staticText = "flip a coin. If you lose the flip, sacrifice {this}";
    }

    VolatileRigEffect(final VolatileRigEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!player.flipCoin(game)) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    return permanent.sacrifice(source.getSourceId(), game);
                }
            }
        }
        return false;
    }

    @Override
    public VolatileRigEffect copy() {
        return new VolatileRigEffect(this);
    }
}

class VolatileRigEffect2 extends OneShotEffect<VolatileRigEffect2> {

    VolatileRigEffect2() {
        super(Outcome.Sacrifice);
        staticText = "flip a coin. If you lose the flip, it deals 4 damage to each creature and each player";
    }

    VolatileRigEffect2(final VolatileRigEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!player.flipCoin(game)) {

                List<Permanent> permanents = game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game);
                for (Permanent permanent: permanents) {
                    permanent.damage(4, source.getId(), game, true, false);
                }
                for (UUID playerId: player.getInRange()) {
                    Player damageToPlayer = game.getPlayer(playerId);
                    if (damageToPlayer != null) {
                        damageToPlayer.damage(4, source.getId(), game, false, true);
                    }
                }
                return true;

            }
        }
        return false;
    }

    @Override
    public VolatileRigEffect2 copy() {
        return new VolatileRigEffect2(this);
    }
}