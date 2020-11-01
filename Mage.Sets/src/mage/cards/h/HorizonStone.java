package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HorizonStone extends CardImpl {

    public HorizonStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // If you would lose unspent mana, that mana becomes colorless instead.
        this.addAbility(new SimpleStaticAbility(new HorizonStoneEffect()));
    }

    private HorizonStone(final HorizonStone card) {
        super(card);
    }

    @Override
    public HorizonStone copy() {
        return new HorizonStone(this);
    }
}

class HorizonStoneEffect extends ReplacementEffectImpl {

    HorizonStoneEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would lose unspent mana, that mana becomes colorless instead.";
    }

    private HorizonStoneEffect(final HorizonStoneEffect effect) {
        super(effect);
    }

    @Override
    public HorizonStoneEffect copy() {
        return new HorizonStoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EMPTY_MANA_POOL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}
