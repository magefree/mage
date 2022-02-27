
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class HealingGrace extends CardImpl {

    public HealingGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Prevent the next 3 damage that would be dealt to any target this turn by a source of your choice. You gain 3 life.
        this.getSpellAbility().addEffect(new HealingGraceEffect());
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private HealingGrace(final HealingGrace card) {
        super(card);
    }

    @Override
    public HealingGrace copy() {
        return new HealingGrace(this);
    }
}

class HealingGraceEffect extends PreventionEffectImpl {

    protected final TargetSource targetSource;

    public HealingGraceEffect() {
        super(Duration.EndOfTurn, 3, false);
        this.targetSource = new TargetSource();
        this.staticText = "Prevent the next 3 damage that would be dealt to any target this turn by a source of your choice";
    }

    public HealingGraceEffect(final HealingGraceEffect effect) {
        super(effect);
        this.targetSource = effect.targetSource.copy();
    }

    @Override
    public HealingGraceEffect copy() {
        return new HealingGraceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.targetSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        preventDamageAction(event, source, game);
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getFirstTarget()) && event.getSourceId().equals(targetSource.getFirstTarget())) {
                return true;
            }
        }
        return false;
    }
}
