
package mage.cards.p;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class PerilousPredicament extends CardImpl {

    public PerilousPredicament(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Each opponent sacrifices an artifact and a nonartifact creature.
        getSpellAbility().addEffect(new PerilousPredicamentSacrificeOpponentsEffect());
    }

    private PerilousPredicament(final PerilousPredicament card) {
        super(card);
    }

    @Override
    public PerilousPredicament copy() {
        return new PerilousPredicament(this);
    }
}

class PerilousPredicamentSacrificeOpponentsEffect extends OneShotEffect {

    public PerilousPredicamentSacrificeOpponentsEffect() {
        super(Outcome.Sacrifice);
        staticText = "Each opponent sacrifices an artifact creature and a nonartifact creature";
    }

    public PerilousPredicamentSacrificeOpponentsEffect(final PerilousPredicamentSacrificeOpponentsEffect effect) {
        super(effect);
    }

    @Override
    public PerilousPredicamentSacrificeOpponentsEffect copy() {
        return new PerilousPredicamentSacrificeOpponentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                FilterArtifactCreaturePermanent filterArtifact = new FilterArtifactCreaturePermanent("an artifact creature");
                filterArtifact.add(new ControllerIdPredicate(player.getId()));
                FilterCreaturePermanent filterNonArtifact = new FilterCreaturePermanent("a nonartifact creature");
                filterNonArtifact.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
                filterNonArtifact.add(new ControllerIdPredicate(player.getId()));
                if (game.getBattlefield().countAll(filterArtifact, player.getId(), game) > 0) {
                    TargetPermanent target = new TargetPermanent(1, 1, filterArtifact, true);
                    if (target.canChoose(player.getId(), source, game)) {
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }
                }
                if (game.getBattlefield().countAll(filterNonArtifact, player.getId(), game) > 0) {
                    TargetPermanent target = new TargetPermanent(1, 1, filterNonArtifact, true);
                    if (target.canChoose(player.getId(), source, game)) {
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }

                }
            }
        }
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}
