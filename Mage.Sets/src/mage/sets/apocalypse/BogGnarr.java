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

package mage.sets.apocalypse;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 * @author Loki
 */
public class BogGnarr extends CardImpl<BogGnarr> {

    public BogGnarr(UUID ownerId) {
        super(ownerId, 76, "Bog Gnarr", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.expansionSetCode = "APC";
        this.subtype.add("Beast");
        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new BogGnarrTriggeredAbility());
    }

    public BogGnarr(final BogGnarr card) {
        super(card);
    }

    @Override
    public BogGnarr copy() {
        return new BogGnarr(this);
    }

}

class BogGnarrTriggeredAbility extends TriggeredAbilityImpl<BogGnarrTriggeredAbility> {

    private static final FilterCard filter = new FilterCard("a black spell");

    static {
        filter.setUseColor(true);
        filter.setColor(ObjectColor.BLACK);
    }

    public BogGnarrTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Constants.Duration.EndOfTurn), false);
    }

    public BogGnarrTriggeredAbility(final BogGnarrTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts " + filter.getMessage() + ", " + super.getRule();
    }

    @Override
    public BogGnarrTriggeredAbility copy() {
        return new BogGnarrTriggeredAbility(this);
    }
}