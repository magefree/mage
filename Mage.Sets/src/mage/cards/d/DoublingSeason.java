package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.replacement.CreateTwiceThatManyTokensEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DoublingSeason extends CardImpl {

    public DoublingSeason(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // If an effect would create one or more tokens under your control, it creates twice that many of those tokens instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CreateTwiceThatManyTokensEffect()));

        // If an effect would put one or more counters on a permanent you control, it puts twice that many of those counters on that permanent instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DoublingSeasonCounterEffect()));

    }

    public DoublingSeason(final DoublingSeason card) {
        super(card);
    }

    @Override
    public DoublingSeason copy() {
        return new DoublingSeason(this);
    }
}

class DoublingSeasonCounterEffect extends ReplacementEffectImpl {

    boolean landPlayed = false; // a played land is not an effect

    DoublingSeasonCounterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "If an effect would put one or more counters on a permanent you control, it puts twice that many of those counters on that permanent instead";
    }

    DoublingSeasonCounterEffect(final DoublingSeasonCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(event.getAmount() * 2, true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (!event.getFlag()) {
            return false;
        }
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
            landPlayed = (permanent != null
                    && permanent.isLand());  // a played land is not an effect
        }
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && event.getAmount() > 0
                && !landPlayed;  // example: gemstone mine being played as a land drop
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DoublingSeasonCounterEffect copy() {
        return new DoublingSeasonCounterEffect(this);
    }
}
