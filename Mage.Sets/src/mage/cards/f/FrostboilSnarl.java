package mage.cards.f;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
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
public final class FrostboilSnarl extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Island or Mountain card from your hand");

    static {
        filter.add(Predicates.or(
                SubType.ISLAND.getPredicate(),
                SubType.MOUNTAIN.getPredicate()
        ));
    }

    public FrostboilSnarl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // As Frostboil Snarl enters the battlefield, you may reveal an Island or Mountain card from your hand. If you don't, Frostboil Snarl enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(
                        new RevealTargetFromHandCost(new TargetCardInHand(filter))
                ), "you may reveal an Island or Mountain card from your hand. " +
                "If you don't, {this} enters the battlefield tapped"
        ));

        // {T}: Add {U} or {R}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private FrostboilSnarl(final FrostboilSnarl card) {
        super(card);
    }

    @Override
    public FrostboilSnarl copy() {
        return new FrostboilSnarl(this);
    }
}
