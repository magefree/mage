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
package mage.sets.magic2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author North
 */
public class VengefulPharaoh extends CardImpl<VengefulPharaoh> {

    public VengefulPharaoh(UUID ownerId) {
        super(ownerId, 116, "Vengeful Pharaoh", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{B}");
        this.expansionSetCode = "M12";
        this.subtype.add("Zombie");

        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.addAbility(DeathtouchAbility.getInstance());
        this.addAbility(new VengefulPharaohTriggeredAbility());
    }

    public VengefulPharaoh(final VengefulPharaoh card) {
        super(card);
    }

    @Override
    public VengefulPharaoh copy() {
        return new VengefulPharaoh(this);
    }
}

class VengefulPharaohTriggeredAbility extends TriggeredAbilityImpl<VengefulPharaohTriggeredAbility> {

    public VengefulPharaohTriggeredAbility() {
        super(Zone.GRAVEYARD, new VengefulPharaohEffect(), false);
        this.addTarget(new TargetAttackingCreature());
    }

    public VengefulPharaohTriggeredAbility(final VengefulPharaohTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VengefulPharaohTriggeredAbility copy() {
        return new VengefulPharaohTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if ((event.getType() == EventType.DAMAGED_PLAYER && event.getTargetId().equals(this.getControllerId()))
                && ((DamagedEvent) event).isCombatDamage()) {
            return true;
        }
        if (event.getType() == EventType.DAMAGED_PLANESWALKER && ((DamagedEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getControllerId().equals(this.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever combat damage is dealt to you or a planeswalker you control, if {this} is in your graveyard, destroy target attacking creature, then put {this} on top of your library.";
    }
}

class VengefulPharaohEffect extends OneShotEffect<VengefulPharaohEffect> {

    public VengefulPharaohEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy target attacking creature, then put {this} on top of your library";
    }

    public VengefulPharaohEffect(final VengefulPharaohEffect effect) {
        super(effect);
    }

    @Override
    public VengefulPharaohEffect copy() {
        return new VengefulPharaohEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.destroy(source.getId(), game, false);
            applied = true;
        }
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (card != null && player != null) {
            player.getGraveyard().remove(card);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
            applied = true;
        }
        return applied;
    }
}
