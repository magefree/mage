package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetOpponentOrPlaneswalker;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandraFireArtisan extends CardImpl {

    public ChandraFireArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.setStartingLoyalty(4);

        // Whenever one or more loyalty counters are removed from Chandra, Fire Artisan, she deals that much damage to target opponent or planeswalker.
        this.addAbility(new ChandraFireArtisanTriggeredAbility());

        // +1: Exile the top card of your library. You may play it this turn.
        this.addAbility(new LoyaltyAbility(new ChandraFireArtisanEffect(false), 1));

        // -7: Exile the top seven cards of your library. You may play them this turn.
        this.addAbility(new LoyaltyAbility(new ChandraFireArtisanEffect(true), -7));
    }

    private ChandraFireArtisan(final ChandraFireArtisan card) {
        super(card);
    }

    @Override
    public ChandraFireArtisan copy() {
        return new ChandraFireArtisan(this);
    }
}

class ChandraFireArtisanTriggeredAbility extends TriggeredAbilityImpl {

    ChandraFireArtisanTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addTarget(new TargetOpponentOrPlaneswalker());
    }

    private ChandraFireArtisanTriggeredAbility(final ChandraFireArtisanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getAmount() == 0 || !event.getData().equals(CounterType.LOYALTY.getName())
                || !event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new DamageTargetEffect(event.getAmount()));
        return true;
    }

    @Override
    public ChandraFireArtisanTriggeredAbility copy() {
        return new ChandraFireArtisanTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more loyalty counters are removed from {this}, " +
                "she deals that much damage to target opponent or planeswalker.";
    }
}

class ChandraFireArtisanEffect extends OneShotEffect {

    private final boolean exileSeven;

    ChandraFireArtisanEffect(boolean exileSeven) {
        super(Outcome.Detriment);
        this.exileSeven = exileSeven;
        if (exileSeven) {
            staticText = "Exile the top seven cards of your library. You may play them this turn.";
        } else {
            staticText = "Exile the top card of your library. You may play it this turn";
        }
    }

    private ChandraFireArtisanEffect(final ChandraFireArtisanEffect effect) {
        super(effect);
        this.exileSeven = effect.exileSeven;
    }

    @Override
    public ChandraFireArtisanEffect copy() {
        return new ChandraFireArtisanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, (exileSeven ? 7 : 1)));
        controller.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards.getCards(game)) {
            if (card == null) {
                continue;
            }
            ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}