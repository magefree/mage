
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 * @author cbt33
 */
public final class Cognivore extends CardImpl {

    static final FilterCard filter = new FilterCard("instant cards");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public Cognivore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}");
        this.subtype.add(SubType.LHURGOYF);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Cognivore's power and toughness are each equal to the number of instant cards in all graveyards.
        DynamicValue value = (new CardsInAllGraveyardsCount(filter));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(value, Duration.EndOfGame)));
    }

    private Cognivore(final Cognivore card) {
        super(card);
    }

    @Override
    public Cognivore copy() {
        return new Cognivore(this);
    }
}
