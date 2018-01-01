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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * 
 * @author L_J
 */
public class Merseine extends CardImpl {

    public Merseine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Merseine enters the battlefield with three net counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.NET.createInstance(3));
        effect.setText("with three net counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));

        // Enchanted creature doesn't untap during its controller's untap step if Merseine has a net counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousRuleModifyingEffect(new DontUntapInControllersUntapStepEnchantedEffect(), 
            new SourceHasCounterCondition(CounterType.NET)).setText("Enchanted creature doesn't untap during its controller's untap step if Merseine has a net counter on it")));
        
        // Pay enchanted creature's mana cost: Remove a net counter from Merseine. Any player may activate this ability, but only if he or she controls the enchanted creature.
        SimpleActivatedAbility ability = new MerseineActivatedAbility();
        ability.setMayActivate(TargetController.ANY);
        this.addAbility(ability);
    }

    public Merseine(final Merseine card) {
        super(card);
    }

    @Override
    public Merseine copy() {
        return new Merseine(this);
    }
}

class MerseineActivatedAbility extends SimpleActivatedAbility {
    
    public MerseineActivatedAbility() {
        super(Zone.BATTLEFIELD, new RemoveCounterSourceEffect(CounterType.NET.createInstance()), new MerseineCost());
    }

    private MerseineActivatedAbility(final MerseineActivatedAbility ability) {
        super(ability);
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        return super.getEffects(game, effectType);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        Permanent sourcePermanent = game.getBattlefield().getPermanent(this.getSourceId());
        if (sourcePermanent != null) {
            Permanent attachedTo = game.getPermanent(sourcePermanent.getAttachedTo());
            if (attachedTo != null) {
                return super.canActivate(attachedTo.getControllerId(), game);
            }
        }
        return false;
    }

    @Override
    public MerseineActivatedAbility copy() {
        return new MerseineActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return "Pay enchanted creature's mana cost: Remove a net counter from Merseine. Any player may activate this ability, but only if he or she controls the enchanted creature.";
    }
}

class MerseineCost extends CostImpl {

    public MerseineCost() {
        this.text = "Pay enchanted creature's mana cost";
    }

    public MerseineCost(final MerseineCost cost) {
        super(cost);
    }

    @Override
    public MerseineCost copy() {
        return new MerseineCost(this);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent sourcePermanent = game.getBattlefield().getPermanent(sourceId);
        if (sourcePermanent != null) {
            Permanent attachedTo = game.getPermanent(sourcePermanent.getAttachedTo());
            if (attachedTo != null) {
                return attachedTo.getManaCost().canPay(ability, sourceId, controllerId, game);
            }
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent sourcePermanent = game.getBattlefield().getPermanent(sourceId);
        if (sourcePermanent != null) {
            Permanent attachedTo = game.getPermanent(sourcePermanent.getAttachedTo());
            if (attachedTo != null) {
                paid = attachedTo.getManaCost().pay(ability, game, sourceId, controllerId, noMana);
            }
        }
        return paid;
    }
}
