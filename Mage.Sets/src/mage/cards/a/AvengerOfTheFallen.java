package mage.cards.a;

import mage.MageInt;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MobilizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvengerOfTheFallen extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURES, null);
    private static final Hint hint = new ValueHint("Creature cards in your graveyard", xValue);

    public AvengerOfTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Mobilize X where X is the number of creature cards in your graveyard.
        this.addAbility(new MobilizeAbility(xValue).addHint(hint));
    }

    private AvengerOfTheFallen(final AvengerOfTheFallen card) {
        super(card);
    }

    @Override
    public AvengerOfTheFallen copy() {
        return new AvengerOfTheFallen(this);
    }
}
