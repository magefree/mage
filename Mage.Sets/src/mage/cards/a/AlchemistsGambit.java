package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.CleaveAbility;
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
public final class AlchemistsGambit extends CardImpl {

    public AlchemistsGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Cleave {4}{U}{U}{R}
        Ability ability = new CleaveAbility(
                this,
                new AddExtraTurnControllerEffect(
                        false, AlchemistsGambitApplier.instance
                ), "{4}{U}{U}{R}"
        );
        ability.addEffect(new ExileSpellEffect());
        this.addAbility(ability);

        // Take an extra turn after this one. During that turn, damage can't be prevented. [At the beginning of that turn's end step, you lose the game.]
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect(
                true, AlchemistsGambitApplier.instance
        ).setText("take an extra turn after this one. During that turn, damage can't be prevented. " +
                "[At the beginning of that turn's end step, you lose the game.]"));

        // Exile Alchemist's Gambit.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private AlchemistsGambit(final AlchemistsGambit card) {
        super(card);
    }

    @Override
    public AlchemistsGambit copy() {
        return new AlchemistsGambit(this);
    }
}

enum AlchemistsGambitApplier implements AddExtraTurnControllerEffect.TurnModApplier {
    instance;

    @Override
    public void apply(UUID turnId, Ability source, Game game) {
        game.addEffect(new AlchemistsGambitEffect(turnId), source);
    }
}

class AlchemistsGambitEffect extends ReplacementEffectImpl {

    private final UUID turnId;

    public AlchemistsGambitEffect(UUID turnId) {
        super(Duration.Custom, Outcome.Benefit);
        this.turnId = turnId;
    }

    public AlchemistsGambitEffect(final AlchemistsGambitEffect effect) {
        super(effect);
        this.turnId = effect.turnId;
    }

    @Override
    public AlchemistsGambitEffect copy() {
        return new AlchemistsGambitEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PREVENT_DAMAGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getState().getTurnId().equals(turnId);
    }
}
