
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class OathswornGiant extends CardImpl {

    public OathswornGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(VigilanceAbility.getInstance());
        // Other creatures you control get +0/+2 and have vigilance.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 2, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, true));
        ability.addEffect(new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, true).setText("and have vigilance"));
        this.addAbility(ability);
    }

    private OathswornGiant(final OathswornGiant card) {
        super(card);
    }

    @Override
    public OathswornGiant copy() {
        return new OathswornGiant(this);
    }
}
