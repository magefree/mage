package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class RobberFly extends CardImpl {

    public RobberFly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Robber Fly becomes blocked, defending player discards all the cards in their hand, then draws that many cards.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new DrawCardsDefendingPlayerEffect(), false, true));
    }

    private RobberFly(final RobberFly card) {
        super(card);
    }

    @Override
    public RobberFly copy() {
        return new RobberFly(this);
    }
}

class DrawCardsDefendingPlayerEffect extends OneShotEffect {

    public DrawCardsDefendingPlayerEffect() {
        super(Outcome.Benefit);
        this.staticText = "defending player discards all the cards in their hand, "
                + "then draws that many cards";
    }

    public DrawCardsDefendingPlayerEffect(final DrawCardsDefendingPlayerEffect effect) {
        super(effect);
    }

    @Override
    public DrawCardsDefendingPlayerEffect copy() {
        return new DrawCardsDefendingPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player defendingPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller != null
                && defendingPlayer != null) {
            int numberOfCardsInHand = defendingPlayer.getHand().size();
            defendingPlayer.discard(defendingPlayer.getHand(), false, source, game);
            defendingPlayer.drawCards(numberOfCardsInHand, source, game);
            return true;
        }
        return false;
    }
}
