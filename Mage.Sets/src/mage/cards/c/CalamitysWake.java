package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author weirddan455
 */
public final class CalamitysWake extends CardImpl {

    public CalamitysWake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile all graveyards. Players can't cast noncreature spells this turn. Exile Calamity's Wake.
        this.getSpellAbility().addEffect(new ExileGraveyardAllPlayersEffect());
        this.getSpellAbility().addEffect(new CalamitysWakeEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private CalamitysWake(final CalamitysWake card) {
        super(card);
    }

    @Override
    public CalamitysWake copy() {
        return new CalamitysWake(this);
    }
}

class CalamitysWakeEffect extends ContinuousRuleModifyingEffectImpl {

    public CalamitysWakeEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        this.staticText = "Players can't cast noncreature spells this turn.";
    }

    private CalamitysWakeEffect(final CalamitysWakeEffect effect) {
        super(effect);
    }

    @Override
    public CalamitysWakeEffect copy() {
        return new CalamitysWakeEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        return card != null && !card.isCreature(game);
    }
}
