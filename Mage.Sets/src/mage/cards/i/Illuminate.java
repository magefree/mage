
package mage.cards.i;

import java.util.UUID;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class Illuminate extends CardImpl {

    public Illuminate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Kicker {2}{R} and/or {3}{U}
        KickerAbility kickerAbility = new KickerAbility("{2}{R}");
        kickerAbility.addKickerCost("{3}{U}");
        this.addAbility(kickerAbility);
        // Illuminate deals X damage to target creature. If Illuminate was kicked with its {2}{R} kicker, it deals X damage to that creature's controller. If Illuminate was kicked with its {3}{U} kicker, you draw X cards.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetControllerEffect(ManacostVariableValue.REGULAR),
                new KickedCostCondition("{2}{R}"),
                "if this spell was kicked with its {2}{R} kicker, it deals X damage to that creature's controller."));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR),
                new KickedCostCondition("{3}{U}"),
                " if this spell was kicked with its {3}{U} kicker, you draw X cards."));

    }

    private Illuminate(final Illuminate card) {
        super(card);
    }

    @Override
    public Illuminate copy() {
        return new Illuminate(this);
    }
}
