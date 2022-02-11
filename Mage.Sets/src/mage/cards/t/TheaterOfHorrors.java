package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.OpponentsLostLifeHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponentOrPlaneswalker;
import mage.util.CardUtil;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheaterOfHorrors extends CardImpl {

    public TheaterOfHorrors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{R}");

        // At the beginning of your upkeep, exile the top card of your library.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TheaterOfHorrorsExileEffect(),
                TargetController.YOU, false
        ));

        // During your turn, if an opponent lost life this turn, you may play cards exiled with Theater of Horrors.
        this.addAbility(new SimpleStaticAbility(new TheaterOfHorrorsCastEffect()).addHint(OpponentsLostLifeHint.instance));

        // {3}{R}: Theater of Horrors deals 1 damage to target opponent or planeswalker.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1),
                new ManaCostsImpl<>("{3}{R}")
        );
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private TheaterOfHorrors(final TheaterOfHorrors card) {
        super(card);
    }

    @Override
    public TheaterOfHorrors copy() {
        return new TheaterOfHorrors(this);
    }
}

class TheaterOfHorrorsExileEffect extends OneShotEffect {

    TheaterOfHorrorsExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library.";
    }

    private TheaterOfHorrorsExileEffect(final TheaterOfHorrorsExileEffect effect) {
        super(effect);
    }

    @Override
    public TheaterOfHorrorsExileEffect copy() {
        return new TheaterOfHorrorsExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        return player.moveCardsToExile(
                card, source, game, true, CardUtil.getCardExileZoneId(game, source),
                CardUtil.createObjectRealtedWindowTitle(source, game, null)
        );
    }
}

class TheaterOfHorrorsCastEffect extends AsThoughEffectImpl {

    TheaterOfHorrorsCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "during your turn, if an opponent lost life this turn, "
                + "you may play lands and cast spells from among cards exiled with {this}";
    }

    private TheaterOfHorrorsCastEffect(final TheaterOfHorrorsCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TheaterOfHorrorsCastEffect copy() {
        return new TheaterOfHorrorsCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card theCard = game.getCard(objectId);
        if (theCard == null) {
            return false;
        }
        objectId = theCard.getMainCard().getId(); // for split cards and mdfc
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher != null
                && game.isActivePlayer(source.getControllerId())
                && watcher.getAllOppLifeLost(source.getControllerId(), game) > 0
                && affectedControllerId.equals(source.getControllerId())
                && game.getState().getZone(objectId) == Zone.EXILED) {
            ExileZone zone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            return zone != null
                    && zone.contains(objectId);
        }
        return false;
    }
}
