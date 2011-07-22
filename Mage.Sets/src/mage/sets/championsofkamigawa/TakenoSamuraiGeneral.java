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

package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public class TakenoSamuraiGeneral extends CardImpl<TakenoSamuraiGeneral> {

    public TakenoSamuraiGeneral(UUID ownerId) {
        super(ownerId, 46, "Takeno, Samurai General", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Samurai");
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new BushidoAbility(2));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new TakenoSamuraiGeneralEffect()));
    }

    public TakenoSamuraiGeneral(final TakenoSamuraiGeneral card) {
        super(card);
    }

    @Override
    public TakenoSamuraiGeneral copy() {
        return new TakenoSamuraiGeneral(this);
    }

}

class TakenoSamuraiGeneralEffect extends ContinuousEffectImpl<TakenoSamuraiGeneralEffect> {
	private static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.getSubtype().add("Samurai");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

	public TakenoSamuraiGeneralEffect() {
		super(Constants.Duration.WhileOnBattlefield, Constants.Layer.PTChangingEffects_7, Constants.SubLayer.ModifyPT_7c, Constants.Outcome.BoostCreature);
		staticText = "Each other Samurai creature you control gets +1/+1 for each point of bushido it has";
	}

	public TakenoSamuraiGeneralEffect(final TakenoSamuraiGeneralEffect effect) {
		super(effect);
	}

	@Override
	public TakenoSamuraiGeneralEffect copy() {
		return new TakenoSamuraiGeneralEffect(this);
	}

	@Override
	public void init(Ability source, Game game) {
		super.init(source, game);
		if (this.affectedObjectsSet) {
			for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId())) {
				if (!perm.getId().equals(source.getSourceId())) {
                    for (Ability ability : perm.getAbilities()) {
                        if (ability instanceof BushidoAbility) {
                            objects.add(perm.getId());
                        }
                    }
				}
			}
		}
	}

	@Override
	public boolean apply(Game game, Ability source) {
		for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId())) {
			if (!this.affectedObjectsSet || objects.contains(perm.getId())) {
				if (!perm.getId().equals(source.getSourceId())) {
                    for (Ability ability : perm.getAbilities()) {
                        if (ability instanceof BushidoAbility) {
                            perm.addPower(((BushidoAbility) ability).getValue());
                            perm.addToughness(((BushidoAbility) ability).getValue());
                        }
                    }
				}
			}
		}
		return true;
	}

}
