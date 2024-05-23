package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PerimeterEnforcer extends CardImpl {

    private static final FilterPermanent filter1 = new FilterPermanent(SubType.DETECTIVE, "another Detective");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.DETECTIVE, "a Detective you control");

    static {
        filter1.add(AnotherPredicate.instance);
        filter2.add(TargetController.YOU.getControllerPredicate());
    }


    public PerimeterEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever another Detective enters the battlefield under your control and whenever a Detective you control is turned face up, Perimeter Enforcer gets +1/+1 until end of turn.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                false,
                "Whenever another Detective enters the battlefield under your control and "
                        + "whenever a Detective you control is turned face up, ",
                new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, null, filter1, false),
                new TurnedFaceUpAllTriggeredAbility(null, filter2)
        ));
    }

    private PerimeterEnforcer(final PerimeterEnforcer card) {
        super(card);
    }

    @Override
    public PerimeterEnforcer copy() {
        return new PerimeterEnforcer(this);
    }
}
