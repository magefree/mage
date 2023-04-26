package mage.cards.i;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
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
        ability = new SimpleStaticAbility(new IntellectDevourerPlayFromExileEffect());
        ability.addEffect(new IntellectDevourerManaEffect());
        this.addAbility(ability.withFlavorWord("Body Thief"));
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

        Boolean applied = false;
        // for storing each card to exile
        Map<UUID, Cards> cardsToExile = new HashMap<>();

        // Each player chooses a card to exile
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            if (opponent.getHand().size() > 0) {
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
            Cards cardsOpponentsChoseToExile = new CardsImpl();
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !cardsToExile.containsKey(opponentId)) {
                continue;
            }
            cardsOpponentsChoseToExile.addAll(cardsToExile.get(opponentId));
            opponent.moveCardsToExile(cardsOpponentsChoseToExile.getCards(game), source, game, false, exileZoneId, sourceObject.getIdName());
            Card thisCard = cardsOpponentsChoseToExile.getCards(game).iterator().next();
            game.getState().setValue(thisCard.getId().toString() + game.getState().getZoneChangeCounter(thisCard.getId()), exileZoneId);
            applied = true;
        }

        return applied;
    }
}

class IntellectDevourerPlayFromExileEffect extends AsThoughEffectImpl {

    IntellectDevourerPlayFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may play lands and cast spells from among cards exiled with {this}";
    }

    IntellectDevourerPlayFromExileEffect(final IntellectDevourerPlayFromExileEffect effect) {super(effect);}

    @Override
    public boolean apply(Game game, Ability source) {return true;}

    @Override
    public IntellectDevourerPlayFromExileEffect copy() {return new IntellectDevourerPlayFromExileEffect(this);}

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        MageObject sourceObject = source.getSourceObject(game);
        Card theCard = game.getCard(objectId);
        if (theCard == null) {
            return false;
        }
        objectId = theCard.getMainCard().getId(); // for split cards

        UUID exileZoneId = CardUtil.getExileZoneId(game, sourceObject.getId(), sourceObject.getZoneChangeCounter(game));
        ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
        if (exileZone == null) {
            return false;
        }
        // this check happens while the chosen card is in the exile zone
        if (exileZone.contains(objectId) && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            return card != null;
        }
        return false;
    }
}

class IntellectDevourerManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    IntellectDevourerManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If you cast a spell this way, you may spend mana as though it were mana of any color to cast it";
    }

    private IntellectDevourerManaEffect(final IntellectDevourerManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IntellectDevourerManaEffect copy() {
        return new IntellectDevourerManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        // this check occurs when the chosen card is outside of the exile zone, so the exileId must be retrieved from history
        MageObject sourceObject = source.getSourceObject(game);
        Card theCard = game.getCard(objectId);
        if (theCard == null) {
            return false;
        }
        objectId = theCard.getMainCard().getId(); // for split cards

        // get the current zcc of the chosen exiled card
        int zcc = game.getState().getZoneChangeCounter(theCard.getId());
        // retrieve the exileId of this source card
        UUID exileId = CardUtil.getExileZoneId(game, sourceObject.getId(), sourceObject.getZoneChangeCounter(game));
        // retrieve the exileId stored on the chosen exiled card (note that we subtract 1 from it due to it being moved from the exile zone to the stack
        UUID storedExileIdOfTheCard = (UUID) game.getState().getValue(theCard.getId().toString() + (zcc - 1));

        if (objectId != null
                && game.getState().getZone(objectId) == Zone.STACK
                && exileId == storedExileIdOfTheCard
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            return card != null;
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

class IntellectDevourerReturnCardsAbility extends DelayedTriggeredAbility {

    public IntellectDevourerReturnCardsAbility() {
        super(new IntellectDevourerReturnExiledCardEffect(), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    public IntellectDevourerReturnCardsAbility(final IntellectDevourerReturnCardsAbility ability) {super(ability);}

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

    public IntellectDevourerReturnExiledCardEffect(final IntellectDevourerReturnExiledCardEffect effect) {super(effect);}

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
