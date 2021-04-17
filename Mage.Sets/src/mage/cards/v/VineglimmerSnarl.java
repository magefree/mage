package mage.cards.v;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VineglimmerSnarl extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Forest or Island card from your hand");

    static {
        filter.add(Predicates.or(
                SubType.FOREST.getPredicate(),
                SubType.ISLAND.getPredicate()
        ));
    }

    public VineglimmerSnarl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // As Vineglimmer Snarl enters the battlefield, you may reveal a Forest or Island card from your hand. If you don't, Vineglimmer Snarl enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(
                        new RevealTargetFromHandCost(new TargetCardInHand(filter))
                ), "you may reveal a Forest or Island card from your hand. " +
                "If you don't, {this} enters the battlefield tapped"
        ));

        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private VineglimmerSnarl(final VineglimmerSnarl card) {
        super(card);
    }

    @Override
    public VineglimmerSnarl copy() {
        return new VineglimmerSnarl(this);
    }
}
