/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.effects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.Constants.AsThoughEffectType;
import mage.Constants.Duration;
import mage.Constants.EffectType;
import mage.Constants.Layer;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ContinuousEffects implements Serializable {

	//transient Continuous effects
	private final ArrayList<ContinuousEffect> layeredEffects = new ArrayList<ContinuousEffect>();
	private final ArrayList<ReplacementEffect> replacementEffects = new ArrayList<ReplacementEffect>();
	private final ArrayList<PreventionEffect> preventionEffects = new ArrayList<PreventionEffect>();
	private final ArrayList<RequirementEffect> requirementEffects = new ArrayList<RequirementEffect>();
	private final ArrayList<RestrictionEffect> restrictionEffects = new ArrayList<RestrictionEffect>();
	private final ArrayList<AsThoughEffect> asThoughEffects = new ArrayList<AsThoughEffect>();
	private final ArrayList<CostModificationEffect> costModificationEffects = new ArrayList<CostModificationEffect>();

	//map Abilities to Continuous effects
	private final Map<UUID, Ability> abilityMap = new HashMap<UUID, Ability>();

	private final ApplyCountersEffect applyCounters;
	private final PlaneswalkerRedirectionEffect planeswalkerRedirectionEffect;

	public ContinuousEffects() {
		applyCounters = new ApplyCountersEffect();
		planeswalkerRedirectionEffect = new PlaneswalkerRedirectionEffect();
	}

	public ContinuousEffects(final ContinuousEffects effect) {
		this.applyCounters = effect.applyCounters.copy();
		this.planeswalkerRedirectionEffect = effect.planeswalkerRedirectionEffect.copy();
        layeredEffects.ensureCapacity(effect.layeredEffects.size());
		for (ContinuousEffect entry: effect.layeredEffects) {
			layeredEffects.add((ContinuousEffect) entry.copy());
		}
        replacementEffects.ensureCapacity(effect.replacementEffects.size());
		for (ReplacementEffect entry: effect.replacementEffects) {
			replacementEffects.add((ReplacementEffect)entry.copy());
		}
        preventionEffects.ensureCapacity(effect.preventionEffects.size());
		for (PreventionEffect entry: effect.preventionEffects) {
			preventionEffects.add((PreventionEffect)entry.copy());
		}
        requirementEffects.ensureCapacity(effect.requirementEffects.size());
		for (RequirementEffect entry: effect.requirementEffects) {
			requirementEffects.add((RequirementEffect)entry.copy());
		}
        restrictionEffects.ensureCapacity(effect.restrictionEffects.size());
		for (RestrictionEffect entry: effect.restrictionEffects) {
			restrictionEffects.add((RestrictionEffect)entry.copy());
		}
        asThoughEffects.ensureCapacity(effect.asThoughEffects.size());
		for (AsThoughEffect entry: effect.asThoughEffects) {
			asThoughEffects.add((AsThoughEffect)entry.copy());
		}
        costModificationEffects.ensureCapacity(effect.costModificationEffects.size());
		for ( CostModificationEffect entry : effect.costModificationEffects ) {
			costModificationEffects.add(entry);
		}
		for (Entry<UUID, Ability> entry: effect.abilityMap.entrySet()) {
			abilityMap.put(entry.getKey(), entry.getValue().copy());
		}
	}

	public ContinuousEffects copy() {
		return new ContinuousEffects(this);
	}

	public List<RequirementEffect> getRequirementEffects() {
		return requirementEffects;
	}

	public List<RestrictionEffect> getRestrictionEffects() {
		return restrictionEffects;
	}

	public Ability getAbility(UUID effectId) {
		return abilityMap.get(effectId);
	}

	public void removeEndOfTurnEffects() {
		for (Iterator<ContinuousEffect> i = layeredEffects.iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
		for (Iterator<ReplacementEffect> i = replacementEffects.iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
		for (Iterator<PreventionEffect> i = preventionEffects.iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
		for (Iterator<RequirementEffect> i = requirementEffects.iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
		for (Iterator<RestrictionEffect> i = restrictionEffects.iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
		for (Iterator<AsThoughEffect> i = asThoughEffects.iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
		for (Iterator<CostModificationEffect> i = costModificationEffects.iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
	}

	public void removeInactiveEffects(Game game) {
		for (Iterator<ContinuousEffect> i = layeredEffects.iterator(); i.hasNext();) {
			if (isInactive(i.next(), game))
				i.remove();
		}
		for (Iterator<ReplacementEffect> i = replacementEffects.iterator(); i.hasNext();) {
			if (isInactive(i.next(), game))
				i.remove();
		}
		for (Iterator<PreventionEffect> i = preventionEffects.iterator(); i.hasNext();) {
			if (isInactive(i.next(), game))
				i.remove();
		}
		for (Iterator<RequirementEffect> i = requirementEffects.iterator(); i.hasNext();) {
			if (isInactive(i.next(), game))
				i.remove();
		}
		for (Iterator<RestrictionEffect> i = restrictionEffects.iterator(); i.hasNext();) {
			if (isInactive(i.next(), game))
				i.remove();
		}
		for (Iterator<AsThoughEffect> i = asThoughEffects.iterator(); i.hasNext();) {
			if (isInactive(i.next(), game))
				i.remove();
		}
		for (Iterator<CostModificationEffect> i = costModificationEffects.iterator(); i.hasNext();) {
			if (isInactive(i.next(), game))
				i.remove();
		}
	}

	private boolean isInactive(ContinuousEffect effect, Game game) {
		switch(effect.getDuration()) {
			case WhileOnBattlefield:
				Permanent permanent = game.getPermanent(abilityMap.get(effect.getId()).getSourceId());
				return (permanent == null || !permanent.isPhasedIn());
			case OneUse:
				return effect.isUsed();
			case Custom:
				return effect.isInactive(abilityMap.get(effect.getId()), game);
		}
		return false;
	}

	private List<ContinuousEffect> getLayeredEffects(Game game) {
		List<ContinuousEffect> layerEffects = new ArrayList<ContinuousEffect>(layeredEffects);
		for (Card card: game.getCards()) {
            Zone zone = game.getState().getZone(card.getId());
			if (zone == Zone.HAND || zone == Zone.GRAVEYARD) {
                for (Entry<Effect, Ability> entry: card.getAbilities().getEffects(game, zone, EffectType.CONTINUOUS).entrySet()) {
                    layerEffects.add((ContinuousEffect)entry.getKey());
                    abilityMap.put(entry.getKey().getId(), entry.getValue());
                }
			}
		}
		for (Permanent permanent: game.getBattlefield().getAllPermanents()) {
            for (Entry<Effect, Ability> entry: permanent.getAbilities().getEffects(game, Zone.BATTLEFIELD, EffectType.CONTINUOUS).entrySet()) {
                layerEffects.add((ContinuousEffect)entry.getKey());
                abilityMap.put(entry.getKey().getId(), entry.getValue());
            }
		}
		Collections.sort(layerEffects, new TimestampSorter());
		return layerEffects;
	}

	private List<ContinuousEffect> filterLayeredEffects(List<ContinuousEffect> effects, Layer layer) {
		List<ContinuousEffect> layerEffects = new ArrayList<ContinuousEffect>();
		for (ContinuousEffect effect: effects) {
			if (effect.hasLayer(layer))
				layerEffects.add(effect);
		}
		return layerEffects;
	}

	public List<RequirementEffect> getApplicableRequirementEffects(Permanent permanent, Game game) {
		List<RequirementEffect> effects = new ArrayList<RequirementEffect>();
		//get all applicable Requirement effects on the battlefield
		for (Permanent perm: game.getBattlefield().getActivePermanents(permanent.getControllerId(), game)) {
            for (Entry<Effect, Ability> entry: perm.getAbilities().getEffects(game, Zone.BATTLEFIELD, EffectType.REQUIREMENT).entrySet()) {
                if (((RequirementEffect)entry.getKey()).applies(permanent, entry.getValue(), game)) {
                    effects.add((RequirementEffect)entry.getKey());
                    abilityMap.put(entry.getKey().getId(), entry.getValue());
                }
            }
		}
		for (RequirementEffect effect: requirementEffects) {
			if (effect.applies(permanent, abilityMap.get(effect.getId()), game))
				effects.add(effect);
		}
		return effects;
	}

	public List<RestrictionEffect> getApplicableRestrictionEffects(Permanent permanent, Game game) {
		List<RestrictionEffect> effects = new ArrayList<RestrictionEffect>();
		//get all applicable Restriction effects on the battlefield
		for (Permanent perm: game.getBattlefield().getActivePermanents(permanent.getControllerId(), game)) {
            for (Entry<Effect, Ability> entry: perm.getAbilities().getEffects(game, Zone.BATTLEFIELD, EffectType.RESTRICTION).entrySet()) {
                if (((RestrictionEffect)entry.getKey()).applies(permanent, entry.getValue(), game)) {
                    effects.add((RestrictionEffect)entry.getKey());
                    abilityMap.put(entry.getKey().getId(), entry.getValue());
                }
            }
		}
		for (RestrictionEffect effect: restrictionEffects) {
			if (effect.applies(permanent, abilityMap.get(effect.getId()), game))
				effects.add(effect);
		}
		return effects;
	}

	/**
	 *
	 * @param event
	 * @param game
	 * @return a list of all {@link ReplacementEffect} that apply to the current event
	 */
	private List<ReplacementEffect> getApplicableReplacementEffects(GameEvent event, Game game) {
		List<ReplacementEffect> replaceEffects = new ArrayList<ReplacementEffect>();
		if (planeswalkerRedirectionEffect.applies(event, null, game))
			replaceEffects.add(planeswalkerRedirectionEffect);
		//get all applicable Replacement effects in each players hand and graveyard
		for (Card card: game.getCards()) {
            Zone zone = game.getState().getZone(card.getId());
			if (zone == Zone.HAND || zone == Zone.GRAVEYARD) {
                for (Entry<ReplacementEffect, Ability> entry: card.getAbilities().getReplacementEffects(zone).entrySet()) {
                    if (entry.getKey().applies(event, entry.getValue(), game)) {
                        replaceEffects.add(entry.getKey());
						abilityMap.put(entry.getKey().getId(), entry.getValue());
                    }
                }
			}
		}
		//get all applicable Replacement effects on the battlefield
		for (Permanent permanent: game.getBattlefield().getAllPermanents()) {
            for (Entry<ReplacementEffect, Ability> entry: permanent.getAbilities().getReplacementEffects(Zone.BATTLEFIELD).entrySet()) {
                if (entry.getKey().applies(event, entry.getValue(), game)) {
                    replaceEffects.add(entry.getKey());
                    abilityMap.put(entry.getKey().getId(), entry.getValue());
                }
            }
		}
        //get all applicable Replacement effects on players
        for (Player player: game.getPlayers().values()) {
            for (Entry<ReplacementEffect, Ability> entry: player.getAbilities().getReplacementEffects(Zone.BATTLEFIELD).entrySet()) {
                if (entry.getKey().applies(event, entry.getValue(), game)) {
                    replaceEffects.add(entry.getKey());
                    abilityMap.put(entry.getKey().getId(), entry.getValue());
                }
            }
        }
		//get all applicable transient Replacement effects
		for (ReplacementEffect effect: replacementEffects) {
			if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
				if (effect.applies(event, abilityMap.get(effect.getId()), game)) {
					replaceEffects.add(effect);
				}
			}
		}
		for (PreventionEffect effect: preventionEffects) {
			if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
				if (effect.applies(event, abilityMap.get(effect.getId()), game)) {
					replaceEffects.add(effect);
				}
			}
		}
		return replaceEffects;
	}

	public boolean asThough(UUID objectId, AsThoughEffectType type, Game game) {
		for (Permanent permanent: game.getBattlefield().getAllPermanents()) {
			for (Ability ability: permanent.getAbilities().getStaticAbilities(Zone.BATTLEFIELD)) {
				for (Effect effect: ability.getEffects(game, EffectType.ASTHOUGH)) {
					AsThoughEffect rEffect = (AsThoughEffect) effect;
					if (rEffect.applies(objectId, ability, game)) {
						return true;
					}
				}
			}
		}
		for (AsThoughEffect entry: asThoughEffects) {
			AsThoughEffect effect = entry;
			if (effect.getAsThoughEffectType() == type) {
				if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
					if (effect.applies(objectId, abilityMap.get(entry.getId()), game)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Inspects all {@link Permanent permanent's} {@link Ability abilities} on the battlefied
	 * for {@link CostModificationEffect cost modification effects} and applies them if necessary.
	 *
	 * @param abilityToModify
	 * @param game
	 * @return
	 */
	public void costModification ( Ability abilityToModify, Game game ) {
		for ( Permanent permanent : game.getBattlefield().getAllPermanents() ) {
			for ( Ability ability : permanent.getAbilities().getStaticAbilities(Zone.BATTLEFIELD) ) {
				for ( Effect effect : ability.getEffects(game, EffectType.COSTMODIFICATION) ) {
					CostModificationEffect rEffect = (CostModificationEffect)effect;
					if ( rEffect.applies(abilityToModify, ability, game) ) {
						rEffect.apply(game, ability, abilityToModify);
					}
				}
			}
		}
		for ( CostModificationEffect effect : costModificationEffects ) {
			if ( effect.applies(abilityToModify, abilityMap.get(effect.getId()), game) ) {
				effect.apply(game, abilityMap.get(effect.getId()), abilityToModify);
			}
		}
	}

	public boolean replaceEvent(GameEvent event, Game game) {
		boolean caught = false;
        List<UUID> consumed = new ArrayList<UUID>();
        do {
            List<ReplacementEffect> rEffects = getApplicableReplacementEffects(event, game);
            for (Iterator<ReplacementEffect> i = rEffects.iterator(); i.hasNext();) {
                ReplacementEffect entry = i.next();
                if (consumed.contains(entry.getId()))
                    i.remove();
            }
            if (rEffects.isEmpty())
                break;
            int index;
            if (rEffects.size() == 1) {
                index = 0;
            }
            else {
                //20100716 - 616.1c
                Player player = game.getPlayer(event.getPlayerId());
                index = player.chooseEffect(rEffects, game);
            }
            ReplacementEffect rEffect = rEffects.get(index);
            caught = rEffect.replaceEvent(event, abilityMap.get(rEffect.getId()), game);
            if (caught)
                break;
            consumed.add(rEffect.getId());
        } while (true);
		return caught;
	}

	//20091005 - 613
	public void apply(Game game) {
		removeInactiveEffects(game);
		List<ContinuousEffect> layerEffects = getLayeredEffects(game);
		List<ContinuousEffect> layer = filterLayeredEffects(layerEffects, Layer.CopyEffects_1);
//		for (ContinuousEffect effect: layer) {
//			effect.apply(Layer.CopyEffects_1, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect.getId()), game);
//		}
		for (ContinuousEffect effect: layer) {
			effect.apply(Layer.CopyEffects_1, SubLayer.NA, abilityMap.get(effect.getId()), game);
		}
		layer = filterLayeredEffects(layerEffects, Layer.ControlChangingEffects_2);
//		for (ContinuousEffect effect: layer) {
//			effect.apply(Layer.ControlChangingEffects_2, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect.getId()), game);
//		}
		for (ContinuousEffect effect: layer) {
			effect.apply(Layer.ControlChangingEffects_2, SubLayer.NA, abilityMap.get(effect.getId()), game);
		}
		layer = filterLayeredEffects(layerEffects, Layer.TextChangingEffects_3);
//		for (ContinuousEffect effect: layer) {
//			effect.apply(Layer.TextChangingEffects_3, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect.getId()), game);
//		}
		for (ContinuousEffect effect: layer) {
			effect.apply(Layer.TextChangingEffects_3, SubLayer.NA, abilityMap.get(effect.getId()), game);
		}
		layer = filterLayeredEffects(layerEffects, Layer.TypeChangingEffects_4);
//		for (ContinuousEffect effect: layer) {
//			effect.apply(Layer.TypeChangingEffects_4, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect.getId()), game);
//		}
		for (ContinuousEffect effect: layer) {
			effect.apply(Layer.TypeChangingEffects_4, SubLayer.NA, abilityMap.get(effect.getId()), game);
		}
		layer = filterLayeredEffects(layerEffects, Layer.ColorChangingEffects_5);
//		for (ContinuousEffect effect: layer) {
//			effect.apply(Layer.ColorChangingEffects_5, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect.getId()), game);
//		}
		for (ContinuousEffect effect: layer) {
			effect.apply(Layer.ColorChangingEffects_5, SubLayer.NA, abilityMap.get(effect.getId()), game);
		}
		layer = filterLayeredEffects(layerEffects, Layer.AbilityAddingRemovingEffects_6);
//		for (ContinuousEffect effect: layer) {
//			effect.apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect.getId()), game);
//		}
		for (ContinuousEffect effect: layer) {
			effect.apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, abilityMap.get(effect.getId()), game);
		}
		layerEffects = getLayeredEffects(game);
		layer = filterLayeredEffects(layerEffects, Layer.PTChangingEffects_7);
//		for (ContinuousEffect effect: layer) {
//			effect.apply(Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect.getId()), game);
//		}
		for (ContinuousEffect effect: layer) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.SetPT_7b, abilityMap.get(effect.getId()), game);
		}
		for (ContinuousEffect effect: layer) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, abilityMap.get(effect.getId()), game);
		}
		applyCounters.apply(Layer.PTChangingEffects_7, SubLayer.Counters_7d, null, game);
		for (ContinuousEffect effect: layer) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.SwitchPT_e, abilityMap.get(effect.getId()), game);
		}
		layer = filterLayeredEffects(layerEffects, Layer.PlayerEffects);
		for (ContinuousEffect effect: layer) {
			effect.apply(Layer.PlayerEffects, SubLayer.NA, abilityMap.get(effect.getId()), game);
		}
		layer = filterLayeredEffects(layerEffects, Layer.RulesEffects);
		for (ContinuousEffect effect: layer) {
			effect.apply(Layer.RulesEffects, SubLayer.NA, abilityMap.get(effect.getId()), game);
		}
	}

	public void addEffect(ContinuousEffect effect, Ability source) {
		switch (effect.getEffectType()) {
			case REPLACEMENT:
				ReplacementEffect newReplacementEffect = (ReplacementEffect)effect;
				replacementEffects.add(newReplacementEffect);
				abilityMap.put(newReplacementEffect.getId(), source);
				break;
			case PREVENTION:
				PreventionEffect newPreventionEffect = (PreventionEffect)effect;
				preventionEffects.add(newPreventionEffect);
				abilityMap.put(newPreventionEffect.getId(), source);
				break;
			case RESTRICTION:
				RestrictionEffect newRestrictionEffect = (RestrictionEffect)effect;
				restrictionEffects.add(newRestrictionEffect);
				abilityMap.put(newRestrictionEffect.getId(), source);
				break;
			case REQUIREMENT:
				RequirementEffect newRequirementEffect = (RequirementEffect)effect;
				requirementEffects.add(newRequirementEffect);
				abilityMap.put(newRequirementEffect.getId(), source);
				break;
			case ASTHOUGH:
				AsThoughEffect newAsThoughEffect = (AsThoughEffect)effect;
				asThoughEffects.add(newAsThoughEffect);
				abilityMap.put(newAsThoughEffect.getId(), source);
				break;
			case COSTMODIFICATION:
				CostModificationEffect newCostModificationEffect = (CostModificationEffect)effect;
				costModificationEffects.add(newCostModificationEffect);
				abilityMap.put(newCostModificationEffect.getId(), source);
				break;
			default:
				ContinuousEffect newEffect = (ContinuousEffect)effect;
				layeredEffects.add(newEffect);
				abilityMap.put(newEffect.getId(), source);
				break;
		}
	}

    public void clear() {
        layeredEffects.clear();
        replacementEffects.clear();
        preventionEffects.clear();
        requirementEffects.clear();
        restrictionEffects.clear();
        asThoughEffects.clear();
        costModificationEffects.clear();
        abilityMap.clear();
    }

}
class TimestampSorter implements Comparator<ContinuousEffect> {
	@Override
	public int compare(ContinuousEffect one, ContinuousEffect two) {
		return one.getTimestamp().compareTo(two.getTimestamp());
	}
}
