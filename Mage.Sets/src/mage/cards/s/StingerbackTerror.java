package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PlotAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StingerbackTerror extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(CardsInControllerHandCount.instance);

    public StingerbackTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.SCORPION);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Stingerback Terror gets -1/-1 for each card in your hand.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                xValue, xValue, Duration.WhileOnBattlefield,
                "{this} gets -1/-1 for each card in your hand"
        )));

        // Plot {2}{R}
        this.addAbility(new PlotAbility(this, "{2}{R}"));
    }

    private StingerbackTerror(final StingerbackTerror card) {
        super(card);
    }

    @Override
    public StingerbackTerror copy() {
        return new StingerbackTerror(this);
    }
}
