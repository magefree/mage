
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HarbingerOfTheTides extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature an opponent controls");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public HarbingerOfTheTides(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // You may cast Harbinger of the Tides as though it had flash if you pay {2} more to cast it.
        this.addAbility(new PayMoreToCastAsThoughtItHadFlashAbility(this, new ManaCostsImpl<>("{2}")));

        // When Harbinger of the Tides enters the battlefield, you may return target tapped creature an opponent controls to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

    }

    private HarbingerOfTheTides(final HarbingerOfTheTides card) {
        super(card);
    }

    @Override
    public HarbingerOfTheTides copy() {
        return new HarbingerOfTheTides(this);
    }
}
