package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SquirrelMob extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SQUIRREL, "");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public SquirrelMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.SQUIRREL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Squirrel Mob gets +1/+1 for each other Squirrel on the battlefield.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                xValue, xValue, Duration.WhileOnBattlefield, false
        ).setText("{this} gets +1/+1 for each other Squirrel on the battlefield")));
    }

    private SquirrelMob(final SquirrelMob card) {
        super(card);
    }

    @Override
    public SquirrelMob copy() {
        return new SquirrelMob(this);
    }
}
