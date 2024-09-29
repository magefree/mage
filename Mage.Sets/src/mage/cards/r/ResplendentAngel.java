package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.AngelVigilanceToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ResplendentAngel extends CardImpl {

    public ResplendentAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each end step, if you gained 5 or more life this turn, create a 4/4 white Angel creature token with flying and vigilance.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new AngelVigilanceToken()),
                TargetController.ANY, new YouGainedLifeCondition(ComparisonType.MORE_THAN, 4), false
        ).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());

        // {3}{W}{W}{W}: Until end of turn, Resplendent Angel gets +2/+2 and gains lifelink.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                2, 2, Duration.EndOfTurn
        ).setText("until end of turn, {this} gets +2/+2"), new ManaCostsImpl<>("{3}{W}{W}{W}"));
        ability.addEffect(new GainAbilitySourceEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains lifelink"));
        this.addAbility(ability);
    }

    private ResplendentAngel(final ResplendentAngel card) {
        super(card);
    }

    @Override
    public ResplendentAngel copy() {
        return new ResplendentAngel(this);
    }
}
