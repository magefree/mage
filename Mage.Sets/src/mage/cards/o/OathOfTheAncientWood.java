
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OathOfTheAncientWood extends CardImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Oath of the Ancient Wood or another enchantment");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public OathOfTheAncientWood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Whenever Oath of the Ancient Wood or another enchantment enters the battlefield under your control, you may put a +1/+1 counter on target creature.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, effect, filter, true, SetTargetPointer.NONE, null, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public OathOfTheAncientWood(final OathOfTheAncientWood card) {
        super(card);
    }

    @Override
    public OathOfTheAncientWood copy() {
        return new OathOfTheAncientWood(this);
    }
}
