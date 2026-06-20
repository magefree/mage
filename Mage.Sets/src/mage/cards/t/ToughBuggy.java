package mage.cards.t;

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
public final class ToughBuggy extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.INSECT, "Insects");

    public ToughBuggy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect( //buff other creatures of same type by +0/+1
                0, 1, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private ToughBuggy(final ToughBuggy card) {
        super(card);
    }

    @Override
    public ToughBuggy copy() {
        return new ToughBuggy(this);
    }
}