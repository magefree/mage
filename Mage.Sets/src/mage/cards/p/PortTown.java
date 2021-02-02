
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class PortTown extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Plains or Island card from your hand");

    static {
        filter.add(Predicates.or(SubType.PLAINS.getPredicate(),
                SubType.ISLAND.getPredicate()));
    }

    public PortTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // As Port Town enters the battlefield, you may reveal a Plains or Island card from your hand. If you don't, Port Town enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))),
                "you may reveal a Plains or Island card from your hand. If you don't, {this} enters the battlefield tapped"));

        // {T}: Add {W} or {U}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private PortTown(final PortTown card) {
        super(card);
    }

    @Override
    public PortTown copy() {
        return new PortTown(this);
    }
}
