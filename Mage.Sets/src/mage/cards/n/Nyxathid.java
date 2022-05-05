
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class Nyxathid extends CardImpl {

    public Nyxathid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // As Nyxathid enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.Detriment)));

        // Nyxathid gets -1/-1 for each card in the chosen player's hand.
        DynamicValue chosenPlayerHand = new SignInversionDynamicValue(new CardsInChosenPlayerHandCount());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(chosenPlayerHand, chosenPlayerHand, Duration.WhileOnBattlefield)));

    }

    private Nyxathid(final Nyxathid card) {
        super(card);
    }

    @Override
    public Nyxathid copy() {
        return new Nyxathid(this);
    }
}

class CardsInChosenPlayerHandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            UUID playerId = (UUID) game.getState().getValue(sourceAbility.getSourceId() + ChooseOpponentEffect.VALUE_KEY);
            Player chosenPlayer = game.getPlayer(playerId);
            if (chosenPlayer != null) {
                return chosenPlayer.getHand().size();
            }
        }
        return 0;
    }

    @Override
    public CardsInChosenPlayerHandCount copy() {
        return new CardsInChosenPlayerHandCount();
    }

    @Override
    public String getMessage() {
        return "card in the chosen player's hand";
    }

    @Override
    public String toString() {
        return "1";
    }
}
