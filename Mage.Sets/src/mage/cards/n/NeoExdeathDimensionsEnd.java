package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeoExdeathDimensionsEnd extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENTS);

    public NeoExdeathDimensionsEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);
        this.nightCard = true;
        this.color.setBlack(true);
        this.color.setGreen(true);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Neo Exdeath's power is equal to the number of permanent cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new SetBasePowerSourceEffect(xValue)));
    }

    private NeoExdeathDimensionsEnd(final NeoExdeathDimensionsEnd card) {
        super(card);
    }

    @Override
    public NeoExdeathDimensionsEnd copy() {
        return new NeoExdeathDimensionsEnd(this);
    }
}
