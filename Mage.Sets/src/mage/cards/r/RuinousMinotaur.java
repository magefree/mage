
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class RuinousMinotaur extends CardImpl {

    public RuinousMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Whenever Ruinous Minotaur deals damage to an opponent, sacrifice a land.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_LAND, 1, ""), false, false));

    }

    private RuinousMinotaur(final RuinousMinotaur card) {
        super(card);
    }

    @Override
    public RuinousMinotaur copy() {
        return new RuinousMinotaur(this);
    }
}
