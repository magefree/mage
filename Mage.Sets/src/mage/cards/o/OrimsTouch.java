
package mage.cards.o;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class OrimsTouch extends CardImpl {

    public OrimsTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Kicker {1}
        this.addAbility(new KickerAbility("{1}"));

        // Prevent the next 2 damage that would be dealt to any target this turn. If Orim's Touch was kicked, prevent the next 4 damage that would be dealt to that creature or player this turn instead.
        Effect effect = new ConditionalReplacementEffect(
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 4),
                new LockedInCondition(KickedCondition.ONCE),
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 2));
        effect.setText("Prevent the next 2 damage that would be dealt to any target this turn. If Orim's Touch was kicked, prevent the next 4 damage that would be dealt to that permanent or player this turn instead");
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(effect);
    }

    private OrimsTouch(final OrimsTouch card) {
        super(card);
    }

    @Override
    public OrimsTouch copy() {
        return new OrimsTouch(this);
    }
}
