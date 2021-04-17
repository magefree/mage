package mage.cards.n;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
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
public final class NecroblossomSnarl extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Swamp or Forest card from your hand");

    static {
        filter.add(Predicates.or(
                SubType.SWAMP.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    public NecroblossomSnarl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // As Necroblossom Snarl enters the battlefield, you may reveal a Swamp or Forest card from your hand. If you don't, Necroblossom Snarl enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(
                        new RevealTargetFromHandCost(new TargetCardInHand(filter))
                ), "you may reveal a Swamp or Forest card from your hand. " +
                "If you don't, {this} enters the battlefield tapped"
        ));

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private NecroblossomSnarl(final NecroblossomSnarl card) {
        super(card);
    }

    @Override
    public NecroblossomSnarl copy() {
        return new NecroblossomSnarl(this);
    }
}
