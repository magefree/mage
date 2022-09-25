package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ConsumingBlobOozeToken;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class ConsumingBlob extends CardImpl {

    private static final DynamicValue powerValue = CardTypesInGraveyardCount.YOU;
    private static final DynamicValue toughnessValue = new AdditiveDynamicValue(powerValue, StaticValue.get(1));

    public ConsumingBlob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Consuming Blob's power is equal to the number of card types among cards in your graveyard and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerToughnessSourceEffect(powerValue, toughnessValue, Duration.EndOfGame, SubLayer.CharacteristicDefining_7a, false)
                        .setText("{this}'s power is equal to the number of creature cards in all graveyards and its toughness is equal to that number plus 1")
        ));

        // At the beginning of your end step, create a green Ooze creature token with "This creature's power is equal to the number of card types among cards in your graveyard and its toughness is equal to that number plus 1".
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new ConsumingBlobOozeToken()), TargetController.YOU, false)
        );
    }

    private ConsumingBlob(final ConsumingBlob card) {
        super(card);
    }

    @Override
    public ConsumingBlob copy() {
        return new ConsumingBlob(this);
    }
}
