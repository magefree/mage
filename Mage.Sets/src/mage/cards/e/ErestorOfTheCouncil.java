package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.FinishVotingTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ErestorOfTheCouncil extends CardImpl {

    public ErestorOfTheCouncil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever players finish voting, each opponent who voted for a choice you voted for creates a Treasure token. You scry X, where X is the number of opponents who voted for a choice you didn't vote for. Draw a card.
        this.addAbility(new FinishVotingTriggeredAbility(new ErestorOfTheCouncilEffect()));
    }

    private ErestorOfTheCouncil(final ErestorOfTheCouncil card) {
        super(card);
    }

    @Override
    public ErestorOfTheCouncil copy() {
        return new ErestorOfTheCouncil(this);
    }
}

class ErestorOfTheCouncilEffect extends OneShotEffect {

    ErestorOfTheCouncilEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent who voted for a choice you voted for creates a Treasure token. You scry X, " +
                "where X is the number of opponents who voted for a choice you didn't vote for. Draw a card";
    }

    private ErestorOfTheCouncilEffect(final ErestorOfTheCouncilEffect effect) {
        super(effect);
    }

    @Override
    public ErestorOfTheCouncilEffect copy() {
        return new ErestorOfTheCouncilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> playerIds = (Set<UUID>) getValue("votedAgainst");
        int count = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (playerIds.contains(opponentId)) {
                count++;
            } else {
                new TreasureToken().putOntoBattlefield(1, game, source, opponentId);
            }
        }
        if (count > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.scry(count, source, game);
            }
        }
        return true;
    }
}
