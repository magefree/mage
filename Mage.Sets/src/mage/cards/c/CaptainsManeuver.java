
package mage.cards.c;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author sprangg
 */
public final class CaptainsManeuver extends CardImpl {

    public CaptainsManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{W}");

        //The next X damage that would be dealt to target creature, planeswalker, or player this turn is dealt to another target creature, planeswalker, or player instead.
        this.getSpellAbility().addEffect(new CaptainsManeuverEffect());

        FilterCreaturePlayerOrPlaneswalker filter = new FilterCreaturePlayerOrPlaneswalker("creature, planeswalker or player (damage is redirected from)");        
        TargetAnyTarget target = new TargetAnyTarget(filter);
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);
 
        FilterCreaturePlayerOrPlaneswalker filter2 = new FilterCreaturePlayerOrPlaneswalker("another creature, planeswalker or player (damage is redirected to)");     
        filter2.getPlayerFilter().add(new AnotherTargetPredicate(2));
        filter2.getPermanentFilter().add(new AnotherTargetPredicate(2));
        TargetAnyTarget target2 = new TargetAnyTarget(filter2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    private CaptainsManeuver(final CaptainsManeuver card) {
        super(card);
    }

    @Override
    public CaptainsManeuver copy() {
        return new CaptainsManeuver(this);
    }
}

class CaptainsManeuverEffect extends RedirectionEffect {

    protected MageObjectReference redirectToObject;

    public CaptainsManeuverEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next X damage that would be dealt to target creature, planeswalker, or player this turn is dealt to another target creature, planeswalker, or player instead.";
    }

    private CaptainsManeuverEffect(final CaptainsManeuverEffect effect) {
        super(effect);
        this.redirectToObject = effect.redirectToObject;
    }

    @Override
    public CaptainsManeuverEffect copy() {
        return new CaptainsManeuverEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        amountToRedirect = ManacostVariableValue.REGULAR.calculate(game, source, this);
        redirectToObject = new MageObjectReference(source.getTargets().get(1).getFirstTarget(), game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(getTargetPointer().getFirst(game, source))) {
            if (game.getPlayer(redirectToObject.getSourceId()) != null || game.getControllerId(redirectToObject.getSourceId()) != null) {
                if (redirectToObject.equals(new MageObjectReference(source.getTargets().get(1).getFirstTarget(), game))) {
                    redirectTarget = source.getTargets().get(1);
                    return true;
                }
            }
        }
        return false;
    }
}
