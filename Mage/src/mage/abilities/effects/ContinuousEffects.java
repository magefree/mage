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
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.SubLayer;
import mage.counters.BoostCounter;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;



/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ContinuousEffects implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ContinuousEffect> effects = new ArrayList<ContinuousEffect>();

	public void removeEndOfTurnEffects() {
		for (Iterator<ContinuousEffect> i = effects.iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.EndOfTurn)
				i.remove();
		}
	}

	public void removeInactiveEffects(Game game) {
		for (Iterator<ContinuousEffect> i = effects.iterator(); i.hasNext();) {
			ContinuousEffect entry = i.next();
			if (entry.getDuration() == Duration.WhileOnBattlefield) {
				Permanent permanent = (Permanent)game.getPermanent(entry.getSource().getSourceId());
				if (permanent == null || !permanent.isPhasedIn())
					i.remove();
			}
			if (entry.getDuration() == Duration.OneUse) {
				if (entry instanceof ReplacementEffect) {
					if (((ReplacementEffect)entry).isUsed())
						i.remove();
				}
			}
		}
	}

	private List<ContinuousEffect> getLayeredEffects() {
		List<ContinuousEffect> layerEffects = new ArrayList<ContinuousEffect>();
		for (ContinuousEffect effect: effects) {
			if (!(effect instanceof ReplacementEffect) && !(effect instanceof PreventionEffect)) {
				layerEffects.add(effect);
			}
		}
		Collections.sort(layerEffects, new TimestampSorter());
		return layerEffects;
	}


//	private List<ContinuousEffect> getLayer(Layer layer, SubLayer sublayer) {
//		List<ContinuousEffect> layerEffects = new ArrayList<ContinuousEffect>();
//		for (ContinuousEffect effect: effects) {
//			if (effect.getLayer() == layer && effect.getSubLayer() == sublayer) {
//				layerEffects.add(effect);
//			}
//		}
//		Collections.sort(layerEffects, new TimestampSorter());
//		return layerEffects;
//	}

//	private List<PreventionEffect> GetApplicablePreventionEffects(GameEvent event, Game game) {
//		List<PreventionEffect> effects = new ArrayList<PreventionEffect>();
//		for (IEffect effect: this) {
//			if (effect instanceof PreventionEffect && ((PreventionEffect)effect).Applies(event, game)) {
//				effects.add((PreventionEffect)effect);
//			}
//		}
//		return effects;
//	}

//	public List<PreventionEffect> GetApplicablePreventionEffects(IMageObject source, IPermanent target, Game game) {
//		List<PreventionEffect> effects = new ArrayList<PreventionEffect>();
//		for (IEffect effect: this) {
//			if (effect instanceof PreventionEffect && ((PreventionEffect)effect).Applies(source, target, game)) {
//				effects.add((PreventionEffect)effect);
//			}
//		}
//		return effects;
//	}
//
//	public List<PreventionEffect> GetApplicablePreventionEffects(IMageObject source, IPlayer target, Game game) {
//		List<PreventionEffect> effects = new ArrayList<PreventionEffect>();
//		for (IEffect effect: this) {
//			if (effect instanceof PreventionEffect && ((PreventionEffect)effect).Applies(source, target, game)) {
//				effects.add((PreventionEffect)effect);
//			}
//		}
//		return effects;
//	}

	private List<ReplacementEffect> getApplicableReplacementEffects(GameEvent event, Game game) {
		List<ReplacementEffect> replacementEffects = new ArrayList<ReplacementEffect>();
		for (Effect effect: effects) {
			if (effect instanceof ReplacementEffect && ((ReplacementEffect)effect).applies(event, game)) {
				if (((ReplacementEffect)effect).getDuration() != Duration.OneUse || !((ReplacementEffect)effect).isUsed())
					replacementEffects.add((ReplacementEffect)effect);
			}
		}
		return replacementEffects;
	}

