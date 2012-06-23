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

package mage.sets.magic2011;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddPlusOneCountersAttachedEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PrimalCocoon extends CardImpl<PrimalCocoon> {

    public PrimalCocoon(UUID ownerId) {
        super(ownerId, 191, "Primal Cocoon", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "M11";
        this.color.setGreen(true);
        this.subtype.add("Aura");

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        this.addAbility(new PrimalCocoonAbility1());
        this.addAbility(new PrimalCocoonAbility2());

    }

    public PrimalCocoon(final PrimalCocoon card) {
        super(card);
    }

    @Override
    public PrimalCocoon copy() {
        return new PrimalCocoon(this);
    }
}

class PrimalCocoonAbility1 extends TriggeredAbilityImpl<PrimalCocoonAbility1> {

    public PrimalCocoonAbility1() {
        super(Zone.BATTLEFIELD, new AddPlusOneCountersAttachedEffect(1));
    }

    public PrimalCocoonAbility1(final PrimalCocoonAbility1 ability) {
        super(ability);
    }

    @Override
    public PrimalCocoonAbility1 copy() {
        return new PrimalCocoonAbility1(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, put a +1/+1 counter on enchanted creature.";
    }
}

class PrimalCocoonAbility2 extends TriggeredAbilityImpl<PrimalCocoonAbility2> {

    public PrimalCocoonAbility2() {
        super(Zone.BATTLEFIELD, new DestroySourceEffect());
    }

    public PrimalCocoonAbility2(final PrimalCocoonAbility2 ability) {
        super(ability);
    }

    @Override
    public PrimalCocoonAbility2 copy() {
        return new PrimalCocoonAbility2(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ATTACKER_DECLARED || event.getType() == EventType.BLOCKER_DECLARED) {
            Permanent enchantment = game.getPermanent(sourceId);
            if (enchantment != null && enchantment.getAttachedTo() != null && event.getSourceId() != null && event.getSourceId().equals(enchantment.getAttachedTo())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted creature attacks or blocks, sacrifice {this}.";
    }

}
