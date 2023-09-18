package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author L_J
 */
public final class ThresherBeast extends CardImpl {

    public ThresherBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Thresher Beast becomes blocked, defending player sacrifices a land.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(
                new SacrificeEffect(StaticFilters.FILTER_LAND, 1, "defending player"),
                false, true));
    }

    private ThresherBeast(final ThresherBeast card) {
        super(card);
    }

    @Override
    public ThresherBeast copy() {
        return new ThresherBeast(this);
    }
}
