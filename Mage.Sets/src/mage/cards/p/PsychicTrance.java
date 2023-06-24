
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetSpell;

/**
 *
 * @author TheElk801
 */
public final class PsychicTrance extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wizards you control");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

    public PsychicTrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Until end of turn, Wizards you control gain "{tap}: Counter target spell."
        Ability abilityToAdd = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new TapSourceCost());
        abilityToAdd.addTarget(new TargetSpell());
        Effect effect = new GainAbilityControlledEffect(abilityToAdd, Duration.EndOfTurn, filter);
        effect.setText("until end of turn, Wizards you control gain \"{T}: Counter target spell.\"");
        this.getSpellAbility().addEffect(effect);
    }

    private PsychicTrance(final PsychicTrance card) {
        super(card);
    }

    @Override
    public PsychicTrance copy() {
        return new PsychicTrance(this);
    }
}
