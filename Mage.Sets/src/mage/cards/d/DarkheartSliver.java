
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author KholdFuzion
 */
public final class DarkheartSliver extends CardImpl {

    public DarkheartSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers have "Sacrifice this permanent: You gain 3 life."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(3), new SacrificeSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability,
                        Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_SLIVERS,
                        "All Slivers have \"Sacrifice this permanent: You gain 3 life.\"")));
    }

    private DarkheartSliver(final DarkheartSliver card) {
        super(card);
    }

    @Override
    public DarkheartSliver copy() {
        return new DarkheartSliver(this);
    }
}
