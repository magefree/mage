package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
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
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ConsumingBlobEffect()).addHint(CardTypesInGraveyardHint.YOU));

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

class ConsumingBlobEffect extends ContinuousEffectImpl {

    public ConsumingBlobEffect() {
        super(Duration.EndOfGame, Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, Outcome.BoostCreature);
        staticText = "{this}'s power is equal to the number of card types among cards in your graveyard and its toughness is equal to that number plus 1";
    }

    public ConsumingBlobEffect(final ConsumingBlobEffect effect) {
        super(effect);
    }

    @Override
    public ConsumingBlobEffect copy() {
        return new ConsumingBlobEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject target = source.getSourceObject(game);
        if (target == null) {
            return false;
        }
        int number = CardTypesInGraveyardCount.YOU.calculate(game, source, this);
        target.getPower().setValue(number);
        target.getToughness().setValue(number + 1);
        return true;
    }
}
