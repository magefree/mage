package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.DiscardedCardsEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToluzCleverConductor extends CardImpl {

    public ToluzCleverConductor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W/U}{U}{U/B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Toluz, Clever Conductor enters the battlefield, it connives.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConniveSourceEffect()));

        // Whenever you discard one or more cards, exile them from your graveyard.
        this.addAbility(new ToluzCleverConductorTriggeredAbility());

        // When Toluz, Clever Conductor dies, put the cards exiled with it into their owner's hand.
        this.addAbility(new DiesSourceTriggeredAbility(new ToluzCleverConductorEffect()));
    }

    private ToluzCleverConductor(final ToluzCleverConductor card) {
        super(card);
    }

    @Override
    public ToluzCleverConductor copy() {
        return new ToluzCleverConductor(this);
    }
}

class ToluzCleverConductorTriggeredAbility extends TriggeredAbilityImpl {

    ToluzCleverConductorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTargetForSourceEffect());
    }

    private ToluzCleverConductorTriggeredAbility(final ToluzCleverConductorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ToluzCleverConductorTriggeredAbility copy() {
        return new ToluzCleverConductorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARDS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        DiscardedCardsEvent dEvent = (DiscardedCardsEvent) event;
        Cards cards = new CardsImpl(dEvent.getDiscardedCards());
        cards.retainZone(Zone.GRAVEYARD, game);
        this.getEffects().setTargetPointer(new FixedTargets(cards, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you discard one or more cards, exile them from your graveyard.";
    }
}

class ToluzCleverConductorEffect extends OneShotEffect {

    ToluzCleverConductorEffect() {
        super(Outcome.Benefit);
        staticText = "put the cards exiled with it into their owner's hand";
    }

    private ToluzCleverConductorEffect(final ToluzCleverConductorEffect effect) {
        super(effect);
    }

    @Override
    public ToluzCleverConductorEffect copy() {
        return new ToluzCleverConductorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return player != null
                && exileZone != null
                && !exileZone.isEmpty()
                && player.moveCards(exileZone, Zone.HAND, source, game);
    }
}
