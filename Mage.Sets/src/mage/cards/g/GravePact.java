package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author North
 */
public final class GravePact extends CardImpl {

    public GravePact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}{B}");


        // Whenever a creature you control dies, each other player sacrifices a creature.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new GravePactEffect(), false, StaticFilters.FILTER_CONTROLLED_A_CREATURE
        ));
    }

    private GravePact(final GravePact card) {
        super(card);
    }

    @Override
    public GravePact copy() {
        return new GravePact(this);
    }
}

class GravePactEffect extends OneShotEffect {

    GravePactEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each other player sacrifices a creature of their choice";
    }

    private GravePactEffect(final GravePactEffect effect) {
        super(effect);
    }

    @Override
    public GravePactEffect copy() {
        return new GravePactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && !playerId.equals(source.getControllerId())) {
                    TargetSacrifice target = new TargetSacrifice(StaticFilters.FILTER_PERMANENT_A_CREATURE);
                    if (target.canChoose(player.getId(), source, game)) {
                        player.choose(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
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
        return false;
    }
}
