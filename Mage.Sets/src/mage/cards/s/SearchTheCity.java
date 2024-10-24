package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PlayCardTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    SearchTheCityExileEffect() {
        super(Outcome.DrawCard);
        staticText = "exile the top five cards of your library";
    }

    private SearchTheCityExileEffect(final SearchTheCityExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.moveCardsToExile(
                player.getLibrary().getTopCards(game, 5), source, game, true,
                CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source)
        );
    }

    @Override
    public SearchTheCityExileEffect copy() {
        return new SearchTheCityExileEffect(this);
    }
}

class SearchTheCityTriggeredAbility extends PlayCardTriggeredAbility {

    public SearchTheCityTriggeredAbility() {
        super(TargetController.YOU, Zone.BATTLEFIELD, new SearchTheCityExiledCardToHandEffect());
        setTriggerPhrase("Whenever you play a card with the same name as one of the exiled cards, ");
    }

    private SearchTheCityTriggeredAbility(final SearchTheCityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        MageObject object;
        switch (event.getType()) {
            case SPELL_CAST:
                object = game.getStack().getSpell(event.getTargetId());
                break;
            case LAND_PLAYED:
                object = game.getCard(event.getTargetId());
                break;
            default:
                object = null;
        }
        if (object == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, this));
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        Set<Card> cards = exileZone
                .getCards(game)
                .stream()
                .filter(card -> card.sharesName(object, game))
                .collect(Collectors.toSet());
        if (cards.isEmpty()) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTargets(cards, game));
        return true;
    }

    @Override
    public SearchTheCityTriggeredAbility copy() {
        return new SearchTheCityTriggeredAbility(this);
    }
}

class SearchTheCityExiledCardToHandEffect extends OneShotEffect {

    SearchTheCityExiledCardToHandEffect() {
        super(Outcome.DrawCard);
        staticText = "you may put one of those cards with that name into its owner's hand. Then if there are " +
                "no cards exiled with {this}, sacrifice it. If you do, take an extra turn after this one";
    }

    private SearchTheCityExiledCardToHandEffect(final SearchTheCityExiledCardToHandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        cards.retainZone(Zone.EXILED, game);
        TargetCard target = new TargetCardInExile(0, 1, StaticFilters.FILTER_CARD);
        controller.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            controller.moveCards(card, Zone.HAND, source, game);
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone != null && !exileZone.isEmpty()) {
            return true;
        }
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent != null && sourcePermanent.sacrifice(source, game)) {
            game.getState().getTurnMods().add(new TurnMod(source.getControllerId()).withExtraTurn());
        }
        return true;
    }

    @Override
    public SearchTheCityExiledCardToHandEffect copy() {
        return new SearchTheCityExiledCardToHandEffect(this);
    }
}
