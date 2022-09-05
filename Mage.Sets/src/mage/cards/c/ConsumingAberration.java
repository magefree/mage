
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class ConsumingAberration extends CardImpl {

    public ConsumingAberration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        //Consuming Aberration's power and toughness are each equal to the number of cards in your opponents' graveyards.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new CardsInOpponentsGraveyardsCount(), Duration.EndOfGame)));
        //Whenever you cast a spell, each opponent reveals cards from the top of their library until they reveal a land card, then puts those cards into their graveyard.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ConsumingAberrationEffect(), false));
    }

    private ConsumingAberration(final ConsumingAberration card) {
        super(card);
    }

    @Override
    public ConsumingAberration copy() {
        return new ConsumingAberration(this);
    }
}

class ConsumingAberrationEffect extends OneShotEffect {

    public ConsumingAberrationEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "each opponent reveals cards from the top of their library until they reveal a land card, then puts those cards into their graveyard";
    }

    public ConsumingAberrationEffect(final ConsumingAberrationEffect effect) {
        super(effect);
    }

    @Override
    public ConsumingAberrationEffect copy() {
        return new ConsumingAberrationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player == null) {
                continue;
            }
            Cards cards = new CardsImpl();
            for (Card card : player.getLibrary().getCards(game)) {
                if (card != null) {
                    cards.add(card);
                    if (card.isLand(game)) {
                        break;
                    }
                }
            }
            player.revealCards(source, cards, game);
            player.moveCards(cards, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}

class CardsInOpponentsGraveyardsCount implements DynamicValue {

    public CardsInOpponentsGraveyardsCount() {
        super();
    }

    public CardsInOpponentsGraveyardsCount(DynamicValue count) {
        super();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        for (UUID playerUUID : game.getOpponents(sourceAbility.getControllerId())) {
            Player player = game.getPlayer(playerUUID);
            if (player != null) {
                amount += player.getGraveyard().size();
            }
        }
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return new CardsInOpponentsGraveyardsCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "cards in your opponents' graveyards";
    }
}
