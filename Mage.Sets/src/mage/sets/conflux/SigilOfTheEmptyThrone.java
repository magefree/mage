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

package mage.sets.conflux;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;

/**
 *
 * @author Loki
 */
public class SigilOfTheEmptyThrone extends CardImpl<SigilOfTheEmptyThrone> {

    public SigilOfTheEmptyThrone(UUID ownerId) {
        super(ownerId, 18, "Sigil of the Empty Throne", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");
        this.expansionSetCode = "CON";
        this.color.setWhite(true);
        this.addAbility(new SigilOfTheEmptyThroneAbility());
    }

    public SigilOfTheEmptyThrone(final SigilOfTheEmptyThrone card) {
        super(card);
    }

    @Override
    public SigilOfTheEmptyThrone copy() {
        return new SigilOfTheEmptyThrone(this);
    }

    @Override
    public String getArt() {
        return "118726_typ_reg_sty_010.jpg";
    }

}

class SigilOfTheEmptyThroneAbility extends TriggeredAbilityImpl<SigilOfTheEmptyThroneAbility> {
    public SigilOfTheEmptyThroneAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new AngelToken()), false);
    }

    public SigilOfTheEmptyThroneAbility(final SigilOfTheEmptyThroneAbility ability) {
        super(ability);
    }

    @Override
    public SigilOfTheEmptyThroneAbility copy() {
        return new SigilOfTheEmptyThroneAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.SPELL_CAST) {
			Spell spell = game.getStack().getSpell(event.getTargetId());
			if (spell != null && spell.getCardType().contains(CardType.ENCHANTMENT) && event.getPlayerId().equals(getControllerId())) {
				trigger(game, event.getPlayerId());
				return true;
            }
		}
		return false;
    }

    @Override
    public String getRule() {
	return "Whenever you cast an enchantment spell, put a 4/4 white Angel creature token with flying onto the battlefield.";
    }
}

class AngelToken extends Token {
    public AngelToken() {
        super("Angel", "4/4 white Angel creature token with flying");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.WHITE;
        subtype.add("Angel");
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }
}