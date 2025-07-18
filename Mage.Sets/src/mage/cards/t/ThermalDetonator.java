package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureOrPlayer;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author NinthWorld
 */
public final class ThermalDetonator extends CardImpl {

    private static final FilterCreatureOrPlayer filter = new FilterCreatureOrPlayer("creature without spaceflight or target player");

    static {
        filter.getPermanentFilter().add(Predicates.not(new AbilityPredicate(SpaceflightAbility.class)));
    }

    public ThermalDetonator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {2}, Sacrifice Thermal Detonator: Thermal Detonator deals 2 damage to target creature without spaceflight or target player.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new ManaCostsImpl<>("{2}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanentOrPlayer(filter));
        this.addAbility(ability);
    }

    private ThermalDetonator(final ThermalDetonator card) {
        super(card);
    }

    @Override
    public ThermalDetonator copy() {
        return new ThermalDetonator(this);
    }
}
