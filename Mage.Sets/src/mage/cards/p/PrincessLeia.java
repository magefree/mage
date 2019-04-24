
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.permanent.token.RebelToken;

/**
 *
 * @author Styxo
 */
public final class PrincessLeia extends CardImpl {

    private static final FilterControlledCreaturePermanent filter1 = new FilterControlledCreaturePermanent("other Rebel creature you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Rebel creature you control");

    static {
        SubtypePredicate rebel = new SubtypePredicate(SubType.REBEL);
        filter1.add(new AnotherPredicate());
        filter1.add(rebel);
        filter2.add(rebel);
    }

    public PrincessLeia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Princess Leia enters the battlefield, create three 1/1 white Rebel creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RebelToken(), 3)));

        // Princess Leia gets +1/+1 for each other Rebel creature you control.
        DynamicValue count = new PermanentsOnBattlefieldCount(filter1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(count, count, Duration.WhileOnBattlefield)));

        // Other Rebel creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter2, true)));

    }

    public PrincessLeia(final PrincessLeia card) {
        super(card);
    }

    @Override
    public PrincessLeia copy() {
        return new PrincessLeia(this);
    }
}
