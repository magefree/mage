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
	private final List<ContinuousEffect> layeredEffects = new ArrayList<ContinuousEffect>();
	private final List<ReplacementEffect> replacementEffects = new ArrayList<ReplacementEffect>();
	private final List<PreventionEffect> preventionEffects = new ArrayList<PreventionEffect>();
	private final List<AsThoughEffect> asThoughEffects = new ArrayList<AsThoughEffect>();

	//map Abilities to Continuous effects
	private final Map<ContinuousEffect, Ability> abilityMap = new HashMap<ContinuousEffect, Ability>();

	private final ApplyCountersEffect applyCounters;
	private final PlaneswalkerRedirectionEffect planeswalkerRedirectionEffect;

	public ContinuousEffects() {
		applyCounters = new ApplyCountersEffect();
		planeswalkerRedirectionEffect = new PlaneswalkerRedirectionEffect();
	}

	public ContinuousEffects(final ContinuousEffects effect) {
		this.applyCounters = effect.applyCounters.copy();
		this.planeswalkerRedirectionEffect = effect.planeswalkerRedirectionEffect.copy();
		for (ContinuousEffect entry: effect.layeredEffects) {
			layeredEffects.add((ContinuousEffect) entry.copy());
		}
		for (ReplacementEffect entry: effect.replacementEffects) {
			replacementEffects.add((ReplacementEffect)entry.copy());
		}
		for (PreventionEffect entry: effect.preventionEffects) {
			preventionEffects.add((PreventionEffect)entry.copy());
		}
		for (AsThoughEffect entry: effect.asThoughEffects) {
			asThoughEffects.add((AsThoughEffect)entry.copy());
		}
		for (Entry<ContinuousEffect, Ability> entry: effect.abilityMap.entrySet()) {
			abilityMap.put((ContinuousEffect)entry.getKey().copy(), entry.getValue().copy());
		}
	}

	public ContinuousEffects copy() {
		return new ContinuousEffects(this);
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
		for (Iterator<AsThoughEffect> i = asThoughEffects.iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
	}

	public void removeInactiveEffects(Game game) {
		for (Iterator<ContinuousEffect> i = layeredEffects.iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.WhileOnBattlefield) {
				Permanent permanent = game.getPermanent(abilityMap.get(entry).getSourceId());
				if (permanent == null || !permanent.isPhasedIn())
					i.remove();
			}
			else if (entry.getDuration() == Duration.OneUse && entry.isUsed())
				i.remove();
		}
		for (Iterator<ReplacementEffect> i = replacementEffects.iterator(); i.hasNext();) {
			ReplacementEffect entry = i.next();
			if (entry.getDuration() == Duration.WhileOnBattlefield) {
				Permanent permanent = game.getPermanent(abilityMap.get(entry).getSourceId());
				if (permanent == null || !permanent.isPhasedIn())
					i.remove();
			}
			else if (entry.getDuration() == Duration.OneUse && entry.isUsed())
				i.remove();
		}
		for (Iterator<PreventionEffect> i = preventionEffects.iterator(); i.hasNext();) {
			PreventionEffect entry = i.next();
			if (entry.getDuration() == Duration.WhileOnBattlefield) {
				Permanent permanent = game.getPermanent(abilityMap.get(entry).getSourceId());
				if (permanent == null || !permanent.isPhasedIn())
					i.remove();
			}
			else if (entry.getDuration() == Duration.OneUse && entry.isUsed())
				i.remove();
		}
		for (Iterator<AsThoughEffect> i = asThoughEffects.iterator(); i.hasNext();) {
			AsThoughEffect entry = i.next();
			if (entry.getDuration() == Duration.WhileOnBattlefield) {
				Permanent permanent = game.getPermanent(abilityMap.get(entry).getSourceId());
				if (permanent == null || !permanent.isPhasedIn())
					i.remove();
			}
			else if (entry.getDuration() == Duration.OneUse && entry.isUsed())
				i.remove();
		}
	}

	private List<ContinuousEffect> getLayeredEffects(Game game) {
		List<ContinuousEffect> layerEffects = new ArrayList<ContinuousEffect>(layeredEffects);
		for (Card card: game.getCards()) {
			if (game.getZone(card.getId()) == Zone.HAND || game.getZone(card.getId()) == Zone.GRAVEYARD) {
				for (Ability ability: card.getAbilities().getStaticAbilities(game.getZone(card.getId()))) {
					for (Effect effect: ability.getEffects(EffectType.CONTINUOUS)) {
						layerEffects.add((ContinuousEffect) effect);
						abilityMap.put((ContinuousEffect) effect, ability);
					}
				}
			}
		}
		for (Permanent permanent: game.getBattlefield().getAllPermanents()) {
			for (Ability ability: permanent.getAbilities().getStaticAbilities(Zone.BATTLEFIELD)) {
				for (Effect effect: ability.getEffects(EffectType.CONTINUOUS)) {
					layerEffects.add((ContinuousEffect) effect);
					abilityMap.put((ContinuousEffect) effect, ability);
				}
			}
		}
		Collections.sort(layerEffects, new TimestampSorter());
		return layerEffects;
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
			if (game.getZone(card.getId()) == Zone.HAND || game.getZone(card.getId()) == Zone.GRAVEYARD) {
				for (Ability ability: card.getAbilities().getStaticAbilities(game.getZone(card.getId()))) {
					for (Effect effect: ability.getEffects(EffectType.REPLACEMENT)) {
						ReplacementEffect rEffect = (ReplacementEffect) effect;
						if (rEffect.applies(event, ability, game)) {
							replaceEffects.add(rEffect);
							abilityMap.put(rEffect, ability);
						}
					}
				}
			}
		}
		//get all applicable Replacement effects on the battlefield
		for (Permanent permanent: game.getBattlefield().getAllPermanents()) {
			for (Ability ability: permanent.getAbilities().getStaticAbilities(Zone.BATTLEFIELD)) {
				for (Effect effect: ability.getEffects(EffectType.REPLACEMENT)) {
					ReplacementEffect rEffect = (ReplacementEffect) effect;
					if (rEffect.applies(event, ability, game)) {
						replaceEffects.add(rEffect);
						abilityMap.put(rEffect, ability);
					}
				}
			}
		}
		//get all applicable transient Replacement effects
		for (ReplacementEffect effect: replacementEffects) {
			if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
				if (effect.applies(event, abilityMap.get(effect), game)) {
					replaceEffects.add(effect);
				}
			}
		}
		return replaceEffects;
	}

	public boolean asThough(UUID objectId, AsThoughEffectType type, Game game) {
		for (Permanent permanent: game.getBattlefield().getAllPermanents()) {
			for (Ability ability: permanent.getAbilities().getStaticAbilities(Zone.BATTLEFIELD)) {
				for (Effect effect: ability.getEffects(EffectType.ASTHOUGH)) {
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
					if (effect.applies(objectId, abilityMap.get(entry), game)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean replaceEvent(GameEvent event, Game game) {
		boolean caught = false;
		List<ReplacementEffect> rEffects = getApplicableReplacementEffects(event, game);
		if (rEffects.size() > 0) {
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
			caught = rEffect.replaceEvent(event, abilityMap.get(rEffect), game);
		}

		return caught;
	}

	//20091005 - 613
	public void apply(Game game) {
		removeInactiveEffects(game);
		List<ContinuousEffect> layerEffects = getLayeredEffects(game);
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.CopyEffects_1, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.CopyEffects_1, SubLayer.NA, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.ControlChangingEffects_2, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.ControlChangingEffects_2, SubLayer.NA, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.TextChangingEffects_3, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.TextChangingEffects_3, SubLayer.NA, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.TypeChangingEffects_4, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.TypeChangingEffects_4, SubLayer.NA, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.ColorChangingEffects_5, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.ColorChangingEffects_5, SubLayer.NA, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.SetPT_7b, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, abilityMap.get(effect), game);
		}
		applyCounters.apply(Layer.PTChangingEffects_7, SubLayer.Counters_7d, null, game);
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.SwitchPT_e, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.PlayerEffects, SubLayer.NA, abilityMap.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.RulesEffects, SubLayer.NA, abilityMap.get(effect), game);
		}
	}

	public void addEffect(ContinuousEffect effect, Ability source) {
		switch (effect.getEffectType()) {
			case REPLACEMENT:
				ReplacementEffect newReplacementEffect = (ReplacementEffect)effect.copy();
				newReplacementEffect.setTimestamp();
				replacementEffects.add(newReplacementEffect);
				abilityMap.put(newReplacementEffect, source);
				break;
			case PREVENTION:
				PreventionEffect newPreventionEffect = (PreventionEffect)effect.copy();
				newPreventionEffect.setTimestamp();
				preventionEffects.add(newPreventionEffect);
				abilityMap.put(newPreventionEffect, source);
				break;
			case ASTHOUGH:
				AsThoughEffect newAsThoughEffect = (AsThoughEffect)effect.copy();
				newAsThoughEffect.setTimestamp();
				asThoughEffects.add(newAsThoughEffect);
				abilityMap.put(newAsThoughEffect, source);
				break;
			default:
				ContinuousEffect newEffect = (ContinuousEffect)effect.copy();
				newEffect.setTimestamp();
				layeredEffects.add(newEffect);
				abilityMap.put(newEffect, source);
				break;
		}
	}

}

class TimestampSorter implements Comparator<ContinuousEffect> {
	@Override
	public int compare(ContinuousEffect one, ContinuousEffect two) {
		return one.getTimestamp().compareTo(two.getTimestamp());
	}
}
