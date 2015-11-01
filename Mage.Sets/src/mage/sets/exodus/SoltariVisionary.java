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
package mage.sets.exodus;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class SoltariVisionary extends CardImpl {

    public SoltariVisionary(UUID ownerId) {
        super(ownerId, 20, "Soltari Visionary", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "EXO";
        this.subtype.add("Soltari");
        this.subtype.add("Cleric");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());

        // Whenever Soltari Visionary deals damage to a player, destroy target enchantment that player controls.
        this.addAbility(new SoltariVisionaryTriggeredAbility());
    }

    public SoltariVisionary(final SoltariVisionary card) {
        super(card);
    }

    @Override
    public SoltariVisionary copy() {
        return new SoltariVisionary(this);
    }
}

class SoltariVisionaryTriggeredAbility extends TriggeredAbilityImpl {

    SoltariVisionaryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    SoltariVisionaryTriggeredAbility(final SoltariVisionaryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SoltariVisionaryTriggeredAbility copy() {
        return new SoltariVisionaryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent soltari = game.getPermanent(event.getSourceId());
        if (soltari != null && soltari.getId().equals(this.getSourceId())) {
            FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("enchantment that player controls.");
            filter.add(new ControllerIdPredicate(event.getPlayerId()));
            filter.setMessage("enchantment controlled by " + game.getPlayer(event.getTargetId()).getLogName());
            this.getTargets().clear();
            this.addTarget(new TargetPermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to a player, destroy target enchantment that player controls.";
    }
}
