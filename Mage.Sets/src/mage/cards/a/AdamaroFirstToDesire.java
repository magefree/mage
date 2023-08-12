
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AdamaroFirstToDesire extends CardImpl {

    public AdamaroFirstToDesire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Adamaro, First to Desire's power and toughness are each equal to the number of cards in the hand of the opponent with the most cards in hand.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new MostCardsInOpponentsHandCount())));
    }

    private AdamaroFirstToDesire(final AdamaroFirstToDesire card) {
        super(card);
    }

    @Override
    public AdamaroFirstToDesire copy() {
        return new AdamaroFirstToDesire(this);
    }
}

class MostCardsInOpponentsHandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int maxCards = 0;
        for (UUID opponentId : game.getOpponents(sourceAbility.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                int cards = opponent.getHand().size();
                if (cards > maxCards) {
                    maxCards = cards;
                }
            }
        }
        return maxCards;
    }

    @Override
    public MostCardsInOpponentsHandCount copy() {
        return new MostCardsInOpponentsHandCount();
    }

    @Override
    public String getMessage() {
        return "cards in the hand of the opponent with the most cards in hand";
    }

    @Override
    public String toString() {
        return "1";
    }
}
