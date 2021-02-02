
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.ruleModifying.CantRegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author markedagain
 */
public final class Carbonize extends CardImpl {

    public Carbonize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Carbonize deals 3 damage to any target. That creature can't be regenerated this turn. If the creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new CantRegenerateTargetEffect(Duration.EndOfTurn, "That creature"));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect().setText("If the creature would die this turn, exile it instead"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));

    }

    private Carbonize(final Carbonize card) {
        super(card);
    }

    @Override
    public Carbonize copy() {
        return new Carbonize(this);
    }
}
