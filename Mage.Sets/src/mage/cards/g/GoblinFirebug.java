
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class GoblinFirebug extends CardImpl {

    public GoblinFirebug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Goblin Firebug leaves the battlefield, sacrifice a land.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_LAND, 1, ""), false));
    }

    private GoblinFirebug(final GoblinFirebug card) {
        super(card);
    }

    @Override
    public GoblinFirebug copy() {
        return new GoblinFirebug(this);
    }
}
