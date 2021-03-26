package mage.cards.s;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
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
public final class ShineshadowSnarl extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Plains or Swamp card from your hand");

    static {
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.SWAMP.getPredicate()
        ));
    }

    public ShineshadowSnarl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // As Shineshadow Snarl enters the battlefield, you may reveal a Plains or Swamp card from your hand. If you don't, Shineshadow Snarl enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(
                        new RevealTargetFromHandCost(new TargetCardInHand(filter))
                ), "you may reveal a Plains or Swamp card from your hand. " +
                "If you don't, {this} enters the battlefield tapped"
        ));

        // {T}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private ShineshadowSnarl(final ShineshadowSnarl card) {
        super(card);
    }

    @Override
    public ShineshadowSnarl copy() {
        return new ShineshadowSnarl(this);
    }
}
