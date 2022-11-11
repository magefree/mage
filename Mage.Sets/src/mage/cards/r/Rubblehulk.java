
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.BloodrushAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class Rubblehulk extends CardImpl {

    public Rubblehulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        DynamicValue controlledLands = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS);

        // Rubblehulk's power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(controlledLands, Duration.EndOfGame)));

        // Bloodrush - 1{R}{G}, Discard Rubblehulk: Target attacking creature gets +X/+X until end of turn, where X is the number of lands you control.
        this.addAbility(new BloodrushAbility("{1}{R}{G}", new BoostTargetEffect(controlledLands, controlledLands, Duration.EndOfTurn)));
    }

    private Rubblehulk(final Rubblehulk card) {
        super(card);
    }

    @Override
    public Rubblehulk copy() {
        return new Rubblehulk(this);
    }
}
