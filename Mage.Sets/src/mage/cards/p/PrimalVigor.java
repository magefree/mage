
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class PrimalVigor extends CardImpl {

    public PrimalVigor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}");

        // If one or more tokens would be created, twice that many of those tokens are created instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PrimalVigorTokenEffect()));
        // If one or more +1/+1 counters would be put on a creature, twice that many +1/+1 counters are put on that creature instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PrimalVigorCounterEffect()));

    }

    public PrimalVigor(final PrimalVigor card) {
        super(card);
    }

    @Override
    public PrimalVigor copy() {
        return new PrimalVigor(this);
    }
}

class PrimalVigorTokenEffect extends ReplacementEffectImpl {

    public PrimalVigorTokenEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Copy);
        staticText = "If one or more tokens would be created, twice that many of those tokens are created instead";
    }

    public PrimalVigorTokenEffect(final PrimalVigorTokenEffect effect) {
        super(effect);
    }

    @Override
    public PrimalVigorTokenEffect copy() {
        return new PrimalVigorTokenEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() * 2);
        return false;
    }

}

class PrimalVigorCounterEffect extends ReplacementEffectImpl {

    PrimalVigorCounterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "If one or more +1/+1 counters would be put on a creature, twice that many +1/+1 counters are put on that creature instead";
    }

    PrimalVigorCounterEffect(final PrimalVigorCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() * 2);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        if (permanent != null && permanent.isCreature()
                && event.getData() != null && event.getData().equals("+1/+1")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PrimalVigorCounterEffect copy() {
        return new PrimalVigorCounterEffect(this);
    }
}
