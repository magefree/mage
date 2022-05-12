package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DemonFlyingToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class ReignOfThePit extends CardImpl {

    public ReignOfThePit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Each player sacrifices a creature. Create an X/X black Demon creature token with flying, where X is the total power of the creatures sacrificed this way.
        this.getSpellAbility().addEffect(new ReignOfThePitEffect());
    }

    private ReignOfThePit(final ReignOfThePit card) {
        super(card);
    }

    @Override
    public ReignOfThePit copy() {
        return new ReignOfThePit(this);
    }
}

class ReignOfThePitEffect extends OneShotEffect {

    ReignOfThePitEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Each player sacrifices a creature. Create an X/X black Demon creature token with flying, where X is the total power of the creatures sacrificed this way";
    }

    ReignOfThePitEffect(final ReignOfThePitEffect effect) {
        super(effect);
    }

    @Override
    public ReignOfThePitEffect copy() {
        return new ReignOfThePitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int totalPowerSacrificed = 0;
        List<UUID> perms = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent(), true);
                if (target.canChoose(player.getId(), source, game)) {
                    while (!target.isChosen() && player.canRespond()) {
                        player.choose(Outcome.Sacrifice, target, source, game);
                    }
                    perms.addAll(target.getTargets());
                }
            }
        }
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                int power = permanent.getPower().getValue();
                if (permanent.sacrifice(source, game)) {
                    totalPowerSacrificed += power;
                }
            }
        }
        new CreateTokenEffect(new DemonFlyingToken(totalPowerSacrificed)).apply(game, source);
        return true;
    }
}
