package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SplinterAgingChampion extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");
    private static final FilterPlayer filter2 = new FilterPlayer("another target player");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter2.add(TargetController.NOT_YOU.getPlayerPredicate());
    }

    public SplinterAgingChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Splinter enters, destroy up to one target tapped creature.
        Ability enterAbility = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        enterAbility.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(enterAbility);

        // When Splinter leaves the battlefield, you and another target player each draw a card.
        Ability leaveAbility = new LeavesBattlefieldTriggeredAbility(
            new DrawCardSourceControllerEffect(1)
                .setText("you"),
            false
        );
        leaveAbility.addEffect(new DrawCardTargetEffect(1)
            .setText("and another target player each draw a card"));
        leaveAbility.addTarget(new TargetPlayer(filter2));
        this.addAbility(leaveAbility);
    }

    private SplinterAgingChampion(final SplinterAgingChampion card) {
        super(card);
    }

    @Override
    public SplinterAgingChampion copy() {
        return new SplinterAgingChampion(this);
    }
}
