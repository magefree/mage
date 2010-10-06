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
import mage.Constants.Layer;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ContinuousEffects implements Serializable {

	private final Map<ContinuousEffect, Ability> layeredEffects = new HashMap<ContinuousEffect, Ability>();
	private final Map<ReplacementEffect, Ability> replacementEffects = new HashMap<ReplacementEffect, Ability>();
	private final Map<PreventionEffect, Ability> preventionEffects = new HashMap<PreventionEffect, Ability>();
	private final Map<AsThoughEffect, Ability> asThoughEffects = new HashMap<AsThoughEffect, Ability>();
	private final ApplyCountersEffect applyCounters;

	public ContinuousEffects() {
		applyCounters = new ApplyCountersEffect();
	}

	public ContinuousEffects(final ContinuousEffects effect) {
		this.applyCounters = effect.applyCounters.copy();
		for (Entry<ContinuousEffect, Ability> entry: effect.layeredEffects.entrySet()) {
			layeredEffects.put((ContinuousEffect)entry.getKey().copy(), entry.getValue().copy());
		}
		for (Entry<ReplacementEffect, Ability> entry: effect.replacementEffects.entrySet()) {
			replacementEffects.put((ReplacementEffect)entry.getKey().copy(), entry.getValue().copy());
		}
		for (Entry<PreventionEffect, Ability> entry: effect.preventionEffects.entrySet()) {
			preventionEffects.put((PreventionEffect)entry.getKey().copy(), entry.getValue().copy());
		}
		for (Entry<AsThoughEffect, Ability> entry: effect.asThoughEffects.entrySet()) {
			asThoughEffects.put((AsThoughEffect)entry.getKey().copy(), entry.getValue().copy());
		}
	}

	public ContinuousEffects copy() {
		return new ContinuousEffects(this);
	}

	public void removeEndOfTurnEffects() {
		for (Iterator<ContinuousEffect> i = layeredEffects.keySet().iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
		for (Iterator<ReplacementEffect> i = replacementEffects.keySet().iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
		for (Iterator<PreventionEffect> i = preventionEffects.keySet().iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
		for (Iterator<AsThoughEffect> i = asThoughEffects.keySet().iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
	}

	public void removeInactiveEffects(Game game) {
		for (Iterator<ContinuousEffect> i = layeredEffects.keySet().iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.WhileOnBattlefield) {
				Permanent permanent = game.getPermanent(layeredEffects.get(entry).getSourceId());
				if (permanent == null || !permanent.isPhasedIn())
					i.remove();
			}
			else if (entry.getDuration() == Duration.OneUse && entry.isUsed())
				i.remove();
		}
		for (Iterator<ReplacementEffect> i = replacementEffects.keySet().iterator(); i.hasNext();) {
			ReplacementEffect entry = i.next();
			if (entry.getDuration() == Duration.WhileOnBattlefield) {
				Permanent permanent = game.getPermanent(replacementEffects.get(entry).getSourceId());
				if (permanent == null || !permanent.isPhasedIn())
					i.remove();
			}
			else if (entry.getDuration() == Duration.OneUse && entry.isUsed())
				i.remove();
		}
		for (Iterator<PreventionEffect> i = preventionEffects.keySet().iterator(); i.hasNext();) {
			PreventionEffect entry = i.next();
			if (entry.getDuration() == Duration.WhileOnBattlefield) {
				Permanent permanent = game.getPermanent(preventionEffects.get(entry).getSourceId());
				if (permanent == null || !permanent.isPhasedIn())
					i.remove();
			}
			else if (entry.getDuration() == Duration.OneUse && entry.isUsed())
				i.remove();
		}
		for (Iterator<AsThoughEffect> i = asThoughEffects.keySet().iterator(); i.hasNext();) {
			AsThoughEffect entry = i.next();
			if (entry.getDuration() == Duration.WhileOnBattlefield) {
				Permanent permanent = game.getPermanent(asThoughEffects.get(entry).getSourceId());
				if (permanent == null || !permanent.isPhasedIn())
					i.remove();
			}
			else if (entry.getDuration() == Duration.OneUse && entry.isUsed())
				i.remove();
		}
	}

	private List<ContinuousEffect> getLayeredEffects() {
		List<ContinuousEffect> layerEffects = new ArrayList<ContinuousEffect>(layeredEffects.keySet());
		Collections.sort(layerEffects, new TimestampSorter());
		return layerEffects;
	}

	private List<ReplacementEffect> getApplicableReplacementEffects(GameEvent event, Game game) {
		List<ReplacementEffect> replaceEffects = new ArrayList<ReplacementEffect>();
		for (ReplacementEffect effect: replacementEffects.keySet()) {
			if (effect.applies(event, replacementEffects.get(effect), game)) {
				if (effect.getDuration() != Duration.OneUse || !effect.isUsed())
					replaceEffects.add((ReplacementEffect)effect);
			}
		}
		return replaceEffects;
	}

	public boolean asThough(UUID objectId, AsThoughEffectType type, Game game) {
		for (Entry<AsThoughEffect, Ability> entry: asThoughEffects.entrySet()) {
			AsThoughEffect effect = entry.getKey();
			if (effect.getAsThoughEffectType() == type) {
				if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
					if (effect.applies(objectId, entry.getValue(), game)) {
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
				Player player = game.getPlayer(event.getPlayerId());
				index = player.chooseEffect(rEffects, game);
			}
			caught = rEffects.get(index).replaceEvent(event, replacementEffects.get(rEffects.get(index)), game);
		}

		return caught;
	}

	//20091005 - 613
	public void apply(Game game) {
		removeInactiveEffects(game);
		List<ContinuousEffect> layerEffects = getLayeredEffects();
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.CopyEffects_1, SubLayer.CharacteristicDefining_7a, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.CopyEffects_1, SubLayer.NA, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.ControlChangingEffects_2, SubLayer.CharacteristicDefining_7a, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.ControlChangingEffects_2, SubLayer.NA, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.TextChangingEffects_3, SubLayer.CharacteristicDefining_7a, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.TextChangingEffects_3, SubLayer.NA, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.TypeChangingEffects_4, SubLayer.CharacteristicDefining_7a, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.TypeChangingEffects_4, SubLayer.NA, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.ColorChangingEffects_5, SubLayer.CharacteristicDefining_7a, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.ColorChangingEffects_5, SubLayer.NA, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.CharacteristicDefining_7a, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.SetPT_7b, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, layeredEffects.get(effect), game);
		}
		applyCounters.apply(Layer.PTChangingEffects_7, SubLayer.Counters_7d, null, game);
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.SwitchPT_e, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.PlayerEffects, SubLayer.NA, layeredEffects.get(effect), game);
		}
		for (ContinuousEffect effect: layerEffects) {
			effect.apply(Layer.RulesEffects, SubLayer.NA, layeredEffects.get(effect), game);
		}
	}

	public void addEffect(ContinuousEffect effect, Ability source) {
		if (effect instanceof ReplacementEffect)
			replacementEffects.put((ReplacementEffect)effect, source);
		else if (effect instanceof PreventionEffect)
			preventionEffects.put((PreventionEffect)effect, source);
		else if (effect instanceof AsThoughEffect)
			asThoughEffects.put((AsThoughEffect) effect,source);
		else
			layeredEffects.put(effect, source);
	}

}

class TimestampSorter implements Comparator<ContinuousEffect> {
	@Override
	public int compare(ContinuousEffect one, ContinuousEffect two) {
		return one.getTimestamp().compareTo(two.getTimestamp());
	}
}
