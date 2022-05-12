
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author TheElk801
 */
public final class SoundTheCallToken extends TokenImpl {

    private static final FilterCard filter = new FilterCard("card named Sound the Call");

    static {
        filter.add(new NamePredicate("Sound the Call"));
    }

    public SoundTheCallToken() {
        super("Wolf Token", "1/1 green Wolf creature token. It has \"This creature gets +1/+1 for each card named Sound the Call in each graveyard.\"");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WOLF);
        power = new MageInt(1);
        toughness = new MageInt(1);

        DynamicValue value = new CardsInAllGraveyardsCount(filter);
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(value, value, Duration.WhileOnBattlefield)
                        .setText("This creature gets +1/+1 for each card named Sound the Call in each graveyard.")
        ));
    }

    public SoundTheCallToken(final SoundTheCallToken token) {
        super(token);
    }

    public SoundTheCallToken copy() {
        return new SoundTheCallToken(this);
    }

}
