package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class GraniteShard extends CardImpl {

    public GraniteShard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {tap} or {R}, {tap}: Granite Shard deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1),
                new OrCost(
                        "{3}, {T} or {R}, {T}", new CompositeCost(new GenericManaCost(3), new TapSourceCost(), "{3}, {T}"),
                        new CompositeCost(new ManaCostsImpl<>("{R}"), new TapSourceCost(), "{R}, {T}")
                )
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private GraniteShard(final GraniteShard card) {
        super(card);
    }

    @Override
    public GraniteShard copy() {
        return new GraniteShard(this);
    }
}
