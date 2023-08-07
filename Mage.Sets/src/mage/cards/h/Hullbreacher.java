package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Hullbreacher extends CardImpl {

    public Hullbreacher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // If an opponent would draw a card except the first one they draw in each of their draw steps, instead you create a Treasure token.
        this.addAbility(new SimpleStaticAbility(
                new HullbreacherReplacementEffect()
        ), new CardsDrawnDuringDrawStepWatcher());
    }

    private Hullbreacher(final Hullbreacher card) {
        super(card);
    }

    @Override
    public Hullbreacher copy() {
        return new Hullbreacher(this);
    }
}

class HullbreacherReplacementEffect extends ReplacementEffectImpl {

    HullbreacherReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an opponent would draw a card except the first one they draw in each of their draw steps, " +
                "instead you create a Treasure token";
    }

    private HullbreacherReplacementEffect(final HullbreacherReplacementEffect effect) {
        super(effect);
    }

    @Override
    public HullbreacherReplacementEffect copy() {
        return new HullbreacherReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        new TreasureToken().putOntoBattlefield(1, game, source, source.getControllerId());
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        if (!game.isActivePlayer(event.getPlayerId())
                || game.getTurnStepType() != PhaseStep.DRAW) {
            return true;
        }
        CardsDrawnDuringDrawStepWatcher watcher = game.getState().getWatcher(CardsDrawnDuringDrawStepWatcher.class);
        return watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 0;
    }
}
