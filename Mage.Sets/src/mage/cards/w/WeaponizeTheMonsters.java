package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeaponizeTheMonsters extends CardImpl {

    public WeaponizeTheMonsters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // {2}, Sacrifice a creature: Weaponize the Monsters deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(
                StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
        )));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private WeaponizeTheMonsters(final WeaponizeTheMonsters card) {
        super(card);
    }

    @Override
    public WeaponizeTheMonsters copy() {
        return new WeaponizeTheMonsters(this);
    }
}
