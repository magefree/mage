
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LoneFox
 */
public final class DesolationGiant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("other creatures you control");

    static {
        filter.add(new AnotherPredicate());
        filter2.add(new AnotherPredicate());
        filter2.add(new ControllerPredicate(TargetController.YOU));
    }

    public DesolationGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kicker {W}{W}
        this.addAbility(new KickerAbility("{W}{W}"));
        // When Desolation Giant enters the battlefield, destroy all other creatures you control. If it was kicked, destroy all other creatures instead.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(new DestroyAllEffect(filter),
            new DestroyAllEffect(filter2), KickedCondition.instance, "destroy all other creatures you control. If it was kicked, destroy all other creatures instead.")));
}

    public DesolationGiant(final DesolationGiant card) {
        super(card);
    }

    @Override
    public DesolationGiant copy() {
        return new DesolationGiant(this);
    }
}
