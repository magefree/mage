package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.RollDieEvent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class KrarksOtherThumb extends CardImpl {

    public KrarksOtherThumb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);

        // If you would roll a die, instead roll two of those dice and ignore one of those results.
        this.addAbility(new SimpleStaticAbility(new KrarksOtherThumbEffect()));
    }

    private KrarksOtherThumb(final KrarksOtherThumb card) {
        super(card);
    }

    @Override
    public KrarksOtherThumb copy() {
        return new KrarksOtherThumb(this);
    }
}

class KrarksOtherThumbEffect extends ReplacementEffectImpl {

    KrarksOtherThumbEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you would roll a die, instead roll two of those dice and ignore one of those results";
    }

    KrarksOtherThumbEffect(final KrarksOtherThumbEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        // support any roll type
        RollDieEvent rollDieEvent = (RollDieEvent) event;
        rollDieEvent.doubleRollsAmount();
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DIE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public KrarksOtherThumbEffect copy() {
        return new KrarksOtherThumbEffect(this);
    }
}
