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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class AngelicBenediction extends CardImpl<AngelicBenediction> {

    public AngelicBenediction(UUID ownerId) {
        super(ownerId, 3, "Angelic Benediction", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "ALA";

        this.color.setWhite(true);

        this.addAbility(new ExaltedAbility());
        // Whenever a creature you control attacks alone, you may tap target creature.
        this.addAbility(new AngelicBenedictionTriggeredAbility());
    }

    public AngelicBenediction(final AngelicBenediction card) {
        super(card);
    }

    @Override
    public AngelicBenediction copy() {
        return new AngelicBenediction(this);
    }
}

class AngelicBenedictionTriggeredAbility extends TriggeredAbilityImpl<AngelicBenedictionTriggeredAbility> {

    public AngelicBenedictionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TapTargetEffect(), true);
        this.addTarget(new TargetCreaturePermanent());
    }

    public AngelicBenedictionTriggeredAbility(final AngelicBenedictionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AngelicBenedictionTriggeredAbility copy() {
        return new AngelicBenedictionTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DECLARED_ATTACKERS && game.getActivePlayerId().equals(this.controllerId)) {
            if (game.getCombat().attacksAlone()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control attacks alone, you may tap target creature";
    }
}