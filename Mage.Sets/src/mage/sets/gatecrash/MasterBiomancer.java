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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continious.AddCardSubTypeTargetEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
*
* @author LevelX2
*/
public class MasterBiomancer extends CardImpl<MasterBiomancer> {

    public MasterBiomancer(UUID ownerId) {
       super(ownerId, 176, "Master Biomancer", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
       this.expansionSetCode = "GTC";
       this.subtype.add("Elf");
       this.subtype.add("Wizard");

       this.color.setBlue(true);
       this.color.setGreen(true);
       this.power = new MageInt(2);
       this.toughness = new MageInt(4);

       // Each other creature you control enters the battlefield with a number of additional +1/+1 counters on it equal to Master Biomancer's power and as a Mutant in addition to its other types.
       this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new MasterBiomancerEntersBattlefieldEffect()));
    }

    public MasterBiomancer(final MasterBiomancer card) {
       super(card);
    }

    @Override
    public MasterBiomancer copy() {
       return new MasterBiomancer(this);
    }
}

class MasterBiomancerEntersBattlefieldEffect extends ReplacementEffectImpl<MasterBiomancerEntersBattlefieldEffect> {

    public MasterBiomancerEntersBattlefieldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other creature you control enters the battlefield with a number of additional +1/+1 counters on it equal to Master Biomancer's power and as a Mutant in addition to its other types";
    }

    public MasterBiomancerEntersBattlefieldEffect(MasterBiomancerEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent creature = game.getPermanent(event.getTargetId());
            if (creature != null && creature.getControllerId().equals(source.getControllerId()) 
                    && creature.getCardType().contains(CardType.CREATURE)
                    && !event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        Permanent creature = game.getPermanent(event.getTargetId());
        if (sourceCreature != null && creature != null) {
            int power = sourceCreature.getPower().getValue();
            if (power > 0) {
                creature.addCounters(CounterType.P1P1.createInstance(power), game);
            }
            ContinuousEffect effect = new AddCardSubTypeTargetEffect("Mutant", Duration.WhileOnBattlefield);
            effect.setTargetPointer(new FixedTarget(creature.getId()));
            game.addEffect(effect, source);
        }
        return false;
    }


    @Override
    public MasterBiomancerEntersBattlefieldEffect copy() {
        return new MasterBiomancerEntersBattlefieldEffect(this);
    }
}
