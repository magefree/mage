
package mage.cards.s;

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
public final class ServantOfVolrath extends CardImpl {

    public ServantOfVolrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Servant of Volrath leaves the battlefield, sacrifice a creature.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, ""), false));
    }

    private ServantOfVolrath(final ServantOfVolrath card) {
        super(card);
    }

    @Override
    public ServantOfVolrath copy() {
        return new ServantOfVolrath(this);
    }
}
