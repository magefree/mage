package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Gamekeeper extends CardImpl {

    public Gamekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Gamekeeper dies, you may exile it. If you do, reveal cards from the top of your library until you reveal a creature card. Put that card onto the battlefield and put all other cards revealed this way into your graveyard.
        this.addAbility(new DiesSourceTriggeredAbility(new DoIfCostPaid(
                new RevealCardsFromLibraryUntilEffect(
                        StaticFilters.FILTER_CARD_CREATURE, PutCards.BATTLEFIELD, PutCards.GRAVEYARD
                ), new ExileSourceFromGraveCost()
        ), false));
    }

    private Gamekeeper(final Gamekeeper card) {
        super(card);
    }

    @Override
    public Gamekeeper copy() {
        return new Gamekeeper(this);
    }
}
