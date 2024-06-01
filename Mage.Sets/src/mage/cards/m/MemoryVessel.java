package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MemoryVessel extends CardImpl {

    public MemoryVessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}{R}");

        // {T}, Exile Memory Vessel: Each player exiles the top seven cards of their library. Until your next turn, players may play cards they exiled this way, and they can't play cards from their hand. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new MemoryVesselExileEffect(), new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        ability.addEffect(new MemoryVesselPreventionEffect());
        this.addAbility(ability);
    }

    private MemoryVessel(final MemoryVessel card) {
        super(card);
    }

    @Override
    public MemoryVessel copy() {
        return new MemoryVessel(this);
    }
}

class MemoryVesselExileEffect extends OneShotEffect {

    MemoryVesselExileEffect() {
        super(Outcome.Benefit);
        staticText = "each player exiles the top seven cards of their library. " +
                "Until your next turn, players may play cards they exiled this way";
    }

    private MemoryVesselExileEffect(final MemoryVesselExileEffect effect) {
        super(effect);
    }

    @Override
    public MemoryVesselExileEffect copy() {
        return new MemoryVesselExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            Set<Card> cards = player.getLibrary().getTopCards(game, 7);
            player.moveCards(cards, Zone.EXILED, source, game);
            for (Card card : cards) {
                CardUtil.makeCardPlayable(
                        game, source, card, false, Duration.UntilYourNextTurn,
                        false, playerId, null
                );
            }
        }
        return true;
    }
}

class MemoryVesselPreventionEffect extends ContinuousRuleModifyingEffectImpl {

    public MemoryVesselPreventionEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Benefit);
        staticText = ", and they can't play cards from their hand";
    }

    private MemoryVesselPreventionEffect(final MemoryVesselPreventionEffect effect) {
        super(effect);
    }

    @Override
    public MemoryVesselPreventionEffect copy() {
        return new MemoryVesselPreventionEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL
                || event.getType() == GameEvent.EventType.PLAY_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        return card != null && Zone.HAND.match(game.getState().getZone(card.getId()));
    }
}
