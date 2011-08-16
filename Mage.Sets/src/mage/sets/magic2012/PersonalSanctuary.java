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
package mage.sets.magic2012;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author nantuko
 */
public class PersonalSanctuary extends CardImpl<PersonalSanctuary> {

    public PersonalSanctuary(UUID ownerId) {
        super(ownerId, 30, "Personal Sanctuary", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "M12";

        this.color.setWhite(true);

        // During your turn, prevent all damage that would be dealt to you.
		this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new PersonalSanctuaryEffect()));
    }

    public PersonalSanctuary(final PersonalSanctuary card) {
        super(card);
    }

    @Override
    public PersonalSanctuary copy() {
        return new PersonalSanctuary(this);
    }
}

class PersonalSanctuaryEffect extends PreventionEffectImpl<PersonalSanctuaryEffect> {

	public PersonalSanctuaryEffect() {
		super(Constants.Duration.WhileOnBattlefield);
		staticText = "During your turn, prevent all damage that would be dealt to you";
	}

	public PersonalSanctuaryEffect(PersonalSanctuaryEffect effect) {
		super(effect);
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
		if (!game.replaceEvent(preventEvent)) {
			int damage = event.getAmount();
			event.setAmount(0);
			game.informPlayers("Damage has been prevented: " + damage);
			game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
		}
		return false;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		if (event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)) {
			if (event.getTargetId().equals(source.getControllerId()) && game.getActivePlayerId().equals(source.getControllerId()))
				return super.applies(event, source, game);
		}
		return false;
	}

	@Override
	public PersonalSanctuaryEffect copy() {
		return new PersonalSanctuaryEffect(this);
	}
}