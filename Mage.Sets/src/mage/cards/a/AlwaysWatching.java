
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
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
 * @author fireshoes
 */
public final class AlwaysWatching extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creatures");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public AlwaysWatching(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");

        // Nontoken creatures you control get +1/+1 and have vigilance.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter));
        Effect effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("and have vigilance");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private AlwaysWatching(final AlwaysWatching card) {
        super(card);
    }

    @Override
    public AlwaysWatching copy() {
        return new AlwaysWatching(this);
    }
}
