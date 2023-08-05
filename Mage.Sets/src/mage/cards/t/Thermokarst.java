
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class Thermokarst extends CardImpl {

    public Thermokarst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}{G}");

        // Destroy target land. If that land was a snow land, you gain 1 life.
        this.getSpellAbility().addEffect(new ThermokarstEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private Thermokarst(final Thermokarst card) {
        super(card);
    }

    @Override
    public Thermokarst copy() {
        return new Thermokarst(this);
    }
}

class ThermokarstEffect extends OneShotEffect {

    public ThermokarstEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target land. If that land was a snow land, you gain 1 life.";
    }

    public ThermokarstEffect(final ThermokarstEffect effect) {
        super(effect);
    }

    @Override
    public ThermokarstEffect copy() {
        return new ThermokarstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && controller != null) {
            permanent.destroy(source, game, false);
            if (permanent.isSnow(game)) {
                controller.gainLife(1, game, source);
            }
            return true;
        }
        return false;
    }
}
