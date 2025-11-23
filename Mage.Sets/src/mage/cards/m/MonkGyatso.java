package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.keyword.AirbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonkGyatso extends CardImpl {

    public MonkGyatso(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another creature you control becomes the target of a spell or ability, you may airbend that creature.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(
                new AirbendTargetEffect().setText("airbend that creature"),
                StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ).setOptional(true));
    }

    private MonkGyatso(final MonkGyatso card) {
        super(card);
    }

    @Override
    public MonkGyatso copy() {
        return new MonkGyatso(this);
    }
}
