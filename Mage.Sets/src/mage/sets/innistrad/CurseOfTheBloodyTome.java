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
package mage.sets.innistrad;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Alvin
 */
public class CurseOfTheBloodyTome extends CardImpl<CurseOfTheBloodyTome> {

    public CurseOfTheBloodyTome(UUID ownerId) {
        super(ownerId, 50, "Curse of the Bloody Tome", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Aura");
        this.subtype.add("Curse");

        this.color.setBlue(true);

        // Enchant player
        TargetPermanent target = new TargetCreaturePermanent();
		this.getSpellAbility().addTarget(target);
		this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.AddAbility));
		Ability ability = new EnchantAbility(target.getTargetName());
		this.addAbility(ability);
        // At the beginning of enchanted player's upkeep, that player puts the top two cards of his or her library into his or her graveyard.
		this.addAbility(new CurseOfTheBloodyTomeAbility());
		
    }

    public CurseOfTheBloodyTome(final CurseOfTheBloodyTome card) {
        super(card);
    }

    @Override
    public CurseOfTheBloodyTome copy() {
        return new CurseOfTheBloodyTome(this);
    }
}

class CurseOfTheBloodyTomeAbility extends TriggeredAbilityImpl<CurseOfTheBloodyTomeAbility> {

	public CurseOfTheBloodyTomeAbility() {
		super(Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(2));
	}

	public CurseOfTheBloodyTomeAbility(final CurseOfTheBloodyTomeAbility ability) {
		super(ability);
	}

	@Override
	public CurseOfTheBloodyTomeAbility copy() {
		return new CurseOfTheBloodyTomeAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.DRAW_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
			this.addTarget(new TargetPlayer());
			getTargets().get(0).add(event.getPlayerId(), game);
			return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "At the beginning of each player's upkeep, that player reveals a card at random from his or her hand. If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.";
	}

}