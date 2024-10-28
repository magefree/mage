package mage.cards.t;

import java.util.*;

import mage.MageIdentifier;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.RandomUtil;

/**
 *
 * @author jimga150
 */
public final class TheRuinousPowers extends CardImpl {

    public TheRuinousPowers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{R}");
        

        // At the beginning of your upkeep, choose an opponent at random. Exile the top card of that player's library.
        // Until end of turn, you may play that card and you may spend mana as though it were mana of any color to cast it.
        // When you cast a spell this way, its owner loses life equal to its mana value.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new TheRuinousPowersEffect(), false)
                .setIdentifier(MageIdentifier.TheRuinousPowersAlternateCast);
        this.addAbility(ability);
    }

    private TheRuinousPowers(final TheRuinousPowers card) {
        super(card);
    }

    @Override
    public TheRuinousPowers copy() {
        return new TheRuinousPowers(this);
    }
}

// Based on YouFindSomePrisonersEffect
class TheRuinousPowersEffect extends OneShotEffect {

    TheRuinousPowersEffect() {
        super(Outcome.Benefit);
        staticText = "choose an opponent at random. Exile the top card of that player's library. Until end of turn, " +
                "you may play that card and you may spend mana as though it were mana of any color to cast it. " +
                "When you cast a spell this way, its owner loses life equal to its mana value.";
    }

    private TheRuinousPowersEffect(final TheRuinousPowersEffect effect) {
        super(effect);
    }

    @Override
    public TheRuinousPowersEffect copy() {
        return new TheRuinousPowersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null){
            return false;
        }
        List<UUID> opponents = new ArrayList<>(game.getOpponents(source.getControllerId()));
        if (opponents.isEmpty()) {
            return false;
        }
        Player opponent = game.getPlayer(opponents.get(RandomUtil.nextInt(opponents.size())));
        if (opponent == null) {
            return false;
        }
        Card card = opponent.getLibrary().getFromTop(game);
        player.moveCards(card, Zone.EXILED, source, game);
        if (card != null) {
            CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfTurn, true);
            game.addDelayedTriggeredAbility(new TheRuinousPowersTriggeredAbility(card.getId()), source);
        }
        return true;
    }
}

// Based on FiresOfMountDoomDelayedTriggeredAbility
class TheRuinousPowersTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID cardId;

    public TheRuinousPowersTriggeredAbility(UUID cardId) {
        super(null, Duration.EndOfTurn);
        this.cardId = cardId;
    }

    private TheRuinousPowersTriggeredAbility(final TheRuinousPowersTriggeredAbility ability) {
        super(ability);
        this.cardId = ability.cardId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(cardId)){
            return false;
        }
        if (!event.hasApprovingIdentifier(MageIdentifier.TheRuinousPowersAlternateCast)){
            return false;
        }
        Card card = game.getCard(cardId);
        if (card == null){
            return false;
        }
        Player owner = game.getPlayer(card.getOwnerId());
        if (owner == null){
            return false;
        }
        owner.loseLife(card.getManaValue(), game, this, false);
        return true;
    }

    @Override
    public TheRuinousPowersTriggeredAbility copy() {
        return new TheRuinousPowersTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you cast a spell this way, its owner loses life equal to its mana value.";
    }
}
