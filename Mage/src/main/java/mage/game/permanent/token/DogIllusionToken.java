package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.Arrays;

/**
 *
 * @author weirddan455
 */
public final class DogIllusionToken extends TokenImpl {

    public DogIllusionToken() {
        super("Dog Illusion Token", "blue Dog Illusion creature token with \"This creature's power and toughness are each equal to twice the number of cards in your hand.\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.DOG);
        subtype.add(SubType.ILLUSION);
        power = new MageInt(0);
        toughness = new MageInt(0);

        // This creature's power and toughness are each equal to twice the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(
                DogIllusionValue.instance, Duration.EndOfGame)
                .setText("this creature's power and toughness are each equal to twice the number of cards in your hand")
        ));

        availableImageSetCodes = Arrays.asList("AFR");
    }

    private DogIllusionToken(final DogIllusionToken token) {
        super(token);
    }

    @Override
    public DogIllusionToken copy() {
        return new DogIllusionToken(this);
    }
}

enum DogIllusionValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller == null) {
            return 0;
        }
        return controller.getHand().size() * 2;
    }

    @Override
    public DogIllusionValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "twice the number of cards in your hand";
    }
}
