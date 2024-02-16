package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.TotalCardsExiledOwnedManaValue;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.AshiokWickedManipulatorNightmareToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.CardsExiledThisTurnWatcher;

import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class AshiokWickedManipulator extends CardImpl {

    public AshiokWickedManipulator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ASHIOK);
        this.setStartingLoyalty(5);

        // If you would pay life while your library has at least that many cards in it, exile that many cards from the top of your library instead.
        this.addAbility(new SimpleStaticAbility(new AshiokWickedManipulatorReplacementEffect()));

        // +1: Look at the top two cards of your library. Exile one of them and put the other into your hand.
        this.addAbility(new LoyaltyAbility(
                new LookLibraryAndPickControllerEffect(2, 1, PutCards.EXILED, PutCards.HAND)
                        .setText("look at the top two cards of your library. Exile one of them and put the other into your hand"),
                1
        ));

        // -2: Create two 1/1 black Nightmare creature tokens with "At the beginning of combat on your turn, if a card was put into exile this turn, put a +1/+1 counter on this creature."
        this.addAbility(new LoyaltyAbility(
                new CreateTokenEffect(new AshiokWickedManipulatorNightmareToken(), 2),
                -2
        ), new CardsExiledThisTurnWatcher());

        // -7: Target player exiles the top X cards of their library, where X is the total mana value of cards you own in exile.
        Ability ability = new LoyaltyAbility(
                new ExileCardsFromTopOfLibraryTargetEffect(TotalCardsExiledOwnedManaValue.instance)
                        .setText("target player exiles the top X cards of their library, "
                                + "where X is the total mana value of cards you own in exile"),
                -7
        );
        ability.addTarget(new TargetPlayer());
        ability.addHint(TotalCardsExiledOwnedManaValue.getHint());
        this.addAbility(ability);
    }

    private AshiokWickedManipulator(final AshiokWickedManipulator card) {
        super(card);
    }

    @Override
    public AshiokWickedManipulator copy() {
        return new AshiokWickedManipulator(this);
    }
}

class AshiokWickedManipulatorReplacementEffect extends ReplacementEffectImpl {

    AshiokWickedManipulatorReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would pay life while your library has at least that many cards in it, "
                + "exile that many cards from the top of your library instead.";
    }

    private AshiokWickedManipulatorReplacementEffect(final AshiokWickedManipulatorReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AshiokWickedManipulatorReplacementEffect copy() {
        return new AshiokWickedManipulatorReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Set<Card> cards = player.getLibrary().getTopCards(game, event.getAmount());
        player.moveCardsToExile(cards, source, game, false, null, "");

        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PAY_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID playerId = source.getControllerId();
        if (!event.getPlayerId().equals(playerId)) {
            return false;
        }

        Player player = game.getPlayer(playerId);
        return player != null && player.getLibrary().size() >= event.getAmount();
    }
}
