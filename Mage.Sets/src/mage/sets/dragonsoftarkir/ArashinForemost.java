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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class ArashinForemost extends CardImpl {

    public ArashinForemost(UUID ownerId) {
        super(ownerId, 3, "Arashin Foremost", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
        
        // Whenever Arashin Foremost enters the battlefield or attacks, another target Warrior creature you control gains double strike until end of turn.
        this.addAbility(new ArashinForemostAbility());
    }

    public ArashinForemost(final ArashinForemost card) {
        super(card);
    }

    @Override
    public ArashinForemost copy() {
        return new ArashinForemost(this);
    }
}

class ArashinForemostAbility extends TriggeredAbilityImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another Warrior creature you control");

    static {
        filter.add(new SubtypePredicate("Warrior"));
        filter.add(new AnotherPredicate());
    }

    public ArashinForemostAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn), false);
        this.addTarget(new TargetControlledCreaturePermanent(filter));
    }

    public ArashinForemostAbility(final ArashinForemostAbility ability) {
        super(ability);
    }

    @Override
    public ArashinForemostAbility copy() {
        return new ArashinForemostAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED || event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId()) ) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} enters the battlefield or attacks, another target Warrior creature you control gains double strike until end of turn";
    }

}
