package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.MyrToken;

import java.util.UUID;

/**
 * @author Grath
 */
public final class UrtetRemnantOfMemnarch extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Myr spell");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent(SubType.MYR);

    static {
        filter.add(SubType.MYR.getPredicate());
    }

    public UrtetRemnantOfMemnarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a Myr spell, create a 1/1 colorless Myr artifact creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new MyrToken()), filter, false));

        //At the beginning of combat on your turn, untap each Myr you control.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new UntapAllControllerEffect(filter2, "untap each Myr you control"), TargetController.YOU, false));

        // {W}{U}{B}{R}{G}, {T}: Put three +1/+1 counters on each Myr you control. Activate only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.P1P1.createInstance(3), filter2), new ManaCostsImpl<>("{W}{U}{B}{R}{G}"), MyTurnCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private UrtetRemnantOfMemnarch(final UrtetRemnantOfMemnarch card) {
        super(card);
    }

    @Override
    public UrtetRemnantOfMemnarch copy() {
        return new UrtetRemnantOfMemnarch(this);
    }
}
