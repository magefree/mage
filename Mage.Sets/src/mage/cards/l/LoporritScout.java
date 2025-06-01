package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoporritScout extends CardImpl {

    public LoporritScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever another creature you control enters, this creature gets +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ));
    }

    private LoporritScout(final LoporritScout card) {
        super(card);
    }

    @Override
    public LoporritScout copy() {
        return new LoporritScout(this);
    }
}
