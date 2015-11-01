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
package mage.sets.magic2010;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class HiveMind extends CardImpl {

    public HiveMind(UUID ownerId) {
        super(ownerId, 54, "Hive Mind", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}");
        this.expansionSetCode = "M10";

        // Whenever a player casts an instant or sorcery spell, each other player copies that spell. Each of those players may choose new targets for his or her copy.
        this.addAbility(new HiveMindTriggeredAbility());
    }

    public HiveMind(final HiveMind card) {
        super(card);
    }

    @Override
    public HiveMind copy() {
        return new HiveMind(this);
    }
}

class HiveMindTriggeredAbility extends TriggeredAbilityImpl {

    public HiveMindTriggeredAbility() {
        super(Zone.BATTLEFIELD, new HiveMindEffect());
    }

    public HiveMindTriggeredAbility(final HiveMindTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HiveMindTriggeredAbility copy() {
        return new HiveMindTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && (spell.getCardType().contains(CardType.INSTANT)
                || spell.getCardType().contains(CardType.SORCERY))) {
            for (Effect effect : getEffects()) {
                if (effect instanceof HiveMindEffect) {
                    ((HiveMindEffect) effect).setTargetPointer(new FixedTarget(spell.getId()));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an instant or sorcery spell, " + super.getRule();
    }
}

class HiveMindEffect extends OneShotEffect {

    public HiveMindEffect() {
        super(Outcome.Benefit);
        this.staticText = "each other player copies that spell. Each of those players may choose new targets for his or her copy";
    }

    public HiveMindEffect(final HiveMindEffect effect) {
        super(effect);
    }

    @Override
    public HiveMindEffect copy() {
        return new HiveMindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell;
        spell = game.getStack().getSpell(((FixedTarget) getTargetPointer()).getTarget());
        if (spell == null) { // if spell e.g. was countered
            spell = (Spell) game.getLastKnownInformation(((FixedTarget) getTargetPointer()).getTarget(), Zone.STACK);
        }
        Player player = game.getPlayer(source.getControllerId());
        if (spell != null && player != null) {
            for (UUID playerId : game.getState().getPlayersInRange(player.getId(), game)) {
                if (!playerId.equals(spell.getControllerId())) {
                    Spell copy = spell.copySpell();
                    copy.setControllerId(playerId);
                    copy.setCopiedSpell(true);
                    game.getStack().push(copy);
                    copy.chooseNewTargets(game, playerId);
                }
            }
            return true;
        }
        return false;
    }
}
