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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Markedagain
 */
public class GlenElendraPranksters extends CardImpl {

    public GlenElendraPranksters(UUID ownerId) {
        super(ownerId, 67, "Glen Elendra Pranksters", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Faerie");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a spell during an opponent's turn, you may return target creature you control to its owner's hand.
        this.addAbility(new GlenElendraPrankstersTriggeredAbility());
    }

    public GlenElendraPranksters(final GlenElendraPranksters card) {
        super(card);
    }

    @Override
    public GlenElendraPranksters copy() {
        return new GlenElendraPranksters(this);
    }
}
class GlenElendraPrankstersTriggeredAbility extends TriggeredAbilityImpl {
    GlenElendraPrankstersTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), true);
        this.addTarget(new TargetControlledCreaturePermanent());
    }

    GlenElendraPrankstersTriggeredAbility(final GlenElendraPrankstersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GlenElendraPrankstersTriggeredAbility copy() {
        return new GlenElendraPrankstersTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId)
                && game.getOpponents(this.controllerId).contains(game.getActivePlayerId());
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell during an opponent's turn, " + super.getRule();
    }
}