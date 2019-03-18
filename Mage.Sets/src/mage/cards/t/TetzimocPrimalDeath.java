
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.RevealSourceFromYourHandCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TetzimocPrimalDeath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature your opponents control with a prey counter on it");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter.add(new CounterPredicate(CounterType.PREY));
    }

    public TetzimocPrimalDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {B}, Reveal Tetzimoc, Primal Death from your hand: Put a prey counter on target creature. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.HAND, new AddCountersTargetEffect(CounterType.PREY.createInstance()), new ManaCostsImpl("{B}"), MyTurnCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new RevealSourceFromYourHandCost());
        this.addAbility(ability);

        // When Tetzimoc, Primal Death enters the battlefield, destroy each creature your opponents control with a prey counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DestroyAllEffect(filter).setText("destroy each creature your opponents control with a prey counter on it"), false));

    }

    public TetzimocPrimalDeath(final TetzimocPrimalDeath card) {
        super(card);
    }

    @Override
    public TetzimocPrimalDeath copy() {
        return new TetzimocPrimalDeath(this);
    }
}
