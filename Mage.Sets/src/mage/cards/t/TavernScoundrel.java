package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.FlipCoinEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.CoinFlippedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TavernScoundrel extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public TavernScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you win a coin flip, create two Treasure tokens.
        this.addAbility(new TavernScoundrelTriggeredAbility());

        // {1}, {T}, Sacrifice another permanent: Flip a coin.
        Ability ability = new SimpleActivatedAbility(new FlipCoinEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private TavernScoundrel(final TavernScoundrel card) {
        super(card);
    }

    @Override
    public TavernScoundrel copy() {
        return new TavernScoundrel(this);
    }
}

class TavernScoundrelTriggeredAbility extends TriggeredAbilityImpl {

    TavernScoundrelTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken(), 2), false);
    }

    private TavernScoundrelTriggeredAbility(final TavernScoundrelTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TavernScoundrelTriggeredAbility copy() {
        return new TavernScoundrelTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COIN_FLIPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        CoinFlippedEvent flipEvent = (CoinFlippedEvent) event;
        return isControlledBy(event.getPlayerId())
                && flipEvent.isWinnable()
                && flipEvent.wasWon();
    }

    @Override
    public String getRule() {
        return "Whenever you win a coin flip, create two Treasure tokens.";
    }
}
