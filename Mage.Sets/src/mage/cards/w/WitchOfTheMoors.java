package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitchOfTheMoors extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0);

    public WitchOfTheMoors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // At the beginning of your end step, if you gained life this turn, each opponent sacrifices a 
        // creature and you return up to one target creature card from your graveyard to your hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new SacrificeOpponentsEffect(
                        StaticFilters.FILTER_PERMANENT_A_CREATURE
                ), TargetController.YOU, false),
                condition, "At the beginning of your end step, if you gained life this turn, "
                + "each opponent sacrifices a creature and you return up to one target creature card "
                + "from your graveyard to your hand."
        );
        ability.addEffect(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        ));
        this.addAbility(ability.addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private WitchOfTheMoors(final WitchOfTheMoors card) {
        super(card);
    }

    @Override
    public WitchOfTheMoors copy() {
        return new WitchOfTheMoors(this);
    }
}
