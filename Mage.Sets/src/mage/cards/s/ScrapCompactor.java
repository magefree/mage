package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScrapCompactor extends CardImpl {

    public ScrapCompactor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {3}, {T}, Sacrifice this artifact: It deals 3 damage to target creature.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(3, "it"), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {6}, {T}, Sacrifice this artifact: Destroy target creature or Vehicle.
        ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_VEHICLE));
        this.addAbility(ability);
    }

    private ScrapCompactor(final ScrapCompactor card) {
        super(card);
    }

    @Override
    public ScrapCompactor copy() {
        return new ScrapCompactor(this);
    }
}
