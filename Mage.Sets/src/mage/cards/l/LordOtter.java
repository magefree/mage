package mage.cards.l;

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
 *
 * @author ChesseTheWasp
 */
public final class LordOtter extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.OTTER, "Otters");
    public LordOtter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.OTTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect( //buff other creatures of same type by +1/+1
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private LordOtter(final LordOtter card) {
        super(card);
    }

    @Override
    public LordOtter copy() {
        return new LordOtter(this);
    }
}