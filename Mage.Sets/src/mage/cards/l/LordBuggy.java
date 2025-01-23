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
public final class LordBuggy extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.INSECT, "Insects");
    public LordBuggy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect( //buff other creatures of same type by +1/+1
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private LordBuggy(final LordBuggy card) {
        super(card);
    }

    @Override
    public LordBuggy copy() {
        return new LordBuggy(this);
    }
}