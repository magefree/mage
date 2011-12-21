/*
 *  
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 * 
 */
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX
 */

public class KondasBanner extends CardImpl<KondasBanner> {

    private final static FilterControlledCreaturePermanent legendaryFilter = new FilterControlledCreaturePermanent("Legendary creatures");

    static {
        legendaryFilter.getSupertype().add("Legendary");
        legendaryFilter.setScopeSupertype(Filter.ComparisonScope.Any);
    }
    
    public KondasBanner(UUID ownerId) {
        super(ownerId, 259, "Konda's Banner", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Equipment");
        
        // Creatures that share a color with equipped creature get +1/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new KodasBannerColorBoostEffect()));        

        // Creatures that share a creature type with equipped creature get +1/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new KodasBannerTypeBoostEffect()));        
        
        // Konda's Banner can be attached only to a legendary creature.
        // Equip {2}
        this.addAbility(new EquipAbility(
                Constants.Outcome.AddAbility, 
                new GenericManaCost(2), 
                new TargetControlledCreaturePermanent(1,1, legendaryFilter, false)));
        
    }
    
    public KondasBanner(final KondasBanner card) {
        super(card);
    }

    @Override
    public KondasBanner copy() {
        return new KondasBanner(this);
    }        
}

class KodasBannerTypeBoostEffect extends BoostAllEffect  {

	private static final String effectText = "Creatures that share a creature type with equipped creature get +1/+1";
        
	KodasBannerTypeBoostEffect() {
		super(1,1, Constants.Duration.WhileOnBattlefield, new FilterCreaturePermanent(), false);
		staticText = effectText;
	}

	KodasBannerTypeBoostEffect(KodasBannerTypeBoostEffect effect) {
		super(effect);
	}

	@Override
        public boolean apply(Game game, Ability source) {
            // Check if the equipment is attached 
            Permanent equipment = game.getPermanent(source.getSourceId());
	    if (equipment != null && equipment.getAttachedTo() != null)
            {
                Permanent equipedCreature = game.getPermanent(equipment.getAttachedTo());
                for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                        if (CardUtil.shareSubtypes(perm, equipedCreature) || perm.getAbilities().contains(ChangelingAbility.getInstance())) {
            			if (!this.affectedObjectsSet || objects.contains(perm.getId())) {
					perm.addPower(power.calculate(game, source));
					perm.addToughness(toughness.calculate(game, source));
                                }
                            
                        }
		}
		return true;
            }
            return false;
        }

	@Override
	public KodasBannerTypeBoostEffect copy() {
		return new KodasBannerTypeBoostEffect(this);
	}

}


class KodasBannerColorBoostEffect extends BoostAllEffect  {

	private static final String effectText = "Creatures that share a color with equipped creature get +1/+1.";
        
	KodasBannerColorBoostEffect() {
		super(1,1, Constants.Duration.WhileOnBattlefield, new FilterCreaturePermanent(), false);
		staticText = effectText;
	}

	KodasBannerColorBoostEffect(KodasBannerColorBoostEffect effect) {
		super(effect);
	}

	@Override
        public boolean apply(Game game, Ability source) {
            // Check if the equipment is attached 
            Permanent equipment = game.getPermanent(source.getSourceId());
	    if (equipment != null && equipment.getAttachedTo() != null)
            {
                Permanent equipedCreature = game.getPermanent(equipment.getAttachedTo());
                for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                        if (equipedCreature.getColor().shares(perm.getColor())) {
            			if (!this.affectedObjectsSet || objects.contains(perm.getId())) {
					perm.addPower(power.calculate(game, source));
					perm.addToughness(toughness.calculate(game, source));
                                }
                            
                        }
		}
		return true;
            }
            return false;
        }

	@Override
	public KodasBannerColorBoostEffect copy() {
		return new KodasBannerColorBoostEffect(this);
	}

}