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

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki
 */
public class LiegeOfTheTangle extends CardImpl {

    public LiegeOfTheTangle (UUID ownerId) {
        super(ownerId, 123, "Liege of the Tangle", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Elemental");

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new LiegeOfTheTangleTriggeredAbility());
    }

    public LiegeOfTheTangle (final LiegeOfTheTangle card) {
        super(card);
    }

    @Override
    public LiegeOfTheTangle copy() {
        return new LiegeOfTheTangle(this);
    }
}

class LiegeOfTheTangleTriggeredAbility extends TriggeredAbilityImpl {
    LiegeOfTheTangleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.AWAKENING.createInstance()));
        this.addEffect(new LiegeOfTheTangleEffect());
        Target target = new TargetLandPermanent(0, Integer.MAX_VALUE, new FilterLandPermanent(), true);
        this.addTarget(target);
    }

    public LiegeOfTheTangleTriggeredAbility(final LiegeOfTheTangleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LiegeOfTheTangleTriggeredAbility copy() {
        return new LiegeOfTheTangleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
        Permanent p = game.getPermanent(event.getSourceId());
        return damageEvent.isCombatDamage() && p != null && p.getId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may choose any number of target lands you control and put an awakening counter on each of them. Each of those lands is an 8/8 green Elemental creature for as long as it has an awakening counter on it. They're still lands.";
    }
}

class LiegeOfTheTangleEffect extends ContinuousEffectImpl {

    private static AwakeningLandToken token = new AwakeningLandToken();

    public LiegeOfTheTangleEffect() {
        super(Duration.EndOfGame, Outcome.BecomeCreature);
    }

    public LiegeOfTheTangleEffect(final LiegeOfTheTangleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { 
            Permanent perm = it.next().getPermanent(game);
            if (perm != null) {
                if (perm.getCounters(game).getCount(CounterType.AWAKENING) > 0) {
                    switch (layer) {
                        case TypeChangingEffects_4:
                            if (sublayer == SubLayer.NA) {
                                perm.getCardType().addAll(token.getCardType());
                                perm.getSubtype(game).addAll(token.getSubtype(game));
                            }
                            break;
                        case ColorChangingEffects_5:
                            if (sublayer == SubLayer.NA) {
                                perm.getColor(game).setColor(token.getColor(game));
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
            } else {
                it.remove();
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
            for (UUID permId: targetPointer.getTargets(game, source)) {
                affectedObjectList.add(new MageObjectReference(permId, game));
            }
        }
    }

    @Override
    public LiegeOfTheTangleEffect copy() {
        return new LiegeOfTheTangleEffect(this);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.ColorChangingEffects_5 || layer == layer.TypeChangingEffects_4;
    }

}

class AwakeningLandToken extends Token {

    public AwakeningLandToken() {
        super("", "an 8/8 green Elemental creature");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Elemental");
        power = new MageInt(8);
        toughness = new MageInt(8);
    }
}


