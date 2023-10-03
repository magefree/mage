
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 * @author tcontis
 */
public final class WormfangTurtle extends CardImpl {

    public WormfangTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Wormfang Turtle enters the battlefield, exile a land you control.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect(), false);
        Target target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_A_LAND).withNotTarget(true);
        ability1.addTarget(target);
        this.addAbility(ability1);

        // When Wormfang Turtle leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        this.addAbility(ability2);
    }

    private WormfangTurtle(final WormfangTurtle card) {
        super(card);
    }

    @Override
    public WormfangTurtle copy() {
        return new WormfangTurtle(this);
    }
}