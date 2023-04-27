package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.replacement.YouControlYourOpponentsWhileSearchingReplacementEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.LibrarySearchedEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.game.permanent.Permanent;

/**
 * @author JayDi85
 */
public final class OppositionAgent extends CardImpl {

    public OppositionAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // You control your opponents while they’re searching their libraries.
        this.addAbility(new SimpleStaticAbility(new YouControlYourOpponentsWhileSearchingReplacementEffect()));

        // While an opponent is searching their library, they exile each card they find. You may play those cards for as long as they remain exiled, and you may spend mana as though it were mana of any color to cast them.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new OppositionAgentReplacementEffect()));

    }

    private OppositionAgent(final OppositionAgent card) {
        super(card);
    }

    @Override
    public OppositionAgent copy() {
        return new OppositionAgent(this);
    }
}

class OppositionAgentReplacementEffect extends ReplacementEffectImpl {

    public OppositionAgentReplacementEffect() {
        // Duration.WhileOnBattlefield won't work correctly here, so we check in applies
        super(Duration.EndOfGame, Outcome.Benefit);
        staticText = "While an opponent is searching their library, they exile each card they find. You may play "
                + "those cards for as long as they remain exiled, and you may spend mana as though it were mana "
                + "of any color to cast them";
    }

    OppositionAgentReplacementEffect(final OppositionAgentReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        LibrarySearchedEvent se = (LibrarySearchedEvent) event;

        // opponent is searching their library
        if (!event.getTargetId().equals(event.getPlayerId())) {
            return false;
        }

        Player targetPlayer = game.getPlayer(event.getTargetId());
        if (targetPlayer == null) {
            return false;
        }

        Set<Card> cardsToExile = se.getSearchedTarget().getTargets().stream()
                .map(id -> targetPlayer.getLibrary().getCard(id, game))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (cardsToExile.isEmpty()) {
            return false;
        }

        // exile each card they find
        targetPlayer.moveCards(cardsToExile, Zone.EXILED, source, game);
        cardsToExile.removeIf(card -> game.getState().getZone(card.getId()) != Zone.EXILED);
        if (cardsToExile.isEmpty()) {
            return false;
        }

        // remove exiled cards from library searched result
        cardsToExile.forEach(card -> se.getSearchedTarget().remove(card.getId()));

        // You may play those cards for as long as they remain exiled, and you may spend mana as though it were mana of any color to cast them
        for (Card card : cardsToExile) {
            // the source ability is tied to the effect so we need to keep it active to work correctly
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfGame, true);
        }

        // return false all the time
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LIBRARY_SEARCHED;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // Due to the nature of this effect, the "while on battlefield" part of the duration is checked here.
        // If Opposition Agent leaves the battlefield, its source ability (SimpleStaticAbility) will leave with it, discarding the play/anyMana effect with it
        // The exiled card must be playable even if Opposition Agent leaves the battlefield
        Permanent oppositionAgent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        return oppositionAgent != null
                && controller != null
                && game.isOpponent(controller, event.getPlayerId());
    }

    @Override
    public OppositionAgentReplacementEffect copy() {
        return new OppositionAgentReplacementEffect(this);
    }
}
