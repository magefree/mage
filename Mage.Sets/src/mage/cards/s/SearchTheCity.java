package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SearchTheCity extends CardImpl {

    public SearchTheCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        // When Search the City enters the battlefield, exile the top five cards of your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchTheCityExileEffect()));

        // Whenever you play a card with the same name as one of the exiled cards, you may put one of those cards with that name into its owner's hand. Then if there are no cards exiled with Search the City, sacrifice it. If you do, take an extra turn after this one.
        this.addAbility(new SearchTheCityTriggeredAbility());

    }

    private SearchTheCity(final SearchTheCity card) {
        super(card);
    }

    @Override
    public SearchTheCity copy() {
        return new SearchTheCity(this);
    }
}

class SearchTheCityExileEffect extends OneShotEffect {

    public SearchTheCityExileEffect() {
        super(Outcome.DrawCard);
        staticText = "exile the top five cards of your library";
    }

    public SearchTheCityExileEffect(final SearchTheCityExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            // move cards from library to exile
            for (int i = 0; i < 5; i++) {
                if (player.getLibrary().hasCards()) {
                    Card topCard = player.getLibrary().getFromTop(game);
                    topCard.moveToExile(source.getSourceId(), "Cards exiled by Search the City", source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SearchTheCityExileEffect copy() {
        return new SearchTheCityExileEffect(this);
    }
}

class SearchTheCityTriggeredAbility extends TriggeredAbilityImpl {

    public SearchTheCityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SearchTheCityExiledCardToHandEffect(), true);
        setTriggerPhrase("Whenever you play a card with the same name as one of the exiled cards, " );
    }

    public SearchTheCityTriggeredAbility(final SearchTheCityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST || event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }
        String cardName = "";
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                cardName = spell.getName();
            }
        }
        if (event.getType() == GameEvent.EventType.LAND_PLAYED) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                cardName = card.getName();
            }
        }
        if (cardName.isEmpty()) {
            return false;
        }
        ExileZone searchTheCityExileZone = game.getExile().getExileZone(this.getSourceId());
        FilterCard filter = new FilterCard();
        filter.add(new NamePredicate(cardName));

        if (searchTheCityExileZone.count(filter, game) == 0) {
            return false;
        }
        this.getEffects().get(0).setValue("cardName", cardName);
        return true;
    }

    @Override
    public SearchTheCityTriggeredAbility copy() {
        return new SearchTheCityTriggeredAbility(this);
    }
}

class SearchTheCityExiledCardToHandEffect extends OneShotEffect {

    public SearchTheCityExiledCardToHandEffect() {
        super(Outcome.DrawCard);
        staticText = "you may put one of those cards with that name into its owner's hand. Then if there are no cards exiled with {this}, sacrifice it. If you do, take an extra turn after this one";
    }

    public SearchTheCityExiledCardToHandEffect(final SearchTheCityExiledCardToHandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) this.getValue("cardName");
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ExileZone searchTheCityExileZone = game.getExile().getExileZone(source.getSourceId());
        if (cardName != null
                && searchTheCityExileZone != null) {
            for (Card card : searchTheCityExileZone.getCards(game)) {
                if (CardUtil.haveSameNames(card, cardName, game)) {
                    if (controller.moveCards(card, Zone.HAND, source, game)) {
                        game.informPlayers("Search the City: put " + card.getName() + " into owner's hand");
                    }
                    searchTheCityExileZone.remove(card);
                    if (searchTheCityExileZone.isEmpty()) {
                        Permanent permanent = game.getPermanent(source.getSourceId());
                        if (permanent != null) {
                            permanent.sacrifice(source, game);
                            // extra turn
                            game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), false));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public SearchTheCityExiledCardToHandEffect copy() {
        return new SearchTheCityExiledCardToHandEffect(this);
    }
}
