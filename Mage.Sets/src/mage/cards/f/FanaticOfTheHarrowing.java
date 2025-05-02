package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FanaticOfTheHarrowing extends CardImpl {

    public FanaticOfTheHarrowing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Fanatic of the Harrowing enters, each player discards a card. If you discarded a card this way, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FanaticOfTheHarrowingEffect()));
    }

    private FanaticOfTheHarrowing(final FanaticOfTheHarrowing card) {
        super(card);
    }

    @Override
    public FanaticOfTheHarrowing copy() {
        return new FanaticOfTheHarrowing(this);
    }
}

class FanaticOfTheHarrowingEffect extends OneShotEffect {

    FanaticOfTheHarrowingEffect() {
        super(Outcome.Benefit);
        staticText = "each player discards a card. If you discarded a card this way, draw a card";
    }

    private FanaticOfTheHarrowingEffect(final FanaticOfTheHarrowingEffect effect) {
        super(effect);
    }

    @Override
    public FanaticOfTheHarrowingEffect copy() {
        return new FanaticOfTheHarrowingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean flag = false;
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            if (!player.discard(1, false, false, source, game).isEmpty()
                    && playerId.equals(source.getControllerId())) {
                flag = true;
            }
        }
        game.processAction();
        if (flag) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.drawCards(1, source, game);
            }
        }
        return true;
    }
}
