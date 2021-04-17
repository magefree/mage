package mage.cards.f;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
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
public final class FurycalmSnarl extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Mountain or Plains card from your hand");

    static {
        filter.add(Predicates.or(
                SubType.MOUNTAIN.getPredicate(),
                SubType.PLAINS.getPredicate()
        ));
    }

    public FurycalmSnarl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");


        // As Furycalm Snarl enters the battlefield, you may reveal a Mountain or Plains card from your hand. If you don't, Furycalm Snarl enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(
                        new RevealTargetFromHandCost(new TargetCardInHand(filter))
                ), "you may reveal a Mountain or Plains card from your hand. " +
                "If you don't, {this} enters the battlefield tapped"
        ));

        // {T}: Add {R} or {W}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private FurycalmSnarl(final FurycalmSnarl card) {
        super(card);
    }

    @Override
    public FurycalmSnarl copy() {
        return new FurycalmSnarl(this);
    }
}
