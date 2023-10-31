package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class PrimalForcemage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public PrimalForcemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another creature enters the battlefield under your control, that creature gets +3/+3 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new BoostTargetEffect(3, 3, Duration.EndOfTurn).setText("that creature gets +3/+3 until end of turn"),
                StaticFilters.FILTER_ANOTHER_CREATURE, false, SetTargetPointer.PERMANENT));
    }

    private PrimalForcemage(final PrimalForcemage card) {
        super(card);
    }

    @Override
    public PrimalForcemage copy() {
        return new PrimalForcemage(this);
    }
}
