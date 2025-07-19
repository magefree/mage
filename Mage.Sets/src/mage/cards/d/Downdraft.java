
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Downdraft extends CardImpl {

    public Downdraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // {G}: Target creature loses flying until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new LoseAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Sacrifice Downdraft: Downdraft deals 2 damage to each creature with flying.
        this.addAbility(new SimpleActivatedAbility(new DamageAllEffect(2, "it", StaticFilters.FILTER_CREATURE_FLYING), new SacrificeSourceCost()));
    }

    private Downdraft(final Downdraft card) {
        super(card);
    }

    @Override
    public Downdraft copy() {
        return new Downdraft(this);
    }
}
