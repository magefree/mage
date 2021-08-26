package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class IntangibleVirtue extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public IntangibleVirtue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Creature tokens you control get +1/+1 and have vigilance.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and have vigilance"));
        this.addAbility(ability);
    }

    private IntangibleVirtue(final IntangibleVirtue card) {
        super(card);
    }

    @Override
    public IntangibleVirtue copy() {
        return new IntangibleVirtue(this);
    }
}
