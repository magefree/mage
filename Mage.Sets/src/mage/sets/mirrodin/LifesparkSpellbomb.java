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
package mage.sets.mirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki
 */
public class LifesparkSpellbomb extends CardImpl<LifesparkSpellbomb> {

    public LifesparkSpellbomb(UUID ownerId) {
        super(ownerId, 197, "Lifespark Spellbomb", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "MRD";
        Ability firstAbility = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new LifesparkSpellbombEffect(), new ColoredManaCost(Constants.ColoredManaSymbol.G));
        firstAbility.addCost(new SacrificeSourceCost());
        firstAbility.addTarget(new TargetLandPermanent());
        this.addAbility(firstAbility);
        Ability secondAbility = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DrawCardControllerEffect(1), new GenericManaCost(1));
        secondAbility.addCost(new SacrificeSourceCost());
        this.addAbility(secondAbility);
    }

    public LifesparkSpellbomb(final LifesparkSpellbomb card) {
        super(card);
    }

    @Override
    public LifesparkSpellbomb copy() {
        return new LifesparkSpellbomb(this);
    }


}

class LifesparkSpellbombEffect extends ContinuousEffectImpl<LifesparkSpellbombEffect> {

    public LifesparkSpellbombEffect() {
        super(Constants.Duration.EndOfTurn, Constants.Outcome.BecomeCreature);
        staticText = "Until end of turn, target land becomes a 3/3 creature that's still a land";
    }

    public LifesparkSpellbombEffect(final LifesparkSpellbombEffect effect) {
        super(effect);
    }

    @Override
	public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
		Permanent permanent = game.getPermanent(source.getFirstTarget());
		if (permanent != null) {
			switch (layer) {
				case TypeChangingEffects_4:
					if (sublayer == Constants.SubLayer.NA) {
                        permanent.getCardType().add(CardType.CREATURE);
					}
					break;
				case PTChangingEffects_7:
					if (sublayer == Constants.SubLayer.SetPT_7b) {
						permanent.getPower().setValue(3);
					    permanent.getToughness().setValue(3);
					}
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
    public LifesparkSpellbombEffect copy() {
        return new LifesparkSpellbombEffect(this);
    }

    @Override
	public boolean hasLayer(Constants.Layer layer) {
		return layer == Constants.Layer.PTChangingEffects_7 || layer == layer.TypeChangingEffects_4;
	}

}