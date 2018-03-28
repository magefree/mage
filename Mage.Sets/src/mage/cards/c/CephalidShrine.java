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
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class CephalidShrine extends CardImpl {

    public CephalidShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        // Whenever a player casts a spell, counter that spell unless that player pays {X}, where X is the number of cards in all graveyards with the same name as the spell.
        this.addAbility(new CephalidShrineTriggeredAbility());
    }

    public CephalidShrine(final CephalidShrine card) {
        super(card);
    }

    @Override
    public CephalidShrine copy() {
        return new CephalidShrine(this);
    }
}

class CephalidShrineTriggeredAbility extends TriggeredAbilityImpl {

    public CephalidShrineTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CephalidShrineEffect(), false);
    }

    public CephalidShrineTriggeredAbility(final CephalidShrineTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CephalidShrineTriggeredAbility copy() {
        return new CephalidShrineTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        MageObject mageObject = game.getObject(sourceId);
        if (spell != null) {
            game.getState().setValue("cephalidShrine" + mageObject, spell);
            return true;
        }
        return false;
    }

}

class CephalidShrineEffect extends OneShotEffect {

    public CephalidShrineEffect() {
        super(Outcome.Detriment);
        staticText = "Whenever a player casts a spell, counter that spell unless that player pays {X}, where X is the number of cards in all graveyards with the same name as the spell";
    }

    public CephalidShrineEffect(final CephalidShrineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 0;
        MageObject mageObject = game.getObject(source.getSourceId());
        Spell spell = (Spell) game.getState().getValue("cephalidShrine" + mageObject);
        if (spell != null) {
            Player controller = game.getPlayer(spell.getControllerId());
            if (controller != null) {
                String name = spell.getName();
                FilterCard filterCardName = new FilterCard();
                filterCardName.add(new NamePredicate(name));
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        count += player.getGraveyard().count(filterCardName, game);
                    }
                }
                // even if the cost is 0, we still offer
                Cost cost = new GenericManaCost(count);
                if (game.getStack().contains((StackObject) spell)
                        && cost.canPay(source, source.getSourceId(), controller.getId(), game)
                        && controller.chooseUse(outcome, "Pay " + cost.getText() + " to prevent countering " + spell.getName() + "?", source, game)
                        && cost.pay(source, game, source.getSourceId(), controller.getId(), false)
                        && cost.isPaid()) {
                    return false;
                } else {
                    spell.counter(source.getId(), game);
                    game.informPlayers(spell.getName() + " has been countered due to " + controller.getName() + " not paying " + cost.getText());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public CephalidShrineEffect copy() {
        return new CephalidShrineEffect(this);
    }
}
