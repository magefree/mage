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
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class SnapsailGlider extends CardImpl<SnapsailGlider> {

    public SnapsailGlider (UUID ownerId) {
        super(ownerId, 203, "Snapsail Glider", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Construct");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SnapsailGliderAbility()));
    }

    public SnapsailGlider (final SnapsailGlider card) {
        super(card);
    }

    @Override
    public SnapsailGlider copy() {
        return new SnapsailGlider(this);
    }
}

class SnapsailGliderAbility extends ContinuousEffectImpl<SnapsailGliderAbility> {

    private static FilterPermanent filter = new FilterPermanent("artifact");

	static {
		filter.getCardType().add(CardType.ARTIFACT);
	}

    public SnapsailGliderAbility() {
        super(Duration.WhileOnBattlefield, Constants.Outcome.AddAbility);
    }

    public SnapsailGliderAbility(final SnapsailGliderAbility ability) {
        super(ability);
    }

    @Override
    public SnapsailGliderAbility copy() {
        return new SnapsailGliderAbility(this);
    }

    @Override
	public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
		Permanent card = game.getPermanent(source.getSourceId());
		if (card != null && game.getBattlefield().countAll(filter, source.getControllerId()) >= 3) {
			switch (layer) {
				case AbilityAddingRemovingEffects_6:
					if (sublayer == Constants.SubLayer.NA) {
						card.addAbility(FlyingAbility.getInstance());
					}
					break;
			}
			return true;
		}
		return false;
	}

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
	public boolean hasLayer(Constants.Layer layer) {
		return layer == Constants.Layer.AbilityAddingRemovingEffects_6;
	}

    @Override
	public String getText(Ability source) {
		return "Metalcraft - Snapsail Glider has flying as long as you control three or more artifacts";
	}
}