package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.FinishVotingTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrudgeKeeper extends CardImpl {

    public GrudgeKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever players finish voting, each opponent who voted for a choice you didn't vote for loses 2 life.
        this.addAbility(new FinishVotingTriggeredAbility(new GrudgeKeeperEffect()));
    }

    private GrudgeKeeper(final GrudgeKeeper card) {
        super(card);
    }

    @Override
    public GrudgeKeeper copy() {
        return new GrudgeKeeper(this);
    }
}

class GrudgeKeeperEffect extends OneShotEffect {

    GrudgeKeeperEffect() {
        super(Outcome.Benefit);
    }

    private GrudgeKeeperEffect(final GrudgeKeeperEffect effect) {
        super(effect);
    }

    @Override
    public GrudgeKeeperEffect copy() {
        return new GrudgeKeeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> playerIds = (Set<UUID>) getValue("votedAgainst");
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (!playerIds.contains(opponentId)) {
                continue;
            }
            Player player = game.getPlayer(opponentId);
            if (player == null) {
                continue;
            }
            player.loseLife(2, game, source, false);
        }
        return true;
    }
}
