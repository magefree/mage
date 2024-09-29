package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox

 */
public final class FalseProphet extends CardImpl {

    public FalseProphet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When False Prophet dies, exile all creatures.
        this.addAbility(new DiesSourceTriggeredAbility(new ExileAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES)));
    }

    private FalseProphet(final FalseProphet card) {
        super(card);
    }

    @Override
    public FalseProphet copy() {
        return new FalseProphet(this);
    }
}
