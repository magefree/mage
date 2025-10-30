package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BebopAndRocksteady extends CardImpl {

    public BebopAndRocksteady(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/G}{B/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Whenever Bebop & Rocksteady attacks or blocks, sacrifice a permanent unless you discard a card.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new DoIfCostPaid(
                null, new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT, 1, null),
                new DiscardCardCost(), false), false
        ));
    }

    private BebopAndRocksteady(final BebopAndRocksteady card) {
        super(card);
    }

    @Override
    public BebopAndRocksteady copy() {
        return new BebopAndRocksteady(this);
    }
}
