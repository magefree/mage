package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.RevealSourceFromYourHandCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TetzimocPrimalDeath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature your opponents control with a prey counter on it");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(CounterType.PREY.getPredicate());
    }

    public TetzimocPrimalDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {B}, Reveal Tetzimoc, Primal Death from your hand: Put a prey counter on target creature. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.HAND, new AddCountersTargetEffect(CounterType.PREY.createInstance()), new ManaCostsImpl<>("{B}"), MyTurnCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new RevealSourceFromYourHandCost());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);

        // When Tetzimoc, Primal Death enters the battlefield, destroy each creature your opponents control with a prey counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DestroyAllEffect(filter).setText("destroy each creature your opponents control with a prey counter on it"), false));

    }

    private TetzimocPrimalDeath(final TetzimocPrimalDeath card) {
        super(card);
    }

    @Override
    public TetzimocPrimalDeath copy() {
        return new TetzimocPrimalDeath(this);
    }
}
