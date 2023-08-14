package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.*;

/**
 * @author ciaccona007
 */
public final class ConsumingBlobOozeToken extends TokenImpl {

    private static final DynamicValue powerValue = CardTypesInGraveyardCount.YOU;
    private static final DynamicValue toughnessValue = new AdditiveDynamicValue(powerValue, StaticValue.get(1));

    public ConsumingBlobOozeToken() {
        super("Ooze Token", "green Ooze creature token with \"This creature's power is equal to the number of card types among cards in your graveyard and its toughness is equal to that number plus 1.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.OOZE);
        color.setGreen(true);

        power = new MageInt(0);
        toughness = new MageInt(1);

        // This creature's power is equal to the number of card types among cards in your graveyard and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerToughnessSourceEffect(powerValue, toughnessValue, Duration.EndOfGame, SubLayer.CharacteristicDefining_7a)
                        .setText("{this}'s power is equal to the number of creature cards in all graveyards and its toughness is equal to that number plus 1")
        ));
    }

    private ConsumingBlobOozeToken(final ConsumingBlobOozeToken token) {
        super(token);
    }

    @Override
    public ConsumingBlobOozeToken copy() {
        return new ConsumingBlobOozeToken(this);
    }
}
