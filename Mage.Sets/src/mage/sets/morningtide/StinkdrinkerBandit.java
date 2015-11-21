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
package mage.sets.morningtide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.effects.Effect;
import mage.abilities.common.SimpleTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.game.events.GameEvent;

/**
 *
 * @author BursegSardaukar
 */
public class StinkdrinkerBandit extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Attacking and unblocked Rogues");
    
    static {  
        filter.add(new SubtypePredicate("Rogue"));
        filter.add(new AttackingPredicate());
        filter.add(Predicates.not(new BlockedPredicate()));
    }
    
    public StinkdrinkerBandit(UUID ownerId) {
        super(ownerId, 80, "Stinkdrinker Bandit", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "MOR";
        this.subtype.add("Goblin");
        this.subtype.add("Rogue");
        
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        
        // Prowl {1}, {B} (You may cast this for its prowl cost if you dealt combat damage to a player this turn with a Goblin or Rogue.)
        this.addAbility(new ProwlAbility(this, "{1}{B}"));
        
        // Whenever a Rogue you control attacks and isn't blocked, it gets +2/+1 until end of turn.
        Effect effect = new BoostControlledEffect(2,1,Duration.EndOfTurn, filter);
        Ability ability = new SimpleTriggeredAbility(Zone.BATTLEFIELD, GameEvent.EventType.DECLARED_BLOCKERS, effect, "Whenever a Rogue you control attacks and isn't blocked,");
        this.addAbility(ability);
    }

    public StinkdrinkerBandit(final StinkdrinkerBandit card) {
        super(card);
    }

    @Override
    public StinkdrinkerBandit copy() {
        return new StinkdrinkerBandit(this);
    }
}