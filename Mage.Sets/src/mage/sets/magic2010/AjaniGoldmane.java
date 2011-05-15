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
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddPlusOneCountersControlledEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AjaniGoldmane extends CardImpl<AjaniGoldmane> {

	public AjaniGoldmane(UUID ownerId) {
		super(ownerId, 1, "Ajani Goldmane", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");
		this.expansionSetCode = "M10";
		this.subtype.add("Ajani");
		this.color.setWhite(true);
		this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), ""));

		this.addAbility(new LoyaltyAbility(new GainLifeEffect(2), 1));

		Effects effects1 = new Effects();
		effects1.add(new AddPlusOneCountersControlledEffect(1));
		effects1.add(new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, FilterCreaturePermanent.getDefault()));
		this.addAbility(new LoyaltyAbility(effects1, -1));

		this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new AvatarToken()), -6));

	}

	public AjaniGoldmane(final AjaniGoldmane card) {
		super(card);
	}

	@Override
	public AjaniGoldmane copy() {
		return new AjaniGoldmane(this);
	}

	@Override
	public String getArt() {
		return "105545_typ_reg_sty_010.jpg";
	}

}

class AvatarToken extends Token {

	public AvatarToken() {
		super("Avatar", "white Avatar creature token with \"This creature's power and toughness are each equal to your life total.\"");
		cardType.add(CardType.CREATURE);
		subtype.add("Avatar");
		color.setWhite(true);
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AvatarTokenEffect()));
	}

}

class AvatarTokenEffect extends ContinuousEffectImpl<AvatarTokenEffect> {

	public AvatarTokenEffect() {
		super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
	}

	public AvatarTokenEffect(final AvatarTokenEffect effect) {
		super(effect);
	}

	@Override
	public AvatarTokenEffect copy() {
		return new AvatarTokenEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent token = game.getPermanent(source.getSourceId());
		if (token != null) {
			Player controller = game.getPlayer(source.getControllerId());
			if (controller != null) {
				token.getPower().setValue(controller.getLife());
				token.getToughness().setValue(controller.getLife());
				return true;
			}
		}
		return false;
	}

}