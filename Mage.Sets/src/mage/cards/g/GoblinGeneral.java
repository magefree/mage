
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class GoblinGeneral extends CardImpl {

    public GoblinGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Goblin General attacks, Goblin creatures you control get +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, false), false));
    }

    private GoblinGeneral(final GoblinGeneral card) {
        super(card);
    }

    @Override
    public GoblinGeneral copy() {
        return new GoblinGeneral(this);
    }
}
