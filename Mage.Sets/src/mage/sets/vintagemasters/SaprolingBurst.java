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
package mage.sets.vintagemasters;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class SaprolingBurst extends CardImpl {

    public SaprolingBurst(UUID ownerId) {
        super(ownerId, 230, "Saproling Burst", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");
        this.expansionSetCode = "VMA";

        // Fading 7
        this.addAbility(new FadingAbility(7, this));
        
        // Remove a fade counter from Saproling Burst: Put a green Saproling creature token onto the battlefield. It has "This creature's power and toughness are each equal to the number of fade counters on Saproling Burst."
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SaprolingBurstCreateTokenEffect(), new RemoveCountersSourceCost(CounterType.FADE.createInstance())));
        
        // When Saproling Burst leaves the battlefield, destroy all tokens put onto the battlefield with Saproling Burst. They can't be regenerated.
        this.addAbility(new SaprolingBurstLeavesBattlefieldTriggeredAbility());
    }

    public SaprolingBurst(final SaprolingBurst card) {
        super(card);
    }

    @Override
    public SaprolingBurst copy() {
        return new SaprolingBurst(this);
    }
}

class SaprolingBurstCreateTokenEffect extends OneShotEffect {
    
    SaprolingBurstCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a green Saproling creature token onto the battlefield. It has \"This creature's power and toughness are each equal to the number of fade counters on {this}.\"";
    }
    
    SaprolingBurstCreateTokenEffect(final SaprolingBurstCreateTokenEffect effect) {
        super(effect);
    }
    
    @Override
    public SaprolingBurstCreateTokenEffect copy() {
        return new SaprolingBurstCreateTokenEffect(this);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean apply(Game game, Ability source) {
        Token token = new SaprolingBurstToken(new MageObjectReference(source.getSourceObject(game), game));
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Object object = game.getState().getValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game));
            Set<UUID> tokensCreated;
            if (object != null) {
                tokensCreated = (Set<UUID>) object;
            }
            else {
                tokensCreated = new HashSet<>();
            }
            for (UUID tokenId : token.getLastAddedTokenIds()) {
                tokensCreated.add(tokenId);
            }
            game.getState().setValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game), tokensCreated);
        }
        return true;
    }
}

class SaprolingBurstToken extends Token {
    
    SaprolingBurstToken(MageObjectReference saprolingBurstMOR) {
        super("Saproling", "green Saproling creature token with \"This creature's power and toughness are each equal to the number of fade counters on Saproling Burst.\"");
        this.color.setGreen(true);
        this.subtype.add("Saproling");
        this.cardType.add(CardType.CREATURE);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(new SaprolingBurstTokenDynamicValue(saprolingBurstMOR), Duration.WhileOnBattlefield)));
    }
}

class SaprolingBurstTokenDynamicValue implements DynamicValue {

    private final MageObjectReference saprolingBurstMOR;

    SaprolingBurstTokenDynamicValue(MageObjectReference saprolingBurstMOR) {
        this.saprolingBurstMOR = saprolingBurstMOR;
    }

    SaprolingBurstTokenDynamicValue(final SaprolingBurstTokenDynamicValue dynamicValue) {
        this.saprolingBurstMOR = dynamicValue.saprolingBurstMOR;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = this.saprolingBurstMOR.getPermanent(game);
        if (permanent != null) {
            return permanent.getCounters(game).getCount(CounterType.FADE);
        }
        return 0;
    }

    @Override
    public SaprolingBurstTokenDynamicValue copy() {
        return new SaprolingBurstTokenDynamicValue(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of fade counters on Saproling Burst";
    }
}

class SaprolingBurstLeavesBattlefieldTriggeredAbility extends ZoneChangeTriggeredAbility {

    SaprolingBurstLeavesBattlefieldTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, new SaprolingBurstDestroyEffect(), "When {this} leaves the battlefield, ", false);
    }

    SaprolingBurstLeavesBattlefieldTriggeredAbility(SaprolingBurstLeavesBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            for (Effect effect : this.getEffects()) {
                if (effect instanceof SaprolingBurstDestroyEffect) {
                    ((SaprolingBurstDestroyEffect) effect).setCardZoneString(CardUtil.getCardZoneString("_tokensCreated", this.getSourceId(), game, true));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SaprolingBurstLeavesBattlefieldTriggeredAbility copy() {
        return new SaprolingBurstLeavesBattlefieldTriggeredAbility(this);
    }
}

class SaprolingBurstDestroyEffect extends OneShotEffect {
    
    private String cardZoneString;
    
    SaprolingBurstDestroyEffect() {
        super(Outcome.Benefit);
        this.staticText = "destroy all tokens put onto the battlefield with {this}. They can't be regenerated";
    }
    
    SaprolingBurstDestroyEffect(final SaprolingBurstDestroyEffect effect) {
        super(effect);
        this.cardZoneString = effect.cardZoneString;
    }
    
    @Override
    public SaprolingBurstDestroyEffect copy() {
        return new SaprolingBurstDestroyEffect(this);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean apply(Game game, Ability source) {
        Object object = game.getState().getValue(cardZoneString);
        if (object != null) {
            Set<UUID> tokensCreated = (Set<UUID>) object;
            for (UUID tokenId : tokensCreated) {
                Permanent token = game.getPermanent(tokenId);
                if (token != null) {
                    token.destroy(source.getSourceId(), game, true);
                }
            }
        }
        return true;
    }
    
    public void setCardZoneString(String cardZoneString) {
        this.cardZoneString = cardZoneString;
    }
}
