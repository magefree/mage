
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class MagusOfTheTabernacle extends CardImpl {

    static private final String rule = "All creatures have \"At the beginning of your upkeep, sacrifice this creature unless you pay {1}.\"";

    public MagusOfTheTabernacle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // All creatures have "At the beginning of your upkeep, sacrifice this creature unless you pay {1}."
        Ability abilityToGain = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new GenericManaCost(1)), TargetController.YOU, false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(abilityToGain, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE, rule)));
    }

    private MagusOfTheTabernacle(final MagusOfTheTabernacle card) {
        super(card);
    }

    @Override
    public MagusOfTheTabernacle copy() {
        return new MagusOfTheTabernacle(this);
    }
}
