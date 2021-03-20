
package mage.cards.i;

import java.io.ObjectStreamException;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public final class ImpactResonance extends CardImpl {

    public ImpactResonance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Impact Resonance deals X damage divided as you choose among any number of target creatures, where X is the greatest amount of damage dealt by a source to a permanent or player this turn.
        DynamicValue xValue = GreatestAmountOfDamageDealtValue.instance;
        Effect effect = new DamageMultiEffect(xValue);
        effect.setText("{this} deals X damage divided as you choose among any number of target creatures, where X is the greatest amount of damage dealt by a source to a permanent or player this turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(xValue));
        this.getSpellAbility().addWatcher(new GreatestAmountOfDamageWatcher());
    }

    private ImpactResonance(final ImpactResonance card) {
        super(card);
    }

    @Override
    public ImpactResonance copy() {
        return new ImpactResonance(this);
    }
}


enum GreatestAmountOfDamageDealtValue implements DynamicValue, MageSingleton {

    instance;

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }


    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return this.calculate(game, sourceAbility.getControllerId());
    }

    public int calculate(Game game, UUID controllerId) {
        GreatestAmountOfDamageWatcher watcher = game.getState().getWatcher(GreatestAmountOfDamageWatcher.class);
        if (watcher != null) {
            return watcher.getGreatestAmountOfDamage();
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return this;
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
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch(event.getType()) {
            case DAMAGED_PERMANENT:
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
