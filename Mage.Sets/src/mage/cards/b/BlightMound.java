package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.Pest11GainLifeToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlightMound extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.PEST, "attacking Pests");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public BlightMound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Attacking Pests you control get +1/+0 and have menace.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.WhileOnBattlefield, filter
        ).setText("and have menace"));
        this.addAbility(ability);

        // Whenever a nontoken creature you control dies, create a 1/1 black and green Pest creature token with "When this creature dies, you gain 1 life."
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new Pest11GainLifeToken()), false, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN
        ));
    }

    private BlightMound(final BlightMound card) {
        super(card);
    }

    @Override
    public BlightMound copy() {
        return new BlightMound(this);
    }
}
