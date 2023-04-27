
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class OrimsChant extends CardImpl {

    public OrimsChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Kicker {W} (You may pay an additional {W} as you cast this spell.)
        this.addAbility(new KickerAbility("{W}"));

        // Target player can't cast spells this turn.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new OrimsChantCantCastEffect());

        // If Orim's Chant was kicked, creatures can't attack this turn.
        this.getSpellAbility().addEffect(new OrimsChantEffect());
    }

    private OrimsChant(final OrimsChant card) {
        super(card);
    }

    @Override
    public OrimsChant copy() {
        return new OrimsChant(this);
    }

}

class OrimsChantCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public OrimsChantCantCastEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Target player can't cast spells this turn";
    }

    public OrimsChantCantCastEffect(final OrimsChantCantCastEffect effect) {
        super(effect);
    }

    @Override
    public OrimsChantCantCastEffect copy() {
        return new OrimsChantCantCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(getTargetPointer().getFirst(game, source));
    }
}

class OrimsChantEffect extends OneShotEffect {

    public OrimsChantEffect() {
        super(Outcome.Detriment);
        this.staticText = "if this spell was kicked, creatures can't attack this turn";
    }

    public OrimsChantEffect(final OrimsChantEffect effect) {
        super(effect);
    }

    @Override
    public OrimsChantEffect copy() {
        return new OrimsChantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && KickedCondition.ONCE.apply(game, source)) {
            game.addEffect(new CantAttackAnyPlayerAllEffect(Duration.EndOfTurn, FILTER_PERMANENT_CREATURES), source);
            return true;
        }
        return false;
    }
}
