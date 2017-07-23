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
package mage.cards.b;

import java.util.UUID;
import mage.Mana;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.mana.DelayedTriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class BubblingMuck extends CardImpl {

    public BubblingMuck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Until end of turn, whenever a player taps a Swamp for mana, that player adds {B} to his or her mana pool.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new BubblingMuckTriggeredAbility()));
    }

    public BubblingMuck(final BubblingMuck card) {
        super(card);
    }

    @Override
    public BubblingMuck copy() {
        return new BubblingMuck(this);
    }
}

class BubblingMuckTriggeredAbility extends DelayedTriggeredManaAbility {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Swamp");

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public BubblingMuckTriggeredAbility() {
        super(new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.B), "his or her"), Duration.EndOfTurn, false);
        this.usesStack = false;
    }

    public BubblingMuckTriggeredAbility(BubblingMuckTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        if (land != null && filter.match(land, game)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(land.getControllerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public BubblingMuckTriggeredAbility copy() {
        return new BubblingMuckTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a player taps a Swamp for mana, that player adds {B} to his or her mana pool";
    }
}
