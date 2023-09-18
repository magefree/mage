package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StreetUrchin extends CardImpl {

    public StreetUrchin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "{1}, Sacrifice another creature or an artifact: This creature deals 1 damage to any target."
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1, "this creature"), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_OTHER_CREATURE));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                ability, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private StreetUrchin(final StreetUrchin card) {
        super(card);
    }

    @Override
    public StreetUrchin copy() {
        return new StreetUrchin(this);
    }
}
