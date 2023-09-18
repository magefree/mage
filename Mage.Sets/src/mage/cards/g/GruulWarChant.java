
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

/**
 *
 * @author LevelX2
 */
public final class GruulWarChant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creatures you control");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AttackingPredicate.instance);
    }

    public GruulWarChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}{G}");


        // Attacking creatures you control get +1/+0 and have menace. (They can't be blocked except by two or more creatures.)
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAllEffect(new MenaceAbility(false), Duration.WhileOnBattlefield, filter));
        ability.addEffect(new BoostAllEffect(1,0, Duration.WhileOnBattlefield, filter, false));
        this.addAbility(ability);
    }

    private GruulWarChant(final GruulWarChant card) {
        super(card);
    }

    @Override
    public GruulWarChant copy() {
        return new GruulWarChant(this);
    }
}
