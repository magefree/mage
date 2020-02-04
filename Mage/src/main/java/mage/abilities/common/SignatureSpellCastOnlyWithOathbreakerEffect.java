package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * For oathbreaker game mode
 *
 * @author JayDi85
 */
public class SignatureSpellCastOnlyWithOathbreakerEffect extends ContinuousRuleModifyingEffectImpl {

    private final Condition condition;
    private final UUID signatureSpell;

    public SignatureSpellCastOnlyWithOathbreakerEffect(Condition condition, UUID signatureSpell) {
        super(Duration.EndOfGame, Outcome.Detriment);
        this.condition = condition;
        this.signatureSpell = signatureSpell;
        staticText = setText();
    }

    private SignatureSpellCastOnlyWithOathbreakerEffect(final SignatureSpellCastOnlyWithOathbreakerEffect effect) {
        super(effect);
        this.condition = effect.condition;
        this.signatureSpell = effect.signatureSpell;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(signatureSpell)) {
            return condition != null && !condition.apply(game, source);
        }
        return false; // cast not prevented by this effect
    }

    @Override
    public SignatureSpellCastOnlyWithOathbreakerEffect copy() {
        return new SignatureSpellCastOnlyWithOathbreakerEffect(this);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("cast this spell only ");
        if (condition != null) {
            sb.append(' ').append(condition.toString());
        }
        return sb.toString();
    }
}
