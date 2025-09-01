package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AerithLastAncient extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition();
    private static final Condition condition2 = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 6);

    public AerithLastAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Raise -- At the beginning of your end step, if you gained life this turn, return target creature card from your graveyard to your hand. If you gained 7 or more life this turn, return that card to the battlefield instead.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new ConditionalOneShotEffect(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), new ReturnFromGraveyardToHandTargetEffect(),
                condition2, "return target creature card from your graveyard to your hand. " +
                "If you gained 7 or more life this turn, return that card to the battlefield instead"
        )).withInterveningIf(condition);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability.withFlavorWord("Raise").addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private AerithLastAncient(final AerithLastAncient card) {
        super(card);
    }

    @Override
    public AerithLastAncient copy() {
        return new AerithLastAncient(this);
    }
}
