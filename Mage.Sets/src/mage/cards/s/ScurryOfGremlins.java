package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GremlinToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScurryOfGremlins extends CardImpl {

    public ScurryOfGremlins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{W}");

        // When Scurry of Gremlins enters the battlefield, create two 1/1 red Gremlin creature tokens. Then you get an amount of {E} equal to the number of creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GremlinToken(), 2));
        ability.addEffect(new GetEnergyCountersControllerEffect(CreaturesYouControlCount.instance)
                .setText("Then you get an amount of {E} equal to the number of creatures you control"));
        this.addAbility(ability.addHint(CreaturesYouControlHint.instance));

        // Pay {E}{E}{E}{E}: Creatures you control get +1/+0 and gain haste until end of turn.
        ability = new SimpleActivatedAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn
        ).setText("creatures you control get +1/+0"), new PayEnergyCost(4));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain haste until end of turn"));
        this.addAbility(ability);
    }

    private ScurryOfGremlins(final ScurryOfGremlins card) {
        super(card);
    }

    @Override
    public ScurryOfGremlins copy() {
        return new ScurryOfGremlins(this);
    }
}
