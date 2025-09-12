
package mage.abilities.effects.common;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.FilterSource;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetSource;

/**
 * @author LevelX2
 */

public class PreventDamageByChosenSourceEffect extends PreventionEffectImpl {

    private Target target;
    private MageObjectReference mageObjectReference;

    public PreventDamageByChosenSourceEffect() {
        this(new FilterSource("a source"));
    }

    public PreventDamageByChosenSourceEffect(FilterSource filterSource) {
        this(filterSource, false);
    }

    public PreventDamageByChosenSourceEffect(FilterSource filterSource, boolean onlyCombat) {
        this(new TargetSource(filterSource), filterSource.getMessage(), onlyCombat);
    }

    public PreventDamageByChosenSourceEffect(FilterPermanent filterPermanent, boolean onlyCombat) {
        this(new TargetPermanent(filterPermanent), filterPermanent.getMessage(), onlyCombat);
    }

    private PreventDamageByChosenSourceEffect(Target target, String filterMessage, boolean onlyCombat) {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, onlyCombat);
        this.target = target;
        staticText = "Prevent all" + (onlyCombat ? " combat" : "")
                + " damage " + filterMessage + " of your choice would deal this turn";
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
