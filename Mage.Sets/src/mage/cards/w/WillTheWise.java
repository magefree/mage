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
public final class WillTheWise extends CardImpl {

    public WillTheWise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Will the Wise enters or leaves the battlefield, each opponent may investigate. Each opponent who doesn't loses 1 life. You investigate X times, where X is one plus the number of opponents who investigated this way.
        this.addAbility(new EntersBattlefieldOrLeavesSourceTriggeredAbility(new WillTheWiseEffect(), false));

        // Friends forever
        this.addAbility(FriendsForeverAbility.getInstance());
    }

    private WillTheWise(final WillTheWise card) {
        super(card);
    }

    @Override
    public WillTheWise copy() {
        return new WillTheWise(this);
    }
}

class WillTheWiseEffect extends OneShotEffect {

    WillTheWiseEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent may investigate. Each opponent who doesn't loses 1 life. " +
                "You investigate X times, where X is one plus the number of opponents who investigated this way";
    }

    private WillTheWiseEffect(final WillTheWiseEffect effect) {
        super(effect);
    }

    @Override
    public WillTheWiseEffect copy() {
        return new WillTheWiseEffect(this);
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
