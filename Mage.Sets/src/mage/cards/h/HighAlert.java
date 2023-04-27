package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderAllEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HighAlert extends CardImpl {

    public HighAlert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");


        // Each creature you control assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new CombatDamageByToughnessEffect(
                        StaticFilters.FILTER_PERMANENT_CREATURE, true
                )
        ));

        // Creatures you control can attack as though they didn't have defender.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new CanAttackAsThoughItDidntHaveDefenderAllEffect(
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CONTROLLED_CREATURES
                )
        ));

        // {2}{W}{U}: Untap target creature.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new ManaCostsImpl<>("{2}{W}{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HighAlert(final HighAlert card) {
        super(card);
    }

    @Override
    public HighAlert copy() {
        return new HighAlert(this);
    }
}
