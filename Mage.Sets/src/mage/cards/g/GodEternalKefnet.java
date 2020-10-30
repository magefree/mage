package mage.cards.g;

import java.awt.*;
import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GodEternalDiesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.HintUtils;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

/**
 * @author JayDi85
 */
public final class GodEternalKefnet extends CardImpl {

    public GodEternalKefnet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may reveal the first card you draw each turn as you draw it. Whenever you reveal an instant or sorcery card this way,
        // copy that card and you may cast the copy. That copy costs {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GodEternalKefnetDrawCardReplacementEffect()), new CardsAmountDrawnThisTurnWatcher());

        // When God-Eternal Kefnet dies or is put into exile from the battlefield, you may put it into its owner’s library third from the top.
        this.addAbility(new GodEternalDiesTriggeredAbility());
    }

    public GodEternalKefnet(final GodEternalKefnet card) {
        super(card);
    }

    @Override
    public GodEternalKefnet copy() {
        return new GodEternalKefnet(this);
    }
}

class GodEternalKefnetDrawCardReplacementEffect extends ReplacementEffectImpl {

    public GodEternalKefnetDrawCardReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "You may reveal the first card you draw each turn as you draw it. Whenever you reveal an instant "
                + "or sorcery card this way, copy that card and you may cast the copy. That copy costs {2} less to cast";
    }

    public GodEternalKefnetDrawCardReplacementEffect(final GodEternalKefnetDrawCardReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GodEternalKefnetDrawCardReplacementEffect copy() {
        return new GodEternalKefnetDrawCardReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        // reveal top card and drawn (return false to continue default draw)
        Permanent god = game.getPermanent(source.getSourceId());
        Player you = game.getPlayer(source.getControllerId());
        if (god == null && you == null) {
            return false;
        }

        Card topCard = you.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }

        // reveal
        you.setTopCardRevealed(true);

        // cast copy
        if (topCard.isInstantOrSorcery() && you.chooseUse(outcome, "Would you like to copy " + topCard.getName()
                + " and cast it for {2} less?", source, game)) {
            Card blueprint = topCard.copy();
            blueprint.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(2)));
            Card copiedCard = game.copyCard(blueprint, source, source.getControllerId());
            you.moveCardToHandWithInfo(copiedCard, source.getSourceId(), game, true); // The copy is created in and cast from your hand.
            you.cast(copiedCard.getSpellAbility(), game, false, new ApprovingObject(source, game));
        }

        // draw (return false for default draw)
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DRAW_CARD;
    }

    String getAppliedMark(Game game, Ability source) {
        return source.getId() + "-applied-" + source.getControllerId() + "-" + game.getState().getTurnNum();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }

        Permanent god = game.getPermanent(source.getSourceId());
        Player you = game.getPlayer(source.getControllerId());
        if (god == null && you == null) {
            return false;
        }

        Card topCard = you.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }

        // only first draw card
        // if card casted on that turn or controlled changed then needs history from watcher
        CardsAmountDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
        if (watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) != 0) {
            return false;
        }

        // one time use (if multiple cards drawn)
        String mark = getAppliedMark(game, source);
        if (game.getState().getValue(mark) != null) {
            return false;
        }
        game.getState().setValue(mark, true);

        // ask player to reveal top cards
        String mes = topCard.getName() + ", " + (topCard.isInstantOrSorcery()
                ? HintUtils.prepareText("you can copy it and cast {2} less", Color.green)
                : HintUtils.prepareText("you can't copy it", Color.red));
        return you.chooseUse(Outcome.Benefit, "Would you like to reveal first drawn card (" + mes + ")?", source, game);
    }

}
