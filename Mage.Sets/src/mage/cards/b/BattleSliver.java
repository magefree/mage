package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BattleSliver extends CardImpl {

    public BattleSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sliver creatures you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 0, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_SLIVERS
        )));
    }

    private BattleSliver(final BattleSliver card) {
        super(card);
    }

    @Override
    public BattleSliver copy() {
        return new BattleSliver(this);
    }
}
