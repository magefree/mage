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
import mage.Constants.SubLayer;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.sets.Magic2010;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AwakenerDruid extends CardImpl<AwakenerDruid> {

	private static FilterLandPermanent filter = new FilterLandPermanent("Forest");

	static {
		filter.getSubtype().add("Forest");
	}

	public AwakenerDruid(UUID ownerId) {
		super(ownerId, "Awakener Druid", new CardType[]{CardType.CREATURE}, "{2}{G}");
		this.expansionSetId = Magic2010.getInstance().getId();
		this.color.setGreen(true);
		this.subtype.add("Human");
		this.subtype.add("Druid");
		this.power = new MageInt(1);
		this.toughness = new MageInt(1);
		Ability ability = new EntersBattlefieldTriggeredAbility(new AwakenerDruidEffect(), false);
		ability.addTarget(new TargetLandPermanent(filter));
		this.addAbility(ability);
	}


	public AwakenerDruid(final AwakenerDruid card) {
		super(card);
	}

	@Override
	public AwakenerDruid copy() {
		return new AwakenerDruid(this);
	}

	@Override
	public String getArt() {
		return "121576_typ_reg_sty_010.jpg";
	}
}

class AwakenerDruidEffect extends ContinuousEffectImpl<AwakenerDruidEffect> {

	protected static AwakenerDruidToken token = new AwakenerDruidToken();

	public AwakenerDruidEffect() {
		super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
	}

	public AwakenerDruidEffect(final AwakenerDruidEffect effect) {
		super(effect);
	}

	@Override
	public AwakenerDruidEffect copy() {
		return new AwakenerDruidEffect(this);
	}

	@Override
	public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
		Permanent permanent = game.getPermanent(source.getFirstTarget());
		if (permanent != null) {
			switch (layer) {
				case TypeChangingEffects_4:
					if (sublayer == SubLayer.NA) {
						if (token.getCardType().size() > 0)
							permanent.getCardType().addAll(token.getCardType());
						if (token.getSubtype().size() > 0)
							permanent.getSubtype().addAll(token.getSubtype());
					}
					break;
				case ColorChangingEffects_5:
					if (sublayer == SubLayer.NA) {
						if (token.getColor().hasColor())
							permanent.getColor().setColor(token.getColor());
					}
					break;
				case PTChangingEffects_7:
					if (sublayer == SubLayer.SetPT_7b) {
						if (token.getPower() != MageInt.EmptyMageInt)
							permanent.getPower().setValue(token.getPower().getValue());
						if (token.getToughness() != MageInt.EmptyMageInt)
							permanent.getToughness().setValue(token.getToughness().getValue());
					}
			}
		}
		return true;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return false;
	}

	@Override
	public String getText(Ability source) {
		return "target Forest becomes a 4/5 green Treefolk creature for as long as {this} is on the battlefield. It's still a land.";
	}

	@Override
	public boolean hasLayer(Layer layer) {
		return layer == Layer.PTChangingEffects_7 || layer == Layer.ColorChangingEffects_5 || layer == layer.TypeChangingEffects_4;
	}

}

class AwakenerDruidToken extends Token {

	public AwakenerDruidToken() {
		super("", "4/5 green Treefolk creature");
		cardType.add(CardType.CREATURE);
		subtype.add("Treefolk");
		color.setGreen(true);
		power = new MageInt(4);
		toughness = new MageInt(5);
	}

}