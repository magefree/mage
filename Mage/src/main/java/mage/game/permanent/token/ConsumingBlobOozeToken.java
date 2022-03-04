package mage.game.permanent.token;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.constants.*;
import mage.game.Game;

import java.util.Arrays;

/**
 * @author ciaccona007
 */
public final class ConsumingBlobOozeToken extends TokenImpl {

    public ConsumingBlobOozeToken() {
        super("Ooze", "green Ooze creature token with \"This creature's power is equal to the number of card types among cards in your graveyard and its toughness is equal to that number plus 1.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.OOZE);
        color.setGreen(true);

        power = new MageInt(0);
        toughness = new MageInt(1);

        // This creature's power is equal to the number of card types among cards in your graveyard and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ConsumingBlobTokenEffect()).addHint(CardTypesInGraveyardHint.YOU));

        availableImageSetCodes.addAll(Arrays.asList("MID"));
    }

    private ConsumingBlobOozeToken(final ConsumingBlobOozeToken token) {
        super(token);
    }

    @Override
    public ConsumingBlobOozeToken copy() {
        return new ConsumingBlobOozeToken(this);
    }
}


class ConsumingBlobTokenEffect extends ContinuousEffectImpl {

    public ConsumingBlobTokenEffect() {
        super(Duration.EndOfGame, Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, Outcome.BoostCreature);
        staticText = "{this}'s power is equal to the number of card types among cards in your graveyard and its toughness is equal to that number plus 1";
    }

    public ConsumingBlobTokenEffect(final ConsumingBlobTokenEffect effect) {
        super(effect);
    }

    @Override
    public ConsumingBlobTokenEffect copy() {
        return new ConsumingBlobTokenEffect(this);
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
