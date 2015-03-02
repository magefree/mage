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
package mage.sets.commander2014;

import java.io.ObjectStreamException;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class ImpactResonance extends CardImpl {

    public ImpactResonance(UUID ownerId) {
        super(ownerId, 36, "Impact Resonance", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "C14";

        this.color.setRed(true);

        // Impact Resonance deals X damage divided as you choose among any number of target creatures, where X is the greatest amount of damage dealt by a source to a permanent or player this turn.
        DynamicValue xValue = new GreatestAmountOfDamageDealtValue();
        Effect effect = new DamageMultiEffect(xValue);
        effect.setText("{this} deals X damage divided as you choose among any number of target creatures, where X is the greatest amount of damage dealt by a source to a permanent or player this turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(xValue));
        this.getSpellAbility().addWatcher(new GreatestAmountOfDamageWatcher());
    }

    public ImpactResonance(final ImpactResonance card) {
        super(card);
    }

    @Override
    public ImpactResonance copy() {
        return new ImpactResonance(this);
    }
}


class GreatestAmountOfDamageDealtValue implements DynamicValue, MageSingleton {

    private static final GreatestAmountOfDamageDealtValue fINSTANCE =  new GreatestAmountOfDamageDealtValue();

    private Object readResolve() throws ObjectStreamException {
        return fINSTANCE;
    }

    public static GreatestAmountOfDamageDealtValue getInstance() {
        return fINSTANCE;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return this.calculate(game, sourceAbility.getControllerId());
    }

    public int calculate(Game game, UUID controllerId) {
        GreatestAmountOfDamageWatcher watcher = (GreatestAmountOfDamageWatcher) game.getState().getWatchers().get("GreatestAmountOfDamage");
        if (watcher != null) {
            return watcher.getGreatestAmountOfDamage();
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new GreatestAmountOfDamageDealtValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the greatest amount of damage dealt by a source to a permanent or player this turn";
    }
}

class GreatestAmountOfDamageWatcher extends Watcher {

    private int damageAmount;

    public GreatestAmountOfDamageWatcher() {
        super("GreatestAmountOfDamage", WatcherScope.GAME);
    }

    public GreatestAmountOfDamageWatcher(final GreatestAmountOfDamageWatcher watcher) {
        super(watcher);
        this.damageAmount = watcher.damageAmount;
    }

    @Override
    public GreatestAmountOfDamageWatcher copy() {
        return new GreatestAmountOfDamageWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch(event.getType()) {
            case DAMAGED_CREATURE:
            case DAMAGED_PLANESWALKER:
            case DAMAGED_PLAYER:
                if (event.getAmount() > damageAmount) {
                    damageAmount = event.getAmount();
                }
        }
    }

    /**
     *
     * @return Returns the greatest amount of damage dealt to a player or permanent during the current turn.
     */
    public int getGreatestAmountOfDamage() {
        return damageAmount;
    }

    @Override
    public void reset() {
        super.reset();
        damageAmount = 0;
    }
}
