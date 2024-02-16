package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EastfarthingFarmer extends CardImpl {

    public EastfarthingFarmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Eastfarthing Farmer enters the battlefield, create a Food token. When you do, target creature you control gets +1/+1 until end of turn for each Food you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EastfarthingFarmerEffect()));
    }

    private EastfarthingFarmer(final EastfarthingFarmer card) {
        super(card);
    }

    @Override
    public EastfarthingFarmer copy() {
        return new EastfarthingFarmer(this);
    }
}

class EastfarthingFarmerEffect extends OneShotEffect {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.FOOD), 1);

    EastfarthingFarmerEffect() {
        super(Outcome.Benefit);
        staticText = "create a Food token. When you do, target creature " +
                "you control gets +1/+1 until end of turn for each Food you control";
    }

    private EastfarthingFarmerEffect(final EastfarthingFarmerEffect effect) {
        super(effect);
    }

    @Override
    public EastfarthingFarmerEffect copy() {
        return new EastfarthingFarmerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!new FoodToken().putOntoBattlefield(1, game, source)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
