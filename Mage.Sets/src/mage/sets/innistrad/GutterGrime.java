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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author BetaSteward
 */
public class GutterGrime extends CardImpl<GutterGrime> {

    public GutterGrime(UUID ownerId) {
        super(ownerId, 186, "Gutter Grime", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");
        this.expansionSetCode = "ISD";

        this.color.setGreen(true);

        // Whenever a nontoken creature you control dies, put a slime counter on Gutter Grime, then put a green Ooze creature token onto the battlefield with "This creature's power and toughness are each equal to the number of slime counters on Gutter Grime."
        this.addAbility(new GutterGrimeTriggeredAbility());
    }

    public GutterGrime(final GutterGrime card) {
        super(card);
    }

    @Override
    public GutterGrime copy() {
        return new GutterGrime(this);
    }
}

class GutterGrimeTriggeredAbility extends TriggeredAbilityImpl<GutterGrimeTriggeredAbility> {

    public GutterGrimeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.SLIME.createInstance()), false);
        this.addEffect(new GutterGrimeEffect());
    }

    public GutterGrimeTriggeredAbility(GutterGrimeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GutterGrimeTriggeredAbility copy() {
        return new GutterGrimeTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE) {
            UUID targetId = event.getTargetId();
            MageObject card = game.getLastKnownInformation(targetId, Zone.BATTLEFIELD);
            if (card instanceof Permanent && !(card instanceof PermanentToken)) {
                Permanent permanent = (Permanent) card;
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD
                        && permanent.getControllerId().equals(this.controllerId)
                        && (targetId.equals(this.getSourceId())
                            || (permanent.getCardType().contains(CardType.CREATURE)
                                && !(permanent instanceof PermanentToken)))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature you control dies, put a slime counter on Gutter Grime, then put a green Ooze creature token onto the battlefield with \"This creature's power and toughness are each equal to the number of slime counters on Gutter Grime.\"";
    }
}


class GutterGrimeEffect extends OneShotEffect<GutterGrimeEffect> {

    public GutterGrimeEffect() {
        super(Outcome.PutCreatureInPlay);
    }
    
    public GutterGrimeEffect(final GutterGrimeEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        GutterGrimeToken token = new GutterGrimeToken(source.getSourceId());
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        return true;
    }

    @Override
    public GutterGrimeEffect copy() {
        return new GutterGrimeEffect(this);
    }
    
}

class GutterGrimeToken extends Token {

	public GutterGrimeToken(UUID sourceId) {
		super("Ooze", "green Ooze creature token with \"This creature's power and toughness are each equal to the number of slime counters on Gutter Grime.\"");
		cardType.add(CardType.CREATURE);
		subtype.add("Ooze");
		color.setGreen(true);
		power = new MageInt(0);
		toughness = new MageInt(0);
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(new GutterGrimeCounters(sourceId), Duration.WhileOnBattlefield)));
	}
}

class GutterGrimeCounters implements DynamicValue {
    
    private UUID sourceId;
    
    public GutterGrimeCounters(UUID sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Permanent p = game.getPermanent(sourceId);
        if (p != null) {
            return p.getCounters().getCount(CounterType.SLIME);
        }
        return 0;
    }

    @Override
    public GutterGrimeCounters clone() {
        return this;
    }

    @Override
    public String getMessage() {
        return "slime counters on Gutter Grime";
    }

    @Override
    public String toString() {
        return "1";
    }
}
