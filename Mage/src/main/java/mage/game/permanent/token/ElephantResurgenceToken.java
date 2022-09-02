
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author TheElk801
 */
public final class ElephantResurgenceToken extends TokenImpl {

    public ElephantResurgenceToken() {
        super("Elephant Token", "green Elephant creature token. Those creatures have \"This creature's power and toughness are each equal to the number of creature cards in its controller's graveyard.\"");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEPHANT);

        power = new MageInt(0);
        toughness = new MageInt(0);

        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new SetBasePowerToughnessSourceEffect(new CardsInControllerGraveyardCount(new FilterCreatureCard()), Duration.EndOfGame)
                        .setText("This creature's power and toughness are each equal to the number of creature cards in its controller's graveyard.")
        ));
    }

    public ElephantResurgenceToken(final ElephantResurgenceToken token) {
        super(token);
    }

    public ElephantResurgenceToken copy() {
        return new ElephantResurgenceToken(this);
    }
}
