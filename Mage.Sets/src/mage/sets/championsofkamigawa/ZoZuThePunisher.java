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
package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX
 */
public class ZoZuThePunisher extends CardImpl<ZoZuThePunisher> {

    private final static FilterCard filter = new FilterCard("legendary spell");

    static {
        filter.add(new SupertypePredicate("Legendary"));
    }

    public ZoZuThePunisher(UUID ownerId) {
        super(ownerId, 200, "Zo-Zu the Punisher", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");
        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        // Whenever a land enters the battlefield, Zo-Zu the Punisher deals 2 damage to that land's controller.
        this.addAbility(new ZoZuThePunisherAbility());
    }

    public ZoZuThePunisher(final ZoZuThePunisher card) {
            super(card);
    }

    @Override
    public ZoZuThePunisher copy() {
            return new ZoZuThePunisher(this);
    }
}

class ZoZuThePunisherAbility extends TriggeredAbilityImpl<ZoZuThePunisherAbility> {

    public ZoZuThePunisherAbility() {
            super(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(2));
    }

    ZoZuThePunisherAbility(final ZoZuThePunisherAbility ability) {
            super(ability);
    }

    @Override
    public ZoZuThePunisherAbility copy() {
            return new ZoZuThePunisherAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).getToZone() == Constants.Zone.BATTLEFIELD) {
            Permanent permanent = game.getPermanent(event.getTargetId());
                        if (permanent != null && permanent.getCardType().contains(CardType.LAND)) {
                                Player player = game.getPlayer(permanent.getControllerId());
                                if (player != null) {
                                            for (Effect effect : this.getEffects()) {
                                                    effect.setTargetPointer(new FixedTarget(player.getId()));
                                            }
                                            return true;
                                }
            }
            }
            return false;
    }

    @Override
    public String getRule() {
        return "Whenever a land enters the battlefield, Zo-Zu the Punisher deals 2 damage to that land's controller.";
    }
}