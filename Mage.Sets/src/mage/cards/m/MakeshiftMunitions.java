package mage.cards.m;

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

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MakeshiftMunitions extends CardImpl {

    public MakeshiftMunitions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // {1}, Sacrifice an artifact or creature: Makeshift Munitions deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private MakeshiftMunitions(final MakeshiftMunitions card) {
        super(card);
    }

    @Override
    public MakeshiftMunitions copy() {
        return new MakeshiftMunitions(this);
    }
}
