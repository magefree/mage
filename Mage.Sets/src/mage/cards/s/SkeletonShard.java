package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SkeletonShard extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact creature card from your graveyard");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }

    public SkeletonShard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {tap} or {B}, {tap}: Return target artifact creature card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToHandTargetEffect(),
                new OrCost(
                        "{3}, {T} or {B}, {T}", new CompositeCost(new GenericManaCost(3), new TapSourceCost(), "{3}, {T}"),
                        new CompositeCost(new ManaCostsImpl<>("{B}"), new TapSourceCost(), "{B}, {T}")
                )
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private SkeletonShard(final SkeletonShard card) {
        super(card);
    }

    @Override
    public SkeletonShard copy() {
        return new SkeletonShard(this);
    }
}
