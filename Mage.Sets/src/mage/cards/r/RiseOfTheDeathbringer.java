package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class RiseOfTheDeathbringer extends CardImpl {

    public RiseOfTheDeathbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Choose one --
        // * Draw cards equal to the greatest power among creatures you control. You lose life equal to the number of cards drawn this way.
        this.getSpellAbility().addEffect(new RiseOfTheDeathbringerDrawEffect());

        // * All creatures get -3/-3 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostAllEffect(-3, -3, Duration.EndOfTurn)));
    }

    private RiseOfTheDeathbringer(final RiseOfTheDeathbringer card) {
        super(card);
    }

    @Override
    public RiseOfTheDeathbringer copy() {
        return new RiseOfTheDeathbringer(this);
    }
}

class RiseOfTheDeathbringerDrawEffect extends OneShotEffect {

    RiseOfTheDeathbringerDrawEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw cards equal to the greatest power among creatures you control. "
                + "You lose life equal to the number of cards drawn this way.";
    }

    private RiseOfTheDeathbringerDrawEffect(final RiseOfTheDeathbringerDrawEffect effect) {
        super(effect);
    }

    @Override
    public RiseOfTheDeathbringerDrawEffect copy() {
        return new RiseOfTheDeathbringerDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        int cardsDrawn = player.drawCards(
            GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.calculate(game, source, this),
            source, game
        );
        player.loseLife(cardsDrawn, game, source, false);
        return true;
    }
}
