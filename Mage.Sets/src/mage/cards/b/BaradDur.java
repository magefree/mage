package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.condition.common.YouControlALegendaryCreatureCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaradDur extends CardImpl {

    public BaradDur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // Barad-dur enters the battlefield tapped unless you control a legendary creature.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(YouControlALegendaryCreatureCondition.instance)
                .addHint(YouControlALegendaryCreatureCondition.getHint()));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {X}{X}{B}, {T}: Amass Orcs X. Activate only if a creature died this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new AmassEffect(GetXValue.instance, SubType.ORC, false),
                new ManaCostsImpl<>("{X}{X}{B}"), MorbidCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(MorbidHint.instance));
    }

    private BaradDur(final BaradDur card) {
        super(card);
    }

    @Override
    public BaradDur copy() {
        return new BaradDur(this);
    }
}
