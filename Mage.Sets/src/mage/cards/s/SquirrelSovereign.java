package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SquirrelSovereign extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SQUIRREL, "Squirrels");

    public SquirrelSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Squirrels you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private SquirrelSovereign(final SquirrelSovereign card) {
        super(card);
    }

    @Override
    public SquirrelSovereign copy() {
        return new SquirrelSovereign(this);
    }
}
