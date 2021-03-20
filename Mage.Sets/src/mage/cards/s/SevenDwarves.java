package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SevenDwarves extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new NamePredicate("Seven Dwarves"));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public SevenDwarves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Seven Dwarves gets +1/+1 for each other creature named Seven Dwarves you control.
        this.addAbility(new SimpleStaticAbility(
                new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield).setText(
                        "{this} gets +1/+1 for each other creature named Seven Dwarves you control"
                )
        ));

        // A deck can have up to seven cards named Seven Dwarves.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("A deck can have up to seven cards named Seven Dwarves.")
        ));
    }

    private SevenDwarves(final SevenDwarves card) {
        super(card);
    }

    @Override
    public SevenDwarves copy() {
        return new SevenDwarves(this);
    }
}
