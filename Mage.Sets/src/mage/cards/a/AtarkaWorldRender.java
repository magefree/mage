
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class AtarkaWorldRender extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dragon creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.DRAGON.getPredicate());
    }

    public AtarkaWorldRender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a Dragon you control attacks, it gains double strike until end of turn.
        this.addAbility(new AttacksAllTriggeredAbility(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance())
                .setText("it gains double strike until end of turn"), false, filter, SetTargetPointer.PERMANENT, false));

    }

    private AtarkaWorldRender(final AtarkaWorldRender card) {
        super(card);
    }

    @Override
    public AtarkaWorldRender copy() {
        return new AtarkaWorldRender(this);
    }
}
