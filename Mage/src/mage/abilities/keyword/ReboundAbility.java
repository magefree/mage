/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * This ability has no effect by default and will always return false on the call
 * to apply.  This is because of how the {@link ReboundEffect} works.  It will
 * install the effect if and only if the spell was cast from the {@link Zone#HAND Hand}.
 * <p/>
 * 702.85. Rebound
 * <p/>
 * 702.85a Rebound appears on some instants and sorceries. It represents a static
 * ability that functions while the spell is on the stack and may create a delayed
 * triggered ability. "Rebound" means "If this spell was cast from your hand,
 * instead of putting it into your graveyard as it resolves, exile it and, at
 * the beginning of your next upkeep, you may cast this card from exile without
 * paying its mana cost."
 * <p/>
 * 702.85b Casting a card without paying its mana cost as the result of a rebound
 * ability follows the rules for paying alternative costs in rules 601.2b and 601.2e-g.
 * <p/>
 * 702.85c Multiple instances of rebound on the same spell are redundant.
 *
 * @author maurer.it_at_gmail.com, noxx
 */

public class ReboundAbility extends SimpleStaticAbility  {

    public ReboundAbility() {
        super(Zone.STACK, new ReboundCastFromHandReplacementEffect());
    }

    public ReboundAbility(ReboundAbility ability) {
        super(ability);
    }

    @Override
    public ReboundAbility copy() {
          return new ReboundAbility(this);
    }    
}

class ReboundCastFromHandReplacementEffect extends ReplacementEffectImpl {

    ReboundCastFromHandReplacementEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit);
        this.staticText = "Rebound <i>(If you cast this spell from your hand, exile it as it resolves. At the beginning of your next upkeep, you may cast this card from exile without paying its mana cost.)</i>";
    }

    ReboundCastFromHandReplacementEffect(ReboundCastFromHandReplacementEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getFromZone() == Zone.STACK &&
                ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD &&
                event.getSourceId() == source.getSourceId()) { // if countered the source.sourceId is different or null if it fizzles
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getFromZone().equals(Zone.HAND)) {
                return true;
            }            
        }
        return false;
    }
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell sourceSpell = game.getStack().getSpell(source.getSourceId());
        if (sourceSpell != null && sourceSpell.isCopiedSpell()) {
            return false;
        } else {
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourceCard != null) {
                Player player = game.getPlayer(sourceCard.getOwnerId());
                if (player != null) {
                    // Add the delayed triggered effect
                    ReboundEffectCastFromExileDelayedTrigger trigger = new ReboundEffectCastFromExileDelayedTrigger(source.getSourceId(), source.getSourceId());
                    trigger.setControllerId(source.getControllerId());
                    trigger.setSourceObject(source.getSourceObject(game), game);                    
                    game.addDelayedTriggeredAbility(trigger);
                    
                    player.moveCardToExileWithInfo(sourceCard, sourceCard.getId(), player.getName() + " Rebound", source.getSourceId(), game, Zone.STACK, true);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ReboundCastFromHandReplacementEffect copy() {
        return new ReboundCastFromHandReplacementEffect(this);
    }

}


class ReboundEffectCastFromExileDelayedTrigger extends DelayedTriggeredAbility {

    ReboundEffectCastFromExileDelayedTrigger(UUID cardId, UUID sourceId) {
        super(new ReboundCastSpellFromExileEffect());
        setSourceId(sourceId);
        this.optional = true;
    }

    ReboundEffectCastFromExileDelayedTrigger(ReboundEffectCastFromExileDelayedTrigger ability) {
        super(ability);
    }

    @Override
    public ReboundEffectCastFromExileDelayedTrigger copy() {
        return new ReboundEffectCastFromExileDelayedTrigger(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return MyTurnCondition.getInstance().apply(game, this);
    }
    @Override
    public String getRule() {
        return "Rebound - You may cast {this} from exile without paying its mana cost.";
    }
}

/**
 * Will be triggered by {@link ReboundEffectCastFromExileDelayedTrigger} and will
 * simply cast the spell then remove it from its former home in exile.
 *
 * @author maurer.it_at_gmail.com
 */
class ReboundCastSpellFromExileEffect extends OneShotEffect {

    private static String castFromExileText = "Rebound - You may cast {this} from exile without paying its mana cost";

    ReboundCastSpellFromExileEffect() {
        super(Outcome.Benefit);
        staticText = castFromExileText;
    }

    ReboundCastSpellFromExileEffect(ReboundCastSpellFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone zone = game.getExile().getExileZone(source.getSourceId());
        if (zone == null || zone.isEmpty()) {
            return false;
        }
        Card reboundCard = zone.get(source.getSourceId(), game);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && reboundCard != null) {
            SpellAbility ability = reboundCard.getSpellAbility();
            player.cast(ability, game, true);
            zone.remove(reboundCard.getId());
            return true;
        }
        return false;
    }

    @Override
    public ReboundCastSpellFromExileEffect copy() {
        return new ReboundCastSpellFromExileEffect(this);
    }

}
