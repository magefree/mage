
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KolaghanTheStormsFury extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dragon you control");

    static {
        filter.add(SubType.DRAGON.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public KolaghanTheStormsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Dragon you control attacks, creatures you control get +1/+0 until end of turn.
        this.addAbility(new AttacksAllTriggeredAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn),
                false, filter, SetTargetPointer.NONE, false));

        // Dash {3}{B}{R}
        this.addAbility(new DashAbility("{3}{B}{R}"));
    }

    private KolaghanTheStormsFury(final KolaghanTheStormsFury card) {
        super(card);
    }

    @Override
    public KolaghanTheStormsFury copy() {
        return new KolaghanTheStormsFury(this);
    }
}
