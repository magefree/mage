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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class OranRiefHydra extends CardImpl {

    public OranRiefHydra(UUID ownerId) {
        super(ownerId, 181, "Oran-Rief Hydra", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Hydra");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // <i>Landfall</i> - Whenever a land enters the battlefield under your control, put a +1/+1 counter on Oran-Rief Hydra. 
        // If that land is a Forest, put two +1/+1 counters on Oran-Rief Hydra instead.
        this.addAbility(new OranRiefHydraTriggeredAbility());
    }

    public OranRiefHydra(final OranRiefHydra card) {
        super(card);
    }

    @Override
    public OranRiefHydra copy() {
        return new OranRiefHydra(this);
    }
}

class OranRiefHydraTriggeredAbility extends TriggeredAbilityImpl {
    
    private static final String text = "<i>Landfall</i> - Whenever a land enters the battlefield under your control, put a +1/+1 counter on Oran-Rief Hydra. "
            + "If that land is a Forest, put two +1/+1 counters on Oran-Rief Hydra instead.";

    public OranRiefHydraTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OranRiefHydraEffect());
    }

    public OranRiefHydraTriggeredAbility(final OranRiefHydraTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OranRiefHydraTriggeredAbility copy() {
        return new OranRiefHydraTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.getCardType().contains(CardType.LAND)
                && permanent.getControllerId().equals(getControllerId())) {
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (sourcePermanent != null) 
                for (Effect effect : getEffects()) {
                if (effect instanceof OranRiefHydraEffect) {
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return text;
    }
}

class OranRiefHydraEffect extends OneShotEffect {

    public OranRiefHydraEffect() {
        super(Outcome.BoostCreature);
    }

    public OranRiefHydraEffect(final OranRiefHydraEffect effect) {
        super(effect);
    }

    @Override
    public OranRiefHydraEffect copy() {
        return new OranRiefHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (land != null && sourcePermanent != null) {
            if (land.hasSubtype("Forest")) {
                sourcePermanent.addCounters(CounterType.P1P1.createInstance(2), game);
            } else {
                sourcePermanent.addCounters(CounterType.P1P1.createInstance(), game);
            }
            return true;
        }
        return false;
    }
}
