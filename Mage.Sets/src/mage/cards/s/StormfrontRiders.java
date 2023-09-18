
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author LevelX2
 */
public final class StormfrontRiders extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public StormfrontRiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Stormfront Riders enters the battlefield, return two creatures you control to their owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(StaticFilters.FILTER_CONTROLLED_CREATURES, 2)));
        // Whenever Stormfront Riders or another creature is returned to your hand from the battlefield, create a 1/1 white Soldier creature token.
        this.addAbility(new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.HAND, new CreateTokenEffect(new SoldierToken()),
                filter, "Whenever {this} or another creature is returned to your hand from the battlefield, ", false));

    }

    private StormfrontRiders(final StormfrontRiders card) {
        super(card);
    }

    @Override
    public StormfrontRiders copy() {
        return new StormfrontRiders(this);
    }
}
