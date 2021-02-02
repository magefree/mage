
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterUntappedCreature;

/**
 * @author noxx
 */
public final class BuildersBlessing extends CardImpl {

    public BuildersBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");


        // Untapped creatures you control get +0/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 2, Duration.WhileOnBattlefield, new FilterUntappedCreature("untapped creatures"))));
    }

    private BuildersBlessing(final BuildersBlessing card) {
        super(card);
    }

    @Override
    public BuildersBlessing copy() {
        return new BuildersBlessing(this);
    }
}
