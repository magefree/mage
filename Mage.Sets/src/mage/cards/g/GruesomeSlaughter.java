
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class GruesomeSlaughter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("colorless creatures you control");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public GruesomeSlaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}");

        // Until end of turn, colorless creatures you control gain "{T}: This creature deals damage equal to its power to target creature."
        Effect effect = new DamageTargetEffect(new SourcePermanentPowerCount());
        effect.setText("{this} deals damage equal to its power to target creature.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        effect = new GainAbilityControlledEffect(ability, Duration.EndOfTurn, filter);
        effect.setText("Until end of turn, colorless creatures you control gain \"{T}: This creature deals damage equal to its power to target creature.\"");
        this.getSpellAbility().addEffect(effect);
    }

    private GruesomeSlaughter(final GruesomeSlaughter card) {
        super(card);
    }

    @Override
    public GruesomeSlaughter copy() {
        return new GruesomeSlaughter(this);
    }
}
