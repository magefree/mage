
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 * 
 * @author L_J
 */
public final class FortifiedArea extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wall creatures");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public FortifiedArea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");

        // Wall creatures you control get +1/+0 and have banding.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter));
        Effect effect = new GainAbilityControlledEffect(BandingAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("and have banding");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private FortifiedArea(final FortifiedArea card) {
        super(card);
    }

    @Override
    public FortifiedArea copy() {
        return new FortifiedArea(this);
    }
}
