
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;

/**
 *
 * @author LevelX2
 */
public final class SightOfTheScalelords extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control with toughness 4 or greater");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 3));
    }

    public SightOfTheScalelords(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // At the beginning of combat on your turn, creature you control with toughness 4 or greater get +2/+2 and gain vigilance until end of turn.
        Effect effect = new BoostControlledEffect(2, 2, Duration.EndOfTurn, filter, false);
        effect.setText("creatures you control with toughness 4 or greater get +2/+2");
        Ability ability = new BeginningOfCombatTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, false, false);
        effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, filter);
        effect.setText("and gain vigilance until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private SightOfTheScalelords(final SightOfTheScalelords card) {
        super(card);
    }

    @Override
    public SightOfTheScalelords copy() {
        return new SightOfTheScalelords(this);
    }
}
