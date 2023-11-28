package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class HealerOfThePride extends CardImpl {

    public HealerOfThePride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature enters the battlefield under your control, you gain 2 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(2),
                StaticFilters.FILTER_ANOTHER_CREATURE, false));

    }

    private HealerOfThePride(final HealerOfThePride card) {
        super(card);
    }

    @Override
    public HealerOfThePride copy() {
        return new HealerOfThePride(this);
    }
}
