
package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author BetaSteward
 */
public final class IntangibleVirtue extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creature tokens");

    static {
        filter.add(new TokenPredicate());
    }

    public IntangibleVirtue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // Creature tokens you control get +1/+1 and have vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter)));

    }

    public IntangibleVirtue(final IntangibleVirtue card) {
        super(card);
    }

    @Override
    public IntangibleVirtue copy() {
        return new IntangibleVirtue(this);
    }
}
