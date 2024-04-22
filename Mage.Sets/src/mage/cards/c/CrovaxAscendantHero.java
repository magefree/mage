
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Loki
 */
public final class CrovaxAscendantHero extends CardImpl {
    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("white creatures");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Nonwhite creatures");

    static {
        filter1.add(new ColorPredicate(ObjectColor.WHITE));
        filter2.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }

    public CrovaxAscendantHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Other white creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter1, true)));
        // Nonwhite creatures get -1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(-1, -1, Duration.WhileOnBattlefield, filter2, false)));
        // Pay 2 life: Return Crovax, Ascendant Hero to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new PayLifeCost(2)));
    }

    private CrovaxAscendantHero(final CrovaxAscendantHero card) {
        super(card);
    }

    @Override
    public CrovaxAscendantHero copy() {
        return new CrovaxAscendantHero(this);
    }
}
