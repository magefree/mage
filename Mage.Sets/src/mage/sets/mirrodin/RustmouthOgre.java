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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

/**
 *
 * @author dustinconrad
 */
public class RustmouthOgre extends CardImpl {

    public RustmouthOgre(UUID ownerId) {
        super(ownerId, 103, "Rustmouth Ogre", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Ogre");

        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever Rustmouth Ogre deals combat damage to a player, you may destroy target artifact that player controls.
        this.addAbility(new RustmouthOgreTriggeredAbility());
    }

    public RustmouthOgre(final RustmouthOgre card) {
        super(card);
    }

    @Override
    public RustmouthOgre copy() {
        return new RustmouthOgre(this);
    }
}

class RustmouthOgreTriggeredAbility extends TriggeredAbilityImpl {

    RustmouthOgreTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
    }

    RustmouthOgreTriggeredAbility(final RustmouthOgreTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RustmouthOgreTriggeredAbility copy() {
        return new RustmouthOgreTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
                FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact that player controls");
                filter.add(new ControllerIdPredicate(event.getPlayerId()));
                filter.setMessage("artifact controlled by " + game.getPlayer(event.getTargetId()).getLogName());

                this.getTargets().clear();
                this.addTarget(new TargetPermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may destroy target artifact that player controls.";
    }
}