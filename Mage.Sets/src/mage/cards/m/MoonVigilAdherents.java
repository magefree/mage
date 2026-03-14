package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonVigilAdherents extends CardImpl {

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            CreaturesYouControlCount.SINGULAR, new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)
    );
    private static final Hint hint = new ValueHint(
            "Creatures you control and creature cards in your graveyard", xValue
    );

    public MoonVigilAdherents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // This creature gets +1/+1 for each creature you control and each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)
                .setText("{this} gets +1/+1 for each creature you control and each creature card in your graveyard")).addHint(hint));
    }

    private MoonVigilAdherents(final MoonVigilAdherents card) {
        super(card);
    }

    @Override
    public MoonVigilAdherents copy() {
        return new MoonVigilAdherents(this);
    }
}
