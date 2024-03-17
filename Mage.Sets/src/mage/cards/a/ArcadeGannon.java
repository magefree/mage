package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;

/**
 *
 * @author anonymous
 */
public final class ArcadeGannon extends CardImpl {

    public ArcadeGannon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Draw a card, then discard a card. Put a quest counter on Arcade Gannon.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(), new TapSourceCost()));


        // For Auld Lang Syne -- Once during each of your turns, you may cast an artifact or Human spell from your graveyard with mana value less than or equal to the number of quest counters on Arcade Gannon.
    }

    private ArcadeGannon(final ArcadeGannon card) {
        super(card);
    }

    @Override
    public ArcadeGannon copy() {
        return new ArcadeGannon(this);
    }
}
