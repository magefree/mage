package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class WallOfNets extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures blocked by {this}");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKED_BY);
    }

    public WallOfNets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(7);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // At end of combat, exile all creatures blocked by Wall of Nets.
        this.addAbility(new EndOfCombatTriggeredAbility(
                new ExileAllEffect(filter, true), false
        ));

        // When Wall of Nets leaves the battlefield, return all cards exiled with Wall of Nets to the battlefield under their owners' control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileEffect(
                Zone.BATTLEFIELD, "return all cards exiled with {this} " +
                "to the battlefield under their owners' control"
        ), false));
    }

    private WallOfNets(final WallOfNets card) {
        super(card);
    }

    @Override
    public WallOfNets copy() {
        return new WallOfNets(this);
    }
}
