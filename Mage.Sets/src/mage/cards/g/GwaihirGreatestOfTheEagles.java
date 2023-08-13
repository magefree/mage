package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.GwaihirBirdToken;
import mage.target.common.TargetAttackingCreature;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GwaihirGreatestOfTheEagles extends CardImpl {

    public GwaihirGreatestOfTheEagles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD, SubType.NOBLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Gwaihir attacks, target attacking creature gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), false);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);

        // At the beginning of each end step, if you gained 3 or more life this turn, create a 3/3 white Bird creature token
        // with flying and "Whenever this creature attacks, target attacking creature gains flying until end of turn."
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new GwaihirBirdToken()), TargetController.ANY,
                new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2), false
        ).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private GwaihirGreatestOfTheEagles(final GwaihirGreatestOfTheEagles card) {
        super(card);
    }

    @Override
    public GwaihirGreatestOfTheEagles copy() {
        return new GwaihirGreatestOfTheEagles(this);
    }
}
