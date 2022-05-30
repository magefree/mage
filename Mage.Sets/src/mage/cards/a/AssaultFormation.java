
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AssaultFormation extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with defender");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public AssaultFormation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Each creature you control assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CombatDamageByToughnessEffect(StaticFilters.FILTER_PERMANENT_CREATURE, true)));

        // {G}: Target creature with defender can attack this turn as though it didn't have defender.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CanAttackAsThoughItDidntHaveDefenderTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{G}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // {2}{G}: Creatures you control get +0/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}")));

    }

    private AssaultFormation(final AssaultFormation card) {
        super(card);
    }

    @Override
    public AssaultFormation copy() {
        return new AssaultFormation(this);
    }
}
