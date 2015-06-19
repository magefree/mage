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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.keyword.DethroneAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ScourgeOfTheThrone extends CardImpl {

    public ScourgeOfTheThrone(UUID ownerId) {
        super(ownerId, 184, "Scourge of the Throne", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "VMA";
        this.subtype.add("Dragon");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Dethrone (Whenever this creature attacks the player with the most life or tied for most life, put a +1/+1 counter on it.)
        this.addAbility(new DethroneAbility());
        // Whenever Scourge of the Throne attacks for the first time each turn, if it's attacking the player with the most life or tied for most life, untap all attacking creatures. After this phase, there is an additional combat phase.
        Ability ability = new ScourgeOfTheThroneAttacksTriggeredAbility(new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), "untap all creatures you control"), false);
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);
    }

    public ScourgeOfTheThrone(final ScourgeOfTheThrone card) {
        super(card);
    }

    @Override
    public ScourgeOfTheThrone copy() {
        return new ScourgeOfTheThrone(this);
    }
}

class ScourgeOfTheThroneAttacksTriggeredAbility extends TriggeredAbilityImpl {

    public ScourgeOfTheThroneAttacksTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public ScourgeOfTheThroneAttacksTriggeredAbility(final ScourgeOfTheThroneAttacksTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public void reset(Game game) {
        game.getState().setValue(CardUtil.getCardZoneString("amountAttacks", getSourceId(), game), new Integer(0));
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        UUID defenderId = game.getCombat().getDefenderId(getSourceId());
        if (defenderId != null) {
            Player attackedPlayer = game.getPlayer(defenderId);
            Player controller = game.getPlayer(getControllerId());
            if (attackedPlayer != null && controller != null) {
                int mostLife = Integer.MIN_VALUE;
                for (UUID playerId : controller.getInRange()) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        if (player.getLife() > mostLife) {
                            mostLife = player.getLife();
                        }
                    }
                }
                return attackedPlayer.getLife() == mostLife;
            }
        }
        return false;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            Integer amountAttacks = (Integer) game.getState().getValue(CardUtil.getCardZoneString("amountAttacks", getSourceId(), game));
            if (amountAttacks == null || amountAttacks.intValue() < 1) {
                if (amountAttacks == null) {
                    amountAttacks = new Integer(1);
                } else {
                    ++amountAttacks;
                }
                game.getState().setValue(CardUtil.getCardZoneString("amountAttacks", getSourceId(), game), amountAttacks);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks for the first time each turn, if it's attacking the player with the most life or tied for most life, " + super.getRule();
    }

    @Override
    public ScourgeOfTheThroneAttacksTriggeredAbility copy() {
        return new ScourgeOfTheThroneAttacksTriggeredAbility(this);
    }
}

class AdditionalCombatPhaseEffect extends OneShotEffect {

    public AdditionalCombatPhaseEffect() {
        super(Outcome.Benefit);
        staticText = "After this phase, there is an additional combat phase";
    }

    public AdditionalCombatPhaseEffect(final AdditionalCombatPhaseEffect effect) {
        super(effect);
    }

    @Override
    public AdditionalCombatPhaseEffect copy() {
        return new AdditionalCombatPhaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), TurnPhase.COMBAT, null, false));
        return true;
    }
}
