
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
 * @author LevelX2
 */

public class PreventDamageByChosenSourceEffect extends PreventionEffectImpl {

    private TargetSource target;
    private MageObjectReference mageObjectReference;

    public PreventDamageByChosenSourceEffect() {
        this(new FilterObject("a source"));
    }

    public PreventDamageByChosenSourceEffect(FilterObject filterObject) {
        this(filterObject, false);
    }

    public PreventDamageByChosenSourceEffect(FilterObject filterObject, boolean onlyCombat) {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, onlyCombat);
        if (!filterObject.getMessage().endsWith("source")) {
            filterObject.setMessage(filterObject.getMessage() + " source");
        }
        this.target = new TargetSource(filterObject);
        staticText = "Prevent all" + (onlyCombat ? " combat" : "")
                + " damage " + filterObject.getMessage() + " of your choice would deal this turn";
    }

    protected PreventDamageByChosenSourceEffect(final PreventDamageByChosenSourceEffect effect) {
        super(effect);
        this.target = effect.target.copy();
        this.mageObjectReference = effect.mageObjectReference;
    }

    @Override
    public PreventDamageByChosenSourceEffect copy() {
        return new PreventDamageByChosenSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
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
