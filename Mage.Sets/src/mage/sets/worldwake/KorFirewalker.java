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

package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author Loki
 */
public class KorFirewalker extends CardImpl<KorFirewalker> {
    private static FilterCard filter = new FilterCard("Red");

    static {
        filter.setUseColor(true);
		filter.getColor().setRed(true);
		filter.setScopeColor(ComparisonScope.Any);
    }

    public KorFirewalker (UUID ownerId) {
        super(ownerId, 11, "Kor Firewalker", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Kor");
        this.subtype.add("Soldier");
		this.color.setWhite(true);
		this.color.setWhite(true);        
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

		this.addAbility(new ProtectionAbility(filter));
        this.addAbility(new KorFirewalkerAbility());

    }

    public KorFirewalker (final KorFirewalker card) {
        super(card);
    }

    @Override
    public KorFirewalker copy() {
        return new KorFirewalker(this);
    }

}

class KorFirewalkerAbility extends TriggeredAbilityImpl<KorFirewalkerAbility> {

	public KorFirewalkerAbility() {
		super(Zone.BATTLEFIELD, new GainLifeEffect(1), true);
	}

	public KorFirewalkerAbility(final KorFirewalkerAbility ability) {
		super(ability);
	}

	@Override
	public KorFirewalkerAbility copy() {
		return new KorFirewalkerAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.SPELL_CAST) {
			Spell spell = game.getStack().getSpell(event.getTargetId());
			if (spell != null && spell.getColor().isRed()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever a player casts a red spell, you may gain 1 life.";
	}
}