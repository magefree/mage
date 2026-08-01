package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.replacement.CreateTwiceThatManyTokensEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;

/**
 *
 * @author muz
 */
public final class BardKingOfDale extends CardImpl {

    public BardKingOfDale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // If you would draw a card except the first one you draw in each of your draw steps, draw two cards instead.
        this.addAbility(new SimpleStaticAbility(new BardKingOfDaleReplacementEffect()), new CardsDrawnDuringDrawStepWatcher());

        // If one or more tokens would be created under your control, twice that many of those tokens are created instead.
        this.addAbility(new SimpleStaticAbility(new CreateTwiceThatManyTokensEffect()));
    }

    private BardKingOfDale(final BardKingOfDale card) {
        super(card);
    }

    @Override
    public BardKingOfDale copy() {
        return new BardKingOfDale(this);
    }
}

class BardKingOfDaleReplacementEffect extends ReplacementEffectImpl {

    BardKingOfDaleReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If you would draw a card except the first one you draw in each of your draw steps, draw two cards instead";
    }

    private BardKingOfDaleReplacementEffect(final BardKingOfDaleReplacementEffect effect) {
        super(effect);
    }

    @Override
    public BardKingOfDaleReplacementEffect copy() {
        return new BardKingOfDaleReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(2, source, game, event);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        if (!game.isActivePlayer(event.getPlayerId())
                || game.getPhase().getStep().getType() != PhaseStep.DRAW) {
            return true;
        }
        CardsDrawnDuringDrawStepWatcher watcher = game.getState().getWatcher(CardsDrawnDuringDrawStepWatcher.class);
        return watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 0;
    }
}
