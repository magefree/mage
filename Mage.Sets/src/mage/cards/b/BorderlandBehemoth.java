package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BorderlandBehemoth extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GIANT);

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 4);

    public BorderlandBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.GIANT, SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Borderland Behemoth gets +4/+4 for each other Giant you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                xValue, xValue, Duration.WhileOnBattlefield
        ).setText("{this} gets +4/+4 for each other Giant you control")));
    }

    private BorderlandBehemoth(final BorderlandBehemoth card) {
        super(card);
    }

    @Override
    public BorderlandBehemoth copy() {
        return new BorderlandBehemoth(this);
    }
}
