package mage.cards.i;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class IntellectDevourer extends CardImpl {

    public IntellectDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Devour Intellect — When Intellect Devourer enters the battlefield, each opponent exiles a card from their hand until Intellect Devourer leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new IntellectDevourerExileEffect());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new IntellectDevourerReturnCardsAbility()));
        this.addAbility(ability.withFlavorWord("Devour Intellect"));

        // Body Thief — You may play lands and cast spells from among cards exiled with Intellect Devourer.
        // If you cast a spell this way, you may spend mana as though it were mana of any color to cast it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect(
                "You may play lands and cast spells from among cards exiled with {this}. If you cast " +
                        "a spell this way, you may spend mana as though it were mana of any color to cast it"
        )).withFlavorWord("Body Thief"));
    }

    private IntellectDevourer(final IntellectDevourer card) {
        super(card);
    }

    @Override
    public IntellectDevourer copy() {
        return new IntellectDevourer(this);
    }
}

class IntellectDevourerExileEffect extends OneShotEffect {

    IntellectDevourerExileEffect() {
        super(Outcome.Exile);
        this.staticText = "each opponent exiles a card from their hand until {this} leaves the battlefield";
    }

    private IntellectDevourerExileEffect(final IntellectDevourerExileEffect effect) {super(effect);}

    @Override
    public IntellectDevourerExileEffect copy() {return new IntellectDevourerExileEffect(this);}

    @Override
    public boolean apply(Game game, Ability source) {
        // if Intellect Devourer dies before this ability resolves, it fizzles with nothing being exiled
        if (game.getState().getZone(source.getSourceId()) != Zone.BATTLEFIELD) {
            return false;
        }

        boolean applied = false;
        // for storing each card to exile
        Map<UUID, Cards> cardsToExile = new HashMap<>();

        // Each player chooses a card to exile
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            if (!opponent.getHand().isEmpty()) {
                Target target = new TargetCardInHand(1, new FilterCard());
                target.setRequired(true);
                if (opponent.chooseTarget(Outcome.Exile, target, source, game)) {
                    Cards cards = new CardsImpl(target.getTargets());
                    cardsToExile.put(opponentId, cards);
                }
            } else {
                cardsToExile.put(opponentId, new CardsImpl());
            }
        }

        // Exile all chosen cards at the same time
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null|| sourceObject == null) {
            return false;
        }
        UUID exileZoneId = CardUtil.getExileZoneId(game, sourceObject.getId(), sourceObject.getZoneChangeCounter(game));

        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Cards opponentCardsToExile = new CardsImpl();
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !cardsToExile.containsKey(opponentId) || cardsToExile.get(opponentId).isEmpty()) {
                continue;
            }
            opponentCardsToExile.addAll(cardsToExile.get(opponentId));
            opponent.moveCardsToExile(opponentCardsToExile.getCards(game), source, game, false, exileZoneId, sourceObject.getIdName());
            Card thisCard = opponentCardsToExile.getCards(game).iterator().next();
            game.getState().setValue(thisCard.getId().toString() + game.getState().getZoneChangeCounter(thisCard.getId()), exileZoneId);
            CardUtil.makeCardPlayable(game, source, thisCard, Duration.Custom, true, source.getControllerId(), null);
            applied = true;
        }

        return applied;
    }
}

class IntellectDevourerReturnCardsAbility extends DelayedTriggeredAbility {

    public IntellectDevourerReturnCardsAbility() {
        super(new IntellectDevourerReturnExiledCardEffect(), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    private IntellectDevourerReturnCardsAbility(final IntellectDevourerReturnCardsAbility ability) {super(ability);}

    @Override
    public IntellectDevourerReturnCardsAbility copy() {return new IntellectDevourerReturnCardsAbility(this);}

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }
}

class IntellectDevourerReturnExiledCardEffect extends OneShotEffect {

    public IntellectDevourerReturnExiledCardEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return exiled cards to their owners' hands";
    }

    private IntellectDevourerReturnExiledCardEffect(final IntellectDevourerReturnExiledCardEffect effect) {super(effect);}

    @Override
    public IntellectDevourerReturnExiledCardEffect copy() {return new IntellectDevourerReturnExiledCardEffect(this);}

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (exile != null && sourcePermanent != null) {
                controller.moveCards(exile, Zone.HAND, source, game);
                return true;
            }
        }
        return false;
    }
}
