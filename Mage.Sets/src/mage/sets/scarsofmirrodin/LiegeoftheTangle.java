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
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HaveCounter;
import mage.abilities.condition.common.Metalcraft;
import mage.abilities.condition.common.MyTurn;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BecomesCreatureSourceEOTEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki
 */
public class LiegeoftheTangle extends CardImpl<LiegeoftheTangle> {

    public LiegeoftheTangle (UUID ownerId) {
        super(ownerId, 123, "Liege of the Tangle", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Elemental");
		this.color.setGreen(true);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
		this.addAbility(TrampleAbility.getInstance());
		this.addAbility(new LiegeoftheTangleTriggeredAbility());
    }

    public LiegeoftheTangle (final LiegeoftheTangle card) {
        super(card);
    }

    @Override
    public LiegeoftheTangle copy() {
        return new LiegeoftheTangle(this);
    }
}

class LiegeoftheTangleTriggeredAbility extends TriggeredAbilityImpl<LiegeoftheTangleTriggeredAbility> {
	LiegeoftheTangleTriggeredAbility() {
		super(Constants.Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.AWAKENING.createInstance()));
		this.addEffect(new LiegeoftheTangleEffect());
//		Ability ability  = new SimpleStaticAbility(Constants.Zone.BATTLEFIELD,
//                        	new ConditionalContinousEffect(
//                                new BecomesCreatureSourceEOTEffect(new AwakeningLandToken(), "land"),
//                                new HaveCounter(CounterType.AWAKENING),
//                                "This land is an 8/8 green Elemental creature for as long as it has an awakening counter on it. It's still a land"));
//        this.addEffect(new GainAbilityTargetEffect(ability, Constants.Duration.EndOfGame));
		Target target = new TargetLandPermanent(0, Integer.MAX_VALUE, new FilterLandPermanent(), true);
		this.addTarget(target);
	}

	public LiegeoftheTangleTriggeredAbility(final LiegeoftheTangleTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public LiegeoftheTangleTriggeredAbility copy() {
		return new LiegeoftheTangleTriggeredAbility(this);
	}

	@Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getId().equals(this.getSourceId())) {
			    return true;
            }
        }
        return false;
    }

	@Override
	public String getRule() {
		return "Whenever Liege of the Tangle deals combat damage to a player, you may choose any number of target lands you control and put an awakening counter on each of them. Each of those lands is an 8/8 green Elemental creature for as long as it has an awakening counter on it. They're still lands.";
	}
}

class LiegeoftheTangleEffect extends ContinuousEffectImpl<LiegeoftheTangleEffect> {

	private static AwakeningLandToken token = new AwakeningLandToken();

	public LiegeoftheTangleEffect() {
		super(Duration.EndOfGame, Outcome.BecomeCreature);
	}

	public LiegeoftheTangleEffect(final LiegeoftheTangleEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
		for (UUID permId: objects) {
			Permanent perm = game.getPermanent(permId);
			if (perm != null) {
				if (perm.getCounters().getCount(CounterType.AWAKENING) > 0) {
					switch (layer) {
						case TypeChangingEffects_4:
							if (sublayer == SubLayer.NA) {
								perm.getCardType().addAll(token.getCardType());
								perm.getSubtype().addAll(token.getSubtype());
							}
							break;
						case ColorChangingEffects_5:
							if (sublayer == SubLayer.NA) {
								perm.getColor().setColor(token.getColor());
							}
							break;
						case PTChangingEffects_7:
							if (sublayer == SubLayer.SetPT_7b) {
								perm.getPower().setValue(token.getPower().getValue());
								perm.getToughness().setValue(token.getToughness().getValue());
							}
							break;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return false;
	}

	@Override
	public void init(Ability source, Game game) {
		super.init(source, game);
		if (this.affectedObjectsSet) {
			for (UUID permId: source.getTargets().get(0).getTargets()) {
				objects.add(permId);
			}
		}
	}

	@Override
	public LiegeoftheTangleEffect copy() {
		return new LiegeoftheTangleEffect(this);
	}

	@Override
	public boolean hasLayer(Layer layer) {
		return layer == Layer.PTChangingEffects_7 || layer == Layer.ColorChangingEffects_5 || layer == layer.TypeChangingEffects_4;
	}

}

class AwakeningLandToken extends Token {

    public AwakeningLandToken() {
        super("", "an 8/8 green Elemental creature");
        cardType.add(Constants.CardType.CREATURE);
		color.setGreen(true);
		subtype.add("Elemental");
		power = new MageInt(8);
		toughness = new MageInt(8);
    }
}


