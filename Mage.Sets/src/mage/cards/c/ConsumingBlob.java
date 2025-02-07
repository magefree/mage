package mage.cards.c;

import mage.MageInt;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessPlusOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.ConsumingBlobOozeToken;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class ConsumingBlob extends CardImpl {

    public ConsumingBlob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Consuming Blob's power is equal to the number of card types among cards in your graveyard and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SetBasePowerToughnessPlusOneSourceEffect(CardTypesInGraveyardCount.YOU)
        ).addHint(CardTypesInGraveyardCount.YOU.getHint()));

        // At the beginning of your end step, create a green Ooze creature token with "This creature's power is equal to the number of card types among cards in your graveyard and its toughness is equal to that number plus 1".
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new ConsumingBlobOozeToken()))
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
