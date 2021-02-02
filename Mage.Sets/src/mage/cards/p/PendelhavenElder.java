
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;

/**
 *
 * @author LoneFox
 */
public final class PendelhavenElder extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each 1/1 creature you control");

    static {
        filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, 1));
        filter.add(new ToughnessPredicate(ComparisonType.EQUAL_TO, 1));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public PendelhavenElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Each 1/1 creature you control gets +1/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 2, Duration.EndOfTurn, filter, false)
                .setText("Each 1/1 creature you control gets +1/+2 until end of turn."),
                new TapSourceCost()
        ));
    }

    private PendelhavenElder(final PendelhavenElder card) {
        super(card);
    }

    @Override
    public PendelhavenElder copy() {
        return new PendelhavenElder(this);
    }
}
