package mage.cards.w;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Jmlundeen
 */
public final class WillOfTheJeskai extends CardImpl {

    public WillOfTheJeskai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");
        

        // Choose one. If you control a commander as you cast this spell, you may choose both instead.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, ControlACommanderCondition.instance);

        // * Each player may discard their hand and draw five cards.
        this.getSpellAbility().addEffect(new WillOfTheJeskaiEffect());

        // * Each instant and sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        Mode mode = new Mode(new WillOfTheJeskaiFlashbackEffect());
        this.getSpellAbility().addMode(mode);
    }

    private WillOfTheJeskai(final WillOfTheJeskai card) {
        super(card);
    }

    @Override
    public WillOfTheJeskai copy() {
        return new WillOfTheJeskai(this);
    }
}

class WillOfTheJeskaiEffect extends OneShotEffect {

    WillOfTheJeskaiEffect() {
        super(Outcome.Benefit);
        staticText = "each player may discard their hand and draw five cards";
    }

    private WillOfTheJeskaiEffect(final WillOfTheJeskaiEffect effect) {
        super(effect);
    }

    @Override
    public WillOfTheJeskaiEffect copy() {
        return new WillOfTheJeskaiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Player> wheelers = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.chooseUse(
                    Outcome.DrawCard, "Discard your hand and draw five?", source, game
            )) {
                game.informPlayers(player.getName() + " chooses to discard their hand and draw five");
                wheelers.add(player);
            }
        }
        for (Player player : wheelers) {
            player.discard(player.getHand(), false, source, game);
            player.drawCards(5, source, game);
        }
        return true;
    }
}

class WillOfTheJeskaiFlashbackEffect extends ContinuousEffectImpl {

    WillOfTheJeskaiFlashbackEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "each instant and sorcery card in your graveyard gains flashback until end of turn. " +
                "The flashback cost is equal to its mana cost";
    }

    private WillOfTheJeskaiFlashbackEffect(final WillOfTheJeskaiFlashbackEffect effect) {
        super(effect);
    }

    @Override
    public WillOfTheJeskaiFlashbackEffect copy() {
        return new WillOfTheJeskaiFlashbackEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (!getAffectedObjectsSet()) {
            return;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return;
        }
        player.getGraveyard()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(card -> card.isInstantOrSorcery(game))
                .forEachOrdered(card -> affectedObjectList.add(new MageObjectReference(card, game)));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.getGraveyard()
                .stream()
                .filter(cardId -> affectedObjectList.contains(new MageObjectReference(cardId, game)))
                .forEachOrdered(cardId -> {
                    Card card = game.getCard(cardId);
                    if (card == null) {
                        return;
                    }
                    FlashbackAbility ability = new FlashbackAbility(card, card.getManaCost());
                    ability.setSourceId(cardId);
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                });
        return true;
    }
}