package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiottKingOfTheDwarves extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("Dwarf you control enters and whenever an Equipment you control");

    static {
        filter.add(Predicates.or(
                SubType.DWARF.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    public GiottKingOfTheDwarves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever Giott or another Dwarf you control enters and whenever an Equipment you control enters, you may discard a card. If you do, draw a card.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        ), filter, false, false));
    }

    private GiottKingOfTheDwarves(final GiottKingOfTheDwarves card) {
        super(card);
    }

    @Override
    public GiottKingOfTheDwarves copy() {
        return new GiottKingOfTheDwarves(this);
    }
}
