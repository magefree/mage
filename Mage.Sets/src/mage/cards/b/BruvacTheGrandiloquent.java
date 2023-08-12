package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BruvacTheGrandiloquent extends CardImpl {

    public BruvacTheGrandiloquent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // If an opponent would mill one or more cards, they mill twice that many cards instead.
        this.addAbility(new SimpleStaticAbility(new BruvacTheGrandiloquentReplacementEffect()));
    }

    private BruvacTheGrandiloquent(final BruvacTheGrandiloquent card) {
        super(card);
    }

    @Override
    public BruvacTheGrandiloquent copy() {
        return new BruvacTheGrandiloquent(this);
    }
}

class BruvacTheGrandiloquentReplacementEffect extends ReplacementEffectImpl {

    BruvacTheGrandiloquentReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If an opponent would mill one or more cards, they mill twice that many cards instead";
    }

    private BruvacTheGrandiloquentReplacementEffect(final BruvacTheGrandiloquentReplacementEffect effect) {
        super(effect);
    }

    @Override
    public BruvacTheGrandiloquentReplacementEffect copy() {
        return new BruvacTheGrandiloquentReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MILL_CARDS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getOpponents(source.getControllerId()).contains(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
