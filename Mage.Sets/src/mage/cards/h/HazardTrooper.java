
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class HazardTrooper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Trooper creatures");

    static {
        filter.add(SubType.TROOPER.getPredicate());
    }

    public HazardTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CYBORG);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trooper creatures you control have intimidate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(IntimidateAbility.getInstance(), Duration.WhileOnBattlefield, filter, false)));

    }

    private HazardTrooper(final HazardTrooper card) {
        super(card);
    }

    @Override
    public HazardTrooper copy() {
        return new HazardTrooper(this);
    }
}
