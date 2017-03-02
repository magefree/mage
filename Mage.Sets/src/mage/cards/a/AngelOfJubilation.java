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
package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author noxx
 */
public class AngelOfJubilation extends CardImpl {

    private static final FilterCreaturePermanent filterNonBlack = new FilterCreaturePermanent("nonblack creatures");

    static {
        filterNonBlack.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public AngelOfJubilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}{W}");
        this.subtype.add("Angel");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // Other nonblack creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterNonBlack, true)));

        // Players can't pay life or sacrifice creatures to cast spells or activate abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AngelOfJubilationEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AngelOfJubilationSacrificeFilterEffect()));
    }

    public AngelOfJubilation(final AngelOfJubilation card) {
        super(card);
    }

    @Override
    public AngelOfJubilation copy() {
        return new AngelOfJubilation(this);
    }
}

class AngelOfJubilationEffect extends ContinuousEffectImpl {

    public AngelOfJubilationEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "Players can't pay life or sacrifice creatures to cast spells or activate abilities";
    }

    public AngelOfJubilationEffect(final AngelOfJubilationEffect effect) {
        super(effect);
    }

    @Override
    public AngelOfJubilationEffect copy() {
        return new AngelOfJubilationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Player player : game.getPlayers().values()) {
            player.setCanPayLifeCost(false);
            player.setCanPaySacrificeCostFilter(new FilterCreaturePermanent());
        }
        return true;
    }
}

class AngelOfJubilationSacrificeFilterEffect extends CostModificationEffectImpl {

	public AngelOfJubilationSacrificeFilterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.SET_COST);
        staticText = "Players can't pay life or sacrifice creatures to cast spells or activate abilities";
	}
	
    protected AngelOfJubilationSacrificeFilterEffect(AngelOfJubilationSacrificeFilterEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        for (Cost cost : abilityToModify.getCosts()) {
			if(cost instanceof SacrificeTargetCost) {
				SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
				Filter filter = sacrificeCost.getTargets().get(0).getFilter();
				filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
			}
		}
        return true;
    }
    
    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
    	return abilityToModify.getAbilityType() == AbilityType.ACTIVATED ||
    			abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility;
    }

	@Override
	public AngelOfJubilationSacrificeFilterEffect copy() {
		return new AngelOfJubilationSacrificeFilterEffect(this);
	}
	
}
