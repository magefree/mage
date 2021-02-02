package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class Gamekeeper extends CardImpl {

    public Gamekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Gamekeeper dies, you may exile it. If you do, reveal cards from the top of your library until you reveal a creature card. Put that card onto the battlefield and put all other cards revealed this way into your graveyard.
        Ability ability = new DiesSourceTriggeredAbility(new DoIfCostPaid(new RevealCardsFromLibraryUntilEffect(StaticFilters.FILTER_CARD_CREATURE, Zone.BATTLEFIELD, Zone.GRAVEYARD), new ExileSourceFromGraveCost(), "Exile to reveal cards from the top of your library until you reveal a creature card?"), false);
        this.addAbility(ability);
    }

    private Gamekeeper(final Gamekeeper card) {
        super(card);
    }

    @Override
    public Gamekeeper copy() {
        return new Gamekeeper(this);
    }
}
