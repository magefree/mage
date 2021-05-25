package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeenDuelist extends CardImpl {

    public KeenDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, you and target opponent each reveal the top card of your library. You each lose life equal to the mana value of the card revealed by the other player. You each put the card you revealed into your hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new KeenDuelistEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private KeenDuelist(final KeenDuelist card) {
        super(card);
    }

    @Override
    public KeenDuelist copy() {
        return new KeenDuelist(this);
    }
}

class KeenDuelistEffect extends OneShotEffect {

    KeenDuelistEffect() {
        super(Outcome.Benefit);
        staticText = "you and target opponent each reveal the top card of your library. " +
                "You each lose life equal to the mana value of the card revealed by the other player. " +
                "You each put the card you revealed into your hand";
    }

    private KeenDuelistEffect(final KeenDuelistEffect effect) {
        super(effect);
    }

    @Override
    public KeenDuelistEffect copy() {
        return new KeenDuelistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card myCard = controller.getLibrary().getFromTop(game);
        cards.add(myCard);
        Card theirCard = opponent.getLibrary().getFromTop(game);
        cards.add(theirCard);
        controller.revealCards(source, cards, game);
        if (theirCard != null && theirCard.getManaValue() > 0) {
            controller.loseLife(theirCard.getManaValue(), game, source, false);
        }
        if (myCard != null && myCard.getManaValue() > 0) {
            opponent.loseLife(myCard.getManaValue(), game, source, false);
        }
        controller.moveCards(cards, Zone.HAND, source, game);
        return true;
    }
}
