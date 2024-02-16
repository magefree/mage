package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Temba21
 */
public final class WormfangDrake extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("a creature you control other than {this}");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public WormfangDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.NIGHTMARE, SubType.DRAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Wormfang Drake enters the battlefield, sacrifice it unless you exile a creature you control other than Wormfang Drake.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new ExileTargetCost(new TargetControlledPermanent(filter))),
                false
        ));

        // When Wormfang Drake leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false));
    }

    private WormfangDrake(final WormfangDrake card) {
        super(card);
    }

    @Override
    public WormfangDrake copy() {
        return new WormfangDrake(this);
    }
}
