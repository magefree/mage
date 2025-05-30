package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;
import mage.game.permanent.token.StoneIdolToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StoneIdolGenerator extends CardImpl {

    public StoneIdolGenerator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Whenever a creature you control attacks, you get {E}.
        this.addAbility(new AttacksAllTriggeredAbility(
                new GetEnergyCountersControllerEffect(1), false,
                StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED,
                SetTargetPointer.NONE, false
        ));

        // {T}, Pay six {E}: Create a 6/12 colorless Construct artifact creature token with trample. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new CreateTokenEffect(new StoneIdolToken()), new TapSourceCost()
        );
        ability.addCost(new PayEnergyCost(6));
        this.addAbility(ability);
    }

    private StoneIdolGenerator(final StoneIdolGenerator card) {
        super(card);
    }

    @Override
    public StoneIdolGenerator copy() {
        return new StoneIdolGenerator(this);
    }
}
