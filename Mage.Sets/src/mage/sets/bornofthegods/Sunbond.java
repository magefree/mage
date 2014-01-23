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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Sunbond extends CardImpl<Sunbond> {

    public Sunbond(UUID ownerId) {
        super(ownerId, 28, "Sunbond", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Aura");

        this.color.setWhite(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);        
        // Enchanted creature has "Whenever you gain life, put that many +1/+1 counters on this creature."
        Effect effect = new GainAbilityAttachedEffect(new GainLifeControllerTriggeredAbility(new SunbondEffect(), false, true), AttachmentType.AURA, Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
    }

    public Sunbond(final Sunbond card) {
        super(card);
    }

    @Override
    public Sunbond copy() {
        return new Sunbond(this);
    }
}

class SunbondEffect extends OneShotEffect<SunbondEffect> {
    
    public SunbondEffect() {
        super(Outcome.Benefit);
        this.staticText = "put that many +1/+1 counters on this creature";
    }
    
    public SunbondEffect(final SunbondEffect effect) {
        super(effect);
    }
    
    @Override
    public SunbondEffect copy() {
        return new SunbondEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        int lifeGained = (Integer) this.getValue("gainedLife");
        if (lifeGained > 0) {
            return new AddCountersSourceEffect(CounterType.P1P1.createInstance(lifeGained)).apply(game, source);
        }
        return false;
    }
}
