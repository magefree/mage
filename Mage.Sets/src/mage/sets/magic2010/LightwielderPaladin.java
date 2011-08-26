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
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public class LightwielderPaladin extends CardImpl<LightwielderPaladin> {

    public LightwielderPaladin(UUID ownerId) {
        super(ownerId, 19, "Lightwielder Paladin", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "M10";
        this.subtype.add("Human");
        this.subtype.add("Knight");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new LightwielderPaladinTriggeredAbility());
    }

    public LightwielderPaladin(final LightwielderPaladin card) {
        super(card);
    }

    @Override
    public LightwielderPaladin copy() {
        return new LightwielderPaladin(this);
    }
}

class LightwielderPaladinTriggeredAbility extends TriggeredAbilityImpl<LightwielderPaladinTriggeredAbility> {

    public LightwielderPaladinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTargetEffect(), true);
        FilterPermanent filter = new FilterPermanent("black or red permanent");
        filter.getColor().setBlack(true);
        filter.getColor().setRed(true);
        filter.setUseColor(true);
        filter.setScopeColor(ComparisonScope.Any);
        this.addTarget(new TargetPermanent(filter));
    }

    public LightwielderPaladinTriggeredAbility(final LightwielderPaladinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LightwielderPaladinTriggeredAbility copy() {
        return new LightwielderPaladinTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER && event.getSourceId().equals(this.sourceId)
                && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Filter filter = this.getTargets().get(0).getFilter();
            if (filter instanceof FilterPermanent) {
                FilterPermanent filterPermanent = ((FilterPermanent) filter);
                filterPermanent.getControllerId().add(event.getTargetId());
                filterPermanent.setNotController(false);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may exile target black or red permanent that player controls.";
    }
}
