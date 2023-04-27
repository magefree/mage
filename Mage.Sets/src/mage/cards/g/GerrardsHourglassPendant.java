package mage.cards.g;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

public final class GerrardsHourglassPendant extends CardImpl {

    public GerrardsHourglassPendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        addSuperType(SuperType.LEGENDARY);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // If a player would begin an extra turn, that player skips that turn instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GerrardsHourglassPendantSkipExtraTurnsEffect()));

        // {4}, {T}, Exile Gerrard's Hourglass Pendant: Return to the battlefield tapped all artifact, creature,
        // enchantment, and land cards in your graveyard that were put there from the battlefield this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GerrardsHourglassPendantReanimateEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        ability.addWatcher(new CardsPutIntoGraveyardWatcher());
        this.addAbility(ability);
    }

    private GerrardsHourglassPendant(final GerrardsHourglassPendant card) {
        super(card);
    }

    @Override
    public GerrardsHourglassPendant copy() {
        return new GerrardsHourglassPendant(this);
    }
}

class GerrardsHourglassPendantSkipExtraTurnsEffect extends ReplacementEffectImpl {

    public GerrardsHourglassPendantSkipExtraTurnsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "If a player would begin an extra turn, that player skips that turn instead";
    }

    public GerrardsHourglassPendantSkipExtraTurnsEffect(final GerrardsHourglassPendantSkipExtraTurnsEffect effect) {
        super(effect);
    }

    @Override
    public GerrardsHourglassPendantSkipExtraTurnsEffect copy() {
        return new GerrardsHourglassPendantSkipExtraTurnsEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null) {
            game.informPlayers(sourceObject.getLogName() + ": Extra turn of " + player.getLogName() + " skipped");
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXTRA_TURN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

}

class GerrardsHourglassPendantReanimateEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.CREATURE.getPredicate(),
            CardType.ENCHANTMENT.getPredicate(),
            CardType.LAND.getPredicate()));
    }

    GerrardsHourglassPendantReanimateEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Return to the battlefield tapped all artifact, creature, enchantment, and land cards in your graveyard that were put there from the battlefield this turn";
    }

    private GerrardsHourglassPendantReanimateEffect(final GerrardsHourglassPendantReanimateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.moveCards(player.getGraveyard().getCards(
                filter, source.getControllerId(), source, game
        ), Zone.BATTLEFIELD, source, game, true, false, false, null);
    }

    @Override
    public GerrardsHourglassPendantReanimateEffect copy() {
        return new GerrardsHourglassPendantReanimateEffect(this);
    }
}
