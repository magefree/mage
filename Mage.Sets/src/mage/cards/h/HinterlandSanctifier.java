package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HinterlandSanctifier extends CardImpl {

    public HinterlandSanctifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever another creature you control enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new GainLifeEffect(1), StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ));
    }

    private HinterlandSanctifier(final HinterlandSanctifier card) {
        super(card);
    }

    @Override
    public HinterlandSanctifier copy() {
        return new HinterlandSanctifier(this);
    }
}
