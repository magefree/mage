package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author vernonross21
 */
public final class ShamansTrance extends CardImpl {

    public ShamansTrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Other players can't play lands or cast spells from their graveyards this turn.
        this.getSpellAbility().addEffect(new ShamansTrancePreventsEffect());

        // You may play lands and cast spells from other players' graveyards this turn as though those cards were in your graveyard.
        this.getSpellAbility().addEffect(new ShamansTranceCastEffect());
    }

    private ShamansTrance(final ShamansTrance card) {
        super(card);
    }

    @Override
    public ShamansTrance copy() {
        return new ShamansTrance(this);
    }
}

class ShamansTrancePreventsEffect extends ContinuousRuleModifyingEffectImpl {

    ShamansTrancePreventsEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Other players can't play lands or cast spells from their graveyards this turn";
    }

    private ShamansTrancePreventsEffect(final ShamansTrancePreventsEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND
                || event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return false;
    }

    @Override
    public ShamansTrancePreventsEffect copy() {
        return new ShamansTrancePreventsEffect(this);
    }
}

class ShamansTranceCastEffect extends ContinuousRuleModifyingEffectImpl {

    ShamansTranceCastEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play lands and cast spells from other players' graveyards this turn as though those cards were in your graveyard";
    }

    private ShamansTranceCastEffect(final ShamansTranceCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND
                || event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return false;
    }

    @Override
    public ShamansTranceCastEffect copy() {
        return new ShamansTranceCastEffect(this);
    }
}



