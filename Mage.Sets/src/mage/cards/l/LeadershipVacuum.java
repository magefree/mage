package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.CommanderPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class LeadershipVacuum extends CardImpl {

    public LeadershipVacuum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Target player returns each commander they control from the battlefield to the command zone.
        this.getSpellAbility().addEffect(new LeadershipVacuumEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private LeadershipVacuum(final LeadershipVacuum card) {
        super(card);
    }

    @Override
    public LeadershipVacuum copy() {
        return new LeadershipVacuum(this);
    }
}

class LeadershipVacuumEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(CommanderPredicate.instance);
    }

    LeadershipVacuumEffect() {
        super(Outcome.Benefit);
        staticText = "Target player returns each commander they control from the battlefield to the command zone.";
    }

    private LeadershipVacuumEffect(final LeadershipVacuumEffect effect) {
        super(effect);
    }

    @Override
    public LeadershipVacuumEffect copy() {
        return new LeadershipVacuumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.moveCards(game.getBattlefield()
                .getAllActivePermanents(filter, source.getFirstTarget(), game)
                .stream()
                .collect(Collectors.toSet()), Zone.COMMAND, source, game);
    }
}