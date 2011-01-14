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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author nantuko
 */
public class RazorHippogriff extends CardImpl<RazorHippogriff> {

    public RazorHippogriff (UUID ownerId) {
        super(ownerId, 17, "Razor Hippogriff", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Hippogriff");
		this.color.setWhite(true);
        this.power = new MageInt(3);
      	this.toughness = new MageInt(3);
		this.addAbility(FlyingAbility.getInstance());



		Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
		//TargetCard target = new TargetCard(Zone.GRAVEYARD, FilterArtifactCard.getDefault());
		TargetCard target = new TargetCardInGraveyard(FilterArtifactCard.getDefault());
		target.setRequired(true);
		target.setTargetName("artifact card in your graveyard");
		ability.addTarget(target);
		ability.addEffect(new RazorHippogriffGainLifeEffect());

		this.addAbility(ability);
    }

    public RazorHippogriff (final RazorHippogriff card) {
        super(card);
    }

    @Override
    public RazorHippogriff copy() {
        return new RazorHippogriff(this);
    }

	public class RazorHippogriffGainLifeEffect extends OneShotEffect<RazorHippogriffGainLifeEffect> {

		public RazorHippogriffGainLifeEffect() {
			super(Constants.Outcome.GainLife);
		}

		public RazorHippogriffGainLifeEffect(final RazorHippogriffGainLifeEffect effect) {
			super(effect);
		}

		@Override
		public RazorHippogriffGainLifeEffect copy() {
			return new RazorHippogriffGainLifeEffect(this);
		}

		@Override
		public boolean apply(Game game, Ability source) {
			Player player = game.getPlayer(source.getControllerId());
			if (player != null) {
				MageObject card = game.getObject(source.getFirstTarget());
				if (card != null) {
					player.gainLife(card.getManaCost().convertedManaCost(), game);
				}
			}
			return true;
		}

		@Override
		public String getText(Ability source) {
			return "you gain life equal to that card's converted mana cost.";
		}

	}

}
