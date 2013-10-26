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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth

 */
public class ShrewdHatchling extends CardImpl<ShrewdHatchling> {
    
    private static final FilterSpell filter = new FilterSpell("blue spell");
    private static final FilterSpell filter2 = new FilterSpell("red spell");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter2.add(new ControllerPredicate(TargetController.YOU));
    }
    
    private String rule = "Whenever you cast a blue spell, remove a -1/-1 counter from Shrewd Hatchling.";
    private String rule2 = "Whenever you cast a red spell, remove a -1/-1 counter from Shrewd Hatchling.";

    public ShrewdHatchling(UUID ownerId) {
        super(ownerId, 112, "Shrewd Hatchling", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{U/R}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Elemental");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Shrewd Hatchling enters the battlefield with four -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(4))));
        
        // {UR}: Target creature can't block Shrewd Hatchling this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShrewdHatchlingEffect(), new ManaCostsImpl("{U/R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // Whenever you cast a blue spell, remove a -1/-1 counter from Shrewd Hatchling.
        this.addAbility(new SpellCastAllTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()), filter, false, rule));
        
        // Whenever you cast a red spell, remove a -1/-1 counter from Shrewd Hatchling.
        this.addAbility(new SpellCastAllTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()), filter2, false, rule2));
        
    }

    public ShrewdHatchling(final ShrewdHatchling card) {
        super(card);
    }

    @Override
    public ShrewdHatchling copy() {
        return new ShrewdHatchling(this);
    }
}

class ShrewdHatchlingEffect extends RestrictionEffect<ShrewdHatchlingEffect> {

    public ShrewdHatchlingEffect() {
        super(Duration.EndOfTurn);
        staticText = "Target creature can't block {this} this turn";
    }

    public ShrewdHatchlingEffect(final ShrewdHatchlingEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        UUID targetId = source.getFirstTarget();
        if (targetId != null && blocker.getId().equals(targetId))
            return false;
        return true;
    }

    @Override
    public ShrewdHatchlingEffect copy() {
        return new ShrewdHatchlingEffect(this);
    }

}
