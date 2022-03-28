
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class PeerPressure extends CardImpl {

    public PeerPressure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Choose a creature type. If you control more creatures of that type than each other player, you gain control of all creatures of that type.
        this.getSpellAbility().addEffect(new PeerPressureEffect());
    }

    private PeerPressure(final PeerPressure card) {
        super(card);
    }

    @Override
    public PeerPressure copy() {
        return new PeerPressure(this);
    }
}

class PeerPressureEffect extends OneShotEffect {

    PeerPressureEffect() {
        super(Outcome.GainControl);
        this.staticText = "Choose a creature type. If you control more creatures of that type than each other player, you gain control of all creatures of that type";
    }

    PeerPressureEffect(final PeerPressureEffect effect) {
        super(effect);
    }

    @Override
    public PeerPressureEffect copy() {
        return new PeerPressureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Choice choice = new ChoiceCreatureType(game.getObject(source));
        if (controller != null && controller.choose(Outcome.GainControl, choice, game)) {
            String chosenType = choice.getChoice();
            game.informPlayers(controller.getLogName() + " has chosen " + chosenType);
            UUID playerWithMost = null;
            int maxControlled = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                FilterPermanent filter = new FilterCreaturePermanent(SubType.byDescription(chosenType), chosenType);
                filter.add(new ControllerIdPredicate(playerId));
                int controlled = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
                if (controlled > maxControlled) {
                    maxControlled = controlled;
                    playerWithMost = playerId;
                } else if (controlled == maxControlled) {
                    playerWithMost = null; // Do nothing in case of tie
                }
            }
            if (playerWithMost != null && playerWithMost.equals(controller.getId())) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(SubType.byDescription(chosenType), chosenType), controller.getId(), source, game)) {
                    ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
