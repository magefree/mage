package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.hint.HintUtils;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CherubaelToken;
import mage.players.Player;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

import java.awt.*;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class InquisitorEisenhorn extends CardImpl {

    public InquisitorEisenhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.INQUISITOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // You may reveal the first card you draw each turn as you draw it. Whenever you reveal an instant or
        // sorcery card this way, create Cherubael, a legendary 4/4 black Demon creature token with flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InquisitorEisenhornReplacementEffect()), new CardsAmountDrawnThisTurnWatcher());

        // Whenever Inquisitor Eisenhorn deals combat damage to a player, investigate that many times.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new InvestigateEffect(SavedDamageValue.MANY).setText("investigate that many times"),
                false, true
        ));
    }

    private InquisitorEisenhorn(final InquisitorEisenhorn card) {
        super(card);
    }

    @Override
    public InquisitorEisenhorn copy() {
        return new InquisitorEisenhorn(this);
    }
}

class InquisitorEisenhornReplacementEffect extends ReplacementEffectImpl {

    public InquisitorEisenhornReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "You may reveal the first card you draw each turn as you draw it. Whenever you reveal an instant or " +
                "sorcery card this way, create Cherubael, a legendary 4/4 black Demon creature token with flying";
    }

    public InquisitorEisenhornReplacementEffect(final InquisitorEisenhornReplacementEffect effect) {
        super(effect);
    }

    @Override
    public InquisitorEisenhornReplacementEffect copy() {
        return new InquisitorEisenhornReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        // reveal the top card and draw (return false to continue to default draw)
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent == null || controller == null) {
            return false;
        }

        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }

        if (topCard.isInstantOrSorcery(game)) {
            new CherubaelToken().putOntoBattlefield(1, game, source);
        }

        // reveal
        controller.setTopCardRevealed(true);

        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    String getAppliedMark(Game game, Ability source) {
        return source.getId() + "-applied-" + source.getControllerId() + "-" + game.getState().getTurnNum();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }

        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent == null && controller == null) {
            return false;
        }

        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }

        // only first drawn card
        // if card cast on that turn or controller changed then needs history from watcher
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

        // ask player to reveal top card
        String mes = topCard.getName() + ", " + (topCard.isInstantOrSorcery(game)
                ? HintUtils.prepareText("you will create Cherubael", Color.green)
                : HintUtils.prepareText("you won't create Cherubael", Color.red));
        return controller.chooseUse(Outcome.Benefit, "Reveal first drawn card (" + mes + ")?", source, game);
    }
}
