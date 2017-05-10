/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.iceage;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author nick.myers
 */
public class NakedSingularity extends CardImpl {
    
    public NakedSingularity(UUID ownerId) {
        super(ownerId, 305, "Naked Singularity", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "ICE";
        
        // Cumulative upkeep 3
        this.addAbility(new CumulativeUpkeepAbility(new GenericManaCost(3)));
        
        // If tapped for mana, Plains produce {R}, Islands produce {G}, Swamps produce {W},
        // Mountains produce {U}, and Forests produce {B} instead of any other type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NakedSingularityEffect()));
        
    }
    
    public NakedSingularity(final NakedSingularity card) {
        super(card);
    }
    
    @Override
    public NakedSingularity copy() {
        return new NakedSingularity(this);
    }
}

class NakedSingularityEffect extends ContinuousEffectImpl {
    
    NakedSingularityEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "If tapped for mana, Plains produce {R}, Islands produce {G}, Swamps produce {W}, Mountains produce {U}, and Forests produce {B} instead of any other type";       
    }
    
    NakedSingularityEffect(final NakedSingularityEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
    
    @Override
    public NakedSingularityEffect copy() {
        return new NakedSingularityEffect(this);
    }
    
    @Override 
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent land : game.getBattlefield().getActivePermanents(new FilterLandPermanent(),source.getControllerId(), game)) {
            switch(layer) {
                case AbilityAddingRemovingEffects_6:
                    land.removeAllAbilities(source.getSourceId(), game);
                    // Plains produce {R}
                    if(land.getSubtype().contains("Plains")) {
                        land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                    }
                    // Islands produce {G}
                    if(land.getSubtype().contains("Island")) {
                        land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                    }
                    // Swamps produce {W}
                    if(land.getSubtype().contains("Swamp")) {
                        land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                    }
                    // Mountains produce {U}
                    if(land.getSubtype().contains("Mountain")) {
                        land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                    }
                    // Forests produce {B}
                    if(land.getSubtype().contains("Forest")) {
                        land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                    }
                    break;
            }
        }
        return true;
    }
    
    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
