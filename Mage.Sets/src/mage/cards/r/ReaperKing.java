
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class ReaperKing extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Scarecrow creatures");
    private static final FilterCreaturePermanent filterTrigger = new FilterCreaturePermanent("another Scarecrow");

    static {
        filter.add(SubType.SCARECROW.getPredicate());
        filterTrigger.add(AnotherPredicate.instance);
        filterTrigger.add(SubType.SCARECROW.getPredicate());
    }

    public ReaperKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2/W}{2/U}{2/B}{2/R}{2/G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SCARECROW);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Other Scarecrow creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
        // Whenever another Scarecrow enters the battlefield under your control, destroy target permanent.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new DestroyTargetEffect(), filterTrigger);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

    }

    private ReaperKing(final ReaperKing card) {
        super(card);
    }

    @Override
    public ReaperKing copy() {
        return new ReaperKing(this);
    }
}
