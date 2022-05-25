package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class GrinningTotem extends CardImpl {

    public GrinningTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {tap}, Sacrifice Grinning Totem: Search target opponent's library for a card and exile it. Then that player shuffles their library.
        // Until the beginning of your next upkeep, you may play that card.
        // At the beginning of your next upkeep, if you haven't played it, put it into its owner's graveyard.
        Ability ability = new SimpleActivatedAbility(new GrinningTotemSearchAndExileEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private GrinningTotem(final GrinningTotem card) {
        super(card);
    }

    @Override
    public GrinningTotem copy() {
        return new GrinningTotem(this);
    }
}

class GrinningTotemSearchAndExileEffect extends OneShotEffect {

    public GrinningTotemSearchAndExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search target opponent's library for a card and exile it. Then that player shuffles. " +
                "Until the beginning of your next upkeep, you may play that card. " +
                "At the beginning of your next upkeep, if you haven't played it, put it into its owner's graveyard";
    }

    public GrinningTotemSearchAndExileEffect(final GrinningTotemSearchAndExileEffect effect) {
        super(effect);
    }

    @Override
    public GrinningTotemSearchAndExileEffect copy() {
        return new GrinningTotemSearchAndExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        if (you == null || targetOpponent == null) {
            return false;
        }
        TargetCardInLibrary targetCard = new TargetCardInLibrary();
        you.searchLibrary(targetCard, source, game, targetOpponent.getId());
        Card card = targetOpponent.getLibrary().getCard(targetCard.getFirstTarget(), game);
        if (card != null) {
            UUID exileZoneId = CardUtil.getCardExileZoneId(game, source);
            you.moveCardsToExile(card, source, game, true, exileZoneId, CardUtil.getSourceName(game, source));
            game.addEffect(new GrinningTotemMayPlayEffect().setTargetPointer(new FixedTarget(card.getId())), source);
            game.addDelayedTriggeredAbility(new GrinningTotemDelayedTriggeredAbility(exileZoneId), source);
        }
        targetOpponent.shuffleLibrary(source, game);
        return true;
    }

}

class GrinningTotemMayPlayEffect extends AsThoughEffectImpl {

    private boolean sameStep = true;

    public GrinningTotemMayPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the beginning of your next upkeep, you may play that card.";
    }

    public GrinningTotemMayPlayEffect(final GrinningTotemMayPlayEffect effect) {
        super(effect);
        this.sameStep = effect.sameStep;
    }

    @Override
    public GrinningTotemMayPlayEffect copy() {
        return new GrinningTotemMayPlayEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UPKEEP) {
            return !sameStep && game.isActivePlayer(source.getControllerId()) || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving();
        } else {
            sameStep = false;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && sourceId.equals(getTargetPointer().getFirst(game, source));
    }

}

class GrinningTotemDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID exileZoneId;

    public GrinningTotemDelayedTriggeredAbility(UUID exileZoneId) {
        super(new GrinningTotemPutIntoGraveyardEffect(exileZoneId));
        this.exileZoneId = exileZoneId;
    }

    public GrinningTotemDelayedTriggeredAbility(final GrinningTotemDelayedTriggeredAbility ability) {
        super(ability);
        this.exileZoneId = ability.exileZoneId;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
        return exileZone != null && !exileZone.getCards(game).isEmpty();
    }

    @Override
    public GrinningTotemDelayedTriggeredAbility copy() {
        return new GrinningTotemDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "At the beginning of your next upkeep, if you haven't played it, put it into its owner's graveyard.";
    }
}

class GrinningTotemPutIntoGraveyardEffect extends OneShotEffect {

    private final UUID exileZoneId;

    public GrinningTotemPutIntoGraveyardEffect(UUID exileZoneId) {
        super(Outcome.Detriment);
        this.exileZoneId = exileZoneId;
    }

    public GrinningTotemPutIntoGraveyardEffect(final GrinningTotemPutIntoGraveyardEffect effect) {
        super(effect);
        this.exileZoneId = effect.exileZoneId;
    }

    @Override
    public GrinningTotemPutIntoGraveyardEffect copy() {
        return new GrinningTotemPutIntoGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone zone = game.getExile().getExileZone(exileZoneId);
        if (controller == null || zone == null) {
            return false;
        }

        return controller.moveCards(zone, Zone.GRAVEYARD, source, game);
    }
}
