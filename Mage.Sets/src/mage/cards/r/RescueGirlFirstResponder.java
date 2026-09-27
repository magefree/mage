package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.condition.common.MyTurnCondition;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author muz
 */
public final class RescueGirlFirstResponder extends CardImpl {

    public RescueGirlFirstResponder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {T}: Return another target permanent you control to its owner's hand. Activate only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
            new ReturnToHandTargetEffect(), new TapSourceCost(), MyTurnCondition.instance
        );
        ability.addTarget(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_TARGET_PERMANENT));
        this.addAbility(ability);
    }

    private RescueGirlFirstResponder(final RescueGirlFirstResponder card) {
        super(card);
    }

    @Override
    public RescueGirlFirstResponder copy() {
        return new RescueGirlFirstResponder(this);
    }
}