//	private List<SelfReplacementEffect> GetApplicableSelfReplacementEffects(GameEvent event, Game game) {
//		List<SelfReplacementEffect> effects = new ArrayList<SelfReplacementEffect>();
//		for (IEffect effect: this) {
//			if (effect instanceof SelfReplacementEffect && ((SelfReplacementEffect)effect).Applies(event, game)) {
//				effects.add((SelfReplacementEffect)effect);
//			}
//		}
//		return effects;
//	}

	public boolean replaceEvent(GameEvent event, Game game) {
		boolean caught = false;
//		List<SelfReplacementEffect> srEffects = GetApplicableSelfReplacementEffects(event, game);
//
//		if (srEffects.size() > 0) {
//			if (srEffects.size() == 1) {
//				caught = srEffects.get(0).ReplaceEvent(event, game);
//			}
//			else {
//				//TODO: handle multiple
//			}
//		}

		if (!caught) {
			List<ReplacementEffect> rEffects = getApplicableReplacementEffects(event, game);
			if (rEffects.size() > 0) {
				if (rEffects.size() == 1) {
					caught = rEffects.get(0).replaceEvent(event, game);
				}
				else {
					Player player = game.getPlayer(event.getPlayerId());
					caught = rEffects.get(player.chooseEffect(rEffects, game)).replaceEvent(event, game);
				}
			}
		}

//		if (!caught) {
//			List<PreventionEffect> pEffects = GetApplicablePreventionEffects(event, game);
//			if (pEffects.size() > 0) {
//				if (pEffects.size() == 1) {
//					caught = pEffects.get(0).ReplaceEvent(event, game);
//				}
//				else {
//					//TODO: handle multiple
//				}
//			}
//		}

		return caught;
	}

	//20091005 - 613
	public void apply(Game game) {
		removeInactiveEffects(game);
		List<ContinuousEffect> layeredEffects = getLayeredEffects();
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.CopyEffects_1, SubLayer.CharacteristicDefining_7a, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.CopyEffects_1, SubLayer.NA, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.ControlChangingEffects_2, SubLayer.CharacteristicDefining_7a, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.ControlChangingEffects_2, SubLayer.NA, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.TextChangingEffects_3, SubLayer.CharacteristicDefining_7a, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.TextChangingEffects_3, SubLayer.NA, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.TypeChangingEffects_4, SubLayer.CharacteristicDefining_7a, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.TypeChangingEffects_4, SubLayer.NA, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.ColorChangingEffects_5, SubLayer.CharacteristicDefining_7a, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.ColorChangingEffects_5, SubLayer.NA, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.CharacteristicDefining_7a, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.SetPT_7b, game);
		}
		applyCounters(game);
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.Counters_7d, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.PTChangingEffects_7, SubLayer.SwitchPT_e, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.PlayerEffects, SubLayer.NA, game);
		}
		for (ContinuousEffect effect: layeredEffects) {
			effect.apply(Layer.RulesEffects, SubLayer.NA, game);
		}
	}

	protected void applyCounters(Game game) {
		for (Permanent permanent: game.getBattlefield().getActivePermanents(CardType.CREATURE)) {
			for (BoostCounter counter: permanent.getCounters().getBoostCounters()) {
				permanent.addPower(counter.getPower() * counter.getCount());
				permanent.addToughness(counter.getToughness() * counter.getCount());
			}
		}
	}

	public String getText() {
		StringBuilder sbText = new StringBuilder();
		for (ContinuousEffect effect: effects) {
			sbText.append(effect.getText()).append(" ");
		}
		return sbText.toString();
	}

	public void addEffect(ContinuousEffect effect) {
		effects.add(effect);
	}

	public boolean effectExists(UUID abilityId) {
		for (ContinuousEffect effect: effects) {
			if (effect.getSource().getId().equals(abilityId))
				return true;
		}
		return false;
	}

}

class TimestampSorter implements Comparator<ContinuousEffect> {
	@Override
	public int compare(ContinuousEffect one, ContinuousEffect two) {
		return one.getTimestamp().compareTo(two.getTimestamp());
	}
}
