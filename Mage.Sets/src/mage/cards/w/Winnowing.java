package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SharesCreatureTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Winnowing extends CardImpl {

    public Winnowing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // For each player, you choose a creature that player controls. Then each player sacrifices all other creatures they control that don't share a creature type with the chosen creature they control.
        this.getSpellAbility().addEffect(new WinnowingEffect());
    }

    private Winnowing(final Winnowing card) {
        super(card);
    }

    @Override
    public Winnowing copy() {
        return new Winnowing(this);
    }
}

class WinnowingEffect extends OneShotEffect {

    WinnowingEffect() {
        super(Outcome.Benefit);
        staticText = "for each player, you choose a creature that player controls. " +
                "Then each player sacrifices all other creatures they control " +
                "that don't share a creature type with the chosen creature they control";
    }

    private WinnowingEffect(final WinnowingEffect effect) {
        super(effect);
    }

    @Override
    public WinnowingEffect copy() {
        return new WinnowingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Permanent> permanents = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterPermanent filter = new FilterCreaturePermanent("creature controlled by " + player.getName());
            filter.add(new ControllerIdPredicate(playerId));
            if (!game.getBattlefield().contains(filter, source, game, 1)) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(filter);
            target.withNotTarget(true);
            controller.choose(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                continue;
            }
            game.informPlayers(controller.getLogName() + " chooses " + permanent.getLogName() + " controlled by " + player.getLogName());
            filter.add(Predicates.not(new PermanentIdPredicate(permanent.getId())));
            filter.add(Predicates.not(new SharesCreatureTypePredicate(permanent)));
            permanents.addAll(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game));
        }
        for (Permanent permanent : permanents) {
            permanent.sacrifice(source, game);
        }
        return true;
    }
}
