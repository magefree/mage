package mage.cards.t;

import mage.MageInt;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TolarianSerpent extends CardImpl {

    public TolarianSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // At the beginning of your upkeep, put the top seven cards of your library into your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MillCardsControllerEffect(7)));

    }

    private TolarianSerpent(final TolarianSerpent card) {
        super(card);
    }

    @Override
    public TolarianSerpent copy() {
        return new TolarianSerpent(this);
    }
}
