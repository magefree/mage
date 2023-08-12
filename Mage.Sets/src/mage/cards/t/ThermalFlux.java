
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801 & L_J
 */
public final class ThermalFlux extends CardImpl {

    private static final FilterPermanent filterNonsnow = new FilterPermanent("nonsnow permanent");
    private static final FilterPermanent filterSnow = new FilterPermanent("snow permanent");

    static {
        filterNonsnow.add(Predicates.not(SuperType.SNOW.getPredicate()));
        filterSnow.add(SuperType.SNOW.getPredicate());
    }

    public ThermalFlux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Choose one - 
        // Target nonsnow permanent becomes snow until end of turn.
        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addTarget(new TargetPermanent(filterNonsnow));
        this.getSpellAbility().addEffect(new ThermalFluxEffect(true));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false));
        // Target snow permanent isn't snow until end of turn.
        // Draw a card at the beginning of the next turn's upkeep.
        Mode mode = new Mode(new ThermalFluxEffect(false));
        mode.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false));
        mode.addTarget(new TargetPermanent(filterSnow));
        this.getSpellAbility().addMode(mode);

    }

    private ThermalFlux(final ThermalFlux card) {
        super(card);
    }

    @Override
    public ThermalFlux copy() {
        return new ThermalFlux(this);
    }
}

class ThermalFluxEffect extends ContinuousEffectImpl {

    private final boolean addSnow;

    public ThermalFluxEffect(boolean addSnow) {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.addSnow = addSnow;
        this.staticText = "Target " + (addSnow ? "non" : "") + "snow permanent " + (addSnow ? "becomes" : "isn't") + " snow until end of turn";
    }

    public ThermalFluxEffect(final ThermalFluxEffect effect) {
        super(effect);
        this.addSnow = effect.addSnow;
    }

    @Override
    public ThermalFluxEffect copy() {
        return new ThermalFluxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            if (addSnow) {
                permanent.addSuperType(game, SuperType.SNOW);
            } else {
                permanent.removeSuperType(game, SuperType.SNOW);
            }
        }
        return true;
    }
}
