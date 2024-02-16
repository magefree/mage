package mage.cards.a;

import java.util.UUID;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author noahg
 */
public final class AzoriusPloy extends CardImpl {

    public AzoriusPloy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{W}{U}");


        // Prevent all combat damage target creature would deal this turn.
        Effect effect = new PreventDamageByTargetEffect( Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage target creature would deal this turn.");
        this.getSpellAbility().addEffect(effect);
        Target target = new TargetCreaturePermanent(new FilterCreaturePermanent("first creature"));
        this.getSpellAbility().addTarget(target);

        // Prevent all combat damage that would be dealt to target creature this turn.
        Effect effect2 = new PreventDamageToTargetEffect(Duration.EndOfTurn, true);
        effect2.setText("<br><br>Prevent all combat damage that would be dealt to target creature this turn.");
        effect2.setTargetPointer(new SecondTargetPointer());
        this.getSpellAbility().addEffect(effect2);
        target = new TargetCreaturePermanent(new FilterCreaturePermanent("second creature (can be the same as the first)"));
        this.getSpellAbility().addTarget(target);

    }

    private AzoriusPloy(final AzoriusPloy card) {
        super(card);
    }

    @Override
    public AzoriusPloy copy() {
        return new AzoriusPloy(this);
    }
}