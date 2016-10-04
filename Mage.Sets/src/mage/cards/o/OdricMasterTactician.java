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
package mage.sets.magic2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 * @author noxx
 */
public class OdricMasterTactician extends CardImpl {

    public OdricMasterTactician(UUID ownerId) {
        super(ownerId, 23, "Odric, Master Tactician", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "M13";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Odric, Master Tactician and at least three other creatures attack, you choose which creatures block this combat and how those creatures block.
        this.addAbility(new OdricMasterTacticianTriggeredAbility());
    }

    public OdricMasterTactician(final OdricMasterTactician card) {
        super(card);
    }

    @Override
    public OdricMasterTactician copy() {
        return new OdricMasterTactician(this);
    }
}

class OdricMasterTacticianTriggeredAbility extends TriggeredAbilityImpl {

    public OdricMasterTacticianTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OdricMasterTacticianEffect());
    }

    public OdricMasterTacticianTriggeredAbility(final OdricMasterTacticianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OdricMasterTacticianTriggeredAbility copy() {
        return new OdricMasterTacticianTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        resetEffect();
        if (game.getCombat().getAttackers().size() >= 4 && game.getCombat().getAttackers().contains(this.sourceId)) {
            enableEffect();
            return true;
        }
        return false;
    }

    @Override
    public void reset(Game game) {
        resetEffect();
    }

    private void resetEffect() {
        getEffects().get(0).setValue("apply_" + sourceId, false);
    }

    private void enableEffect() {
        getEffects().get(0).setValue("apply_" + sourceId, true);
    }

    @Override
    public String getRule() {
        return "Whenever {this} and at least three other creatures attack, you choose which creatures block this combat and how those creatures block.";
    }

}

class OdricMasterTacticianEffect extends ReplacementEffectImpl {

    public OdricMasterTacticianEffect() {
        super(Duration.EndOfCombat, Outcome.Benefit);
    }

    public OdricMasterTacticianEffect(final OdricMasterTacticianEffect effect) {
        super(effect);
    }

    @Override
    public OdricMasterTacticianEffect copy() {
        return new OdricMasterTacticianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player blockController = game.getPlayer(source.getControllerId());
        if (blockController != null) {
            game.getCombat().selectBlockers(blockController, game);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARING_BLOCKERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Object object = getValue("apply_" + source.getSourceId());
        if (object != null && object instanceof Boolean) {
            if ((Boolean)object) {
                return true; // replace event
            }
        }
        return false;
    }
}
