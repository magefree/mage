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
public final class LordLizard extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.LIZARD, "Lizards");
    public LordLizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{B}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect( //buff other creatures of same type by +1/+1
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private LordLizard(final LordLizard card) {
        super(card);
    }

    @Override
    public LordLizard copy() {
        return new LordLizard(this);
    }
}