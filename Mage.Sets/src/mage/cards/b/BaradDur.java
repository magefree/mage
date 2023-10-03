package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaradDur extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);
    private static final Hint hint = new ConditionHint(condition, "You control a legendary creature");

    public BaradDur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // Barad-dur enters the battlefield tapped unless you control a legendary creature.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(new TapSourceEffect(), condition, ""),
                "tapped unless you control a legendary creature"
        ).addHint(hint));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {X}{X}{B}, {T}: Amass Orcs X. Activate only if a creature died this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new AmassEffect(ManacostVariableValue.REGULAR, SubType.ORC, false),
                new ManaCostsImpl<>("{X}{X}{B}"),
                MorbidCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BaradDur(final BaradDur card) {
        super(card);
    }

    @Override
    public BaradDur copy() {
        return new BaradDur(this);
    }
}