package mage.cards.c;


import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author EikePeace
 */
public final class CircleTheWagons extends CardImpl {

    public CircleTheWagons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(+1, +2, Duration.EndOfTurn)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UntapAllControllerEffect(new FilterCreaturePermanent())));
    }

    public CircleTheWagons(final CircleTheWagons card) {
        super(card);
    }

    @Override
    public CircleTheWagons copy() {
        return new CircleTheWagons(this);
    }

}
