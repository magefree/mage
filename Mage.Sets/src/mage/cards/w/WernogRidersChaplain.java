package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FriendsForeverAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ClueArtifactToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WernogRidersChaplain extends CardImpl {

    public WernogRidersChaplain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Will the Wise enters or leaves the battlefield, each opponent may investigate. Each opponent who doesn't loses 1 life. You investigate X times, where X is one plus the number of opponents who investigated this way.
        this.addAbility(new EntersBattlefieldOrLeavesSourceTriggeredAbility(new WernogRidersChaplainEffect(), false));

        // Friends forever
        this.addAbility(FriendsForeverAbility.getInstance());
    }

    private WernogRidersChaplain(final WernogRidersChaplain card) {
        super(card);
    }

    @Override
    public WernogRidersChaplain copy() {
        return new WernogRidersChaplain(this);
    }
}

class WernogRidersChaplainEffect extends OneShotEffect {

    WernogRidersChaplainEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent may investigate. Each opponent who doesn't loses 1 life. " +
                "You investigate X times, where X is one plus the number of opponents who investigated this way";
    }

    private WernogRidersChaplainEffect(final WernogRidersChaplainEffect effect) {
        super(effect);
    }

    @Override
    public WernogRidersChaplainEffect copy() {
        return new WernogRidersChaplainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 1;
        Token token = new ClueArtifactToken();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            if (opponent.chooseUse(outcome, "Investigate?", source, game)) {
                count++;
                token.putOntoBattlefield(1, game, source, playerId);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.INVESTIGATED, source.getSourceId(), source, playerId));
            } else {
                opponent.loseLife(1, game, source, false);
            }
        }
        token.putOntoBattlefield(count, game, source, source.getControllerId());
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.INVESTIGATED, source.getSourceId(), source, source.getControllerId()));
        return true;
    }
}
