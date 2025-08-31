package mage.cards.u;

import java.util.*;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author anonymous
 */
public final class UnexplainedAbsence extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("target nonland permanent");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public UnexplainedAbsence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // For each player, exile up to one target nonland permanent that player controls. For each permanent exiled this way, its controller cloaks the top card of their library.
        this.getSpellAbility().addEffect(
                new UnexplainedAbsenceEffect()
                        .setTargetPointer(new EachTargetPointer())
                        .setText("For each player, exile up to one target nonland permanent that player controls. "
                                + "For each permanent exiled this way, its controller cloaks the top card of their library.")
        );
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter));
        this.getSpellAbility().setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, false));
    }

    private UnexplainedAbsence(final UnexplainedAbsence card) {
        super(card);
    }

    @Override
    public UnexplainedAbsence copy() {
        return new UnexplainedAbsence(this);
    }
}

class UnexplainedAbsenceEffect extends OneShotEffect {

    UnexplainedAbsenceEffect() {
        super(Outcome.Neutral);
    }

    private UnexplainedAbsenceEffect(final UnexplainedAbsenceEffect effect) {
        super(effect);
    }

    @Override
    public UnexplainedAbsenceEffect copy() {
        return new UnexplainedAbsenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Permanent> permanents = source
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        // set of controllers for the second part of the effect.
        Set<UUID> controllers = new HashSet<>();
        for (Permanent permanent : permanents) {
            Player effectedController = game.getPlayer(permanent.getControllerId());

            if (effectedController != null) {
                controllers.add(effectedController.getId());
            }
        }
        controller.moveCards(permanents, Zone.EXILED, source, game);
        for (UUID controllerId : controllers) {
            Player player = game.getPlayer(controllerId);
            ManifestEffect.doManifestCards(
                    game, source, player,
                    player.getLibrary().getTopCards(game, 1), true
            );
        }
        return true;
    }

}
