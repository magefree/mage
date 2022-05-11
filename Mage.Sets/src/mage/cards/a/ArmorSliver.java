
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class ArmorSliver extends CardImpl {

    public ArmorSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(0, 1, Duration.EndOfTurn).setText("this creature gets +0/+1 until end of turn"),
                        new GenericManaCost(2)), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_SLIVERS, false)));
    }

    private ArmorSliver(final ArmorSliver card) {
        super(card);
    }

    @Override
    public ArmorSliver copy() {
        return new ArmorSliver(this);
    }
}
