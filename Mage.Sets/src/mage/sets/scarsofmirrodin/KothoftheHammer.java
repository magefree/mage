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
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki
 */
public class KothoftheHammer extends CardImpl<KothoftheHammer> {
    static FilterLandPermanent filter = new FilterLandPermanent("Mountain");

    static {
        filter.getSubtype().add("Mountain");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public KothoftheHammer (UUID ownerId) {
        super(ownerId, 94, "Koth of the Hammer", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Koth");
		this.color.setRed(true);
        this.loyalty = new MageInt(3);
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addEffect(new KothoftheHammerFirstEffect());
        ability.addTarget(new TargetLandPermanent(filter));
        this.addAbility(ability);
        this.addAbility(new LoyaltyAbility(new KothoftheHammerSecondEffect(), -2));
        this.addAbility(new LoyaltyAbility(new KothoftheHammerThirdEffect(), -5));
    }

    public KothoftheHammer (final KothoftheHammer card) {
        super(card);
    }

    @Override
    public KothoftheHammer copy() {
        return new KothoftheHammer(this);
    }
}

class KothoftheHammerFirstEffect extends ContinuousEffectImpl<KothoftheHammerFirstEffect> {

    public KothoftheHammerFirstEffect() {
        super(Duration.EndOfTurn, Constants.Outcome.BecomeCreature);
    }

    public KothoftheHammerFirstEffect(final KothoftheHammerFirstEffect effect) {
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
                        permanent.getSubtype().add("Elemental");
					}
					break;
				case ColorChangingEffects_5:
					if (sublayer == Constants.SubLayer.NA) {
                        permanent.getColor().setRed(true);
					}
					break;
				case PTChangingEffects_7:
					if (sublayer == Constants.SubLayer.SetPT_7b) {
						permanent.getPower().setValue(4);
					    permanent.getToughness().setValue(4);
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
    public KothoftheHammerFirstEffect copy() {
        return new KothoftheHammerFirstEffect(this);
    }

    @Override
	public boolean hasLayer(Constants.Layer layer) {
		return layer == Constants.Layer.PTChangingEffects_7 || layer == Constants.Layer.ColorChangingEffects_5 || layer == layer.TypeChangingEffects_4;
	}

    @Override
    public String getText(Ability source) {
        return "It becomes a 4/4 red Elemental creature until end of turn. It's still a land";
    }
}

class KothoftheHammerSecondEffect extends OneShotEffect<KothoftheHammerSecondEffect> {
    public KothoftheHammerSecondEffect() {
        super(Constants.Outcome.PutManaInPool);
    }

    public KothoftheHammerSecondEffect(final KothoftheHammerSecondEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = game.getBattlefield().count(KothoftheHammer.filter, source.getControllerId(), game);
        int current = game.getPlayer(source.getControllerId()).getManaPool().getRed();
        game.getPlayer(source.getControllerId()).getManaPool().setRed(count + current);
        return true;
    }

    @Override
    public KothoftheHammerSecondEffect copy() {
        return new KothoftheHammerSecondEffect(this);
    }

    @Override
	public String getText(Ability source) {
		return "Add {R} to your mana pool for each Mountain you control";
	}
}

class KothoftheHammerThirdEffect extends ContinuousEffectImpl<KothoftheHammerThirdEffect> {
    public KothoftheHammerThirdEffect() {
        super(Duration.EndOfGame, Constants.Outcome.AddAbility);
    }

    public KothoftheHammerThirdEffect(final KothoftheHammerThirdEffect effect) {
        super(effect);
    }

    @Override
	public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case AbilityAddingRemovingEffects_6:
                if (sublayer == Constants.SubLayer.NA) {
                    for (Permanent p : game.getBattlefield().getActivePermanents(KothoftheHammer.filter, source.getControllerId(), game)) {
                        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
                        ability.addTarget(new TargetCreatureOrPlayer());
                        p.addAbility(ability);
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public KothoftheHammerThirdEffect copy() {
        return new KothoftheHammerThirdEffect(this);
    }

    @Override
	public boolean hasLayer(Constants.Layer layer) {
		return layer == Constants.Layer.AbilityAddingRemovingEffects_6;
	}

    @Override
    public String getText(Ability source) {
        return "You get an emblem with \"Mountains you control have '{T}: This land deals 1 damage to target creature or player.'\"";
    }
}