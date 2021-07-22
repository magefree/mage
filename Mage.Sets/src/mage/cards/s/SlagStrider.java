package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlagStrider extends CardImpl {

    public SlagStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // {1}, Sacrifice an artifact: Slag Strider deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN)
        ));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SlagStrider(final SlagStrider card) {
        super(card);
    }

    @Override
    public SlagStrider copy() {
        return new SlagStrider(this);
    }
}
