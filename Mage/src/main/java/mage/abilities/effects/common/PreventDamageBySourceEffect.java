
package mage.abilities.effects.common;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;

/**
 *
 * @author LevelX2
 */

public class PreventDamageBySourceEffect extends PreventionEffectImpl {

    private TargetSource target;
    private MageObjectReference mageObjectReference;

    public PreventDamageBySourceEffect() {
        this(new FilterObject("a"));
    }

    public PreventDamageBySourceEffect(FilterObject filterObject) {
        super(Duration.EndOfTurn);
        if (!filterObject.getMessage().endsWith("source")) {
            filterObject.setMessage(filterObject.getMessage() + " source");
        }
        this.target = new TargetSource(filterObject);
        staticText = "Prevent all damage " + filterObject.getMessage() + " of your choice would deal this turn";
    }

    public PreventDamageBySourceEffect(final PreventDamageBySourceEffect effect) {
        super(effect);
        this.target = effect.target.copy();
        this.mageObjectReference = effect.mageObjectReference;
    }

    @Override
    public PreventDamageBySourceEffect copy() {
        return new PreventDamageBySourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        mageObjectReference = new MageObjectReference(target.getFirstTarget(), game);        
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            MageObject mageObject = game.getObject(event.getSourceId());
            if (mageObject != null && mageObjectReference.refersTo(mageObject, game)) {
                return true;
            }            
        }
        return false;
    }

}
