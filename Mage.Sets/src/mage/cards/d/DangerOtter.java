package mage.cards.d;

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
 * @author ChesseTheWasp
 */
public final class DangerOtter extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.OTTER, "Otters");

    public DangerOtter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        
        this.subtype.add(SubType.OTTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect( //buff other creatures of same type by +1/+0
                1, 0, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private DangerOtter(final DangerOtter card) {
        super(card);
    }

    @Override
    public DangerOtter copy() {
        return new DangerOtter(this);
    }
}