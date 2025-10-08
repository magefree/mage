package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GolbezCrystalCollector extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledArtifactPermanent("you control four or more artifacts"), ComparisonType.MORE_THAN, 3
    );
    private static final Condition condition2 = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, ComparisonType.MORE_THAN, 7
    );

    public GolbezCrystalCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever an artifact you control enters, surveil 1.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new SurveilEffect(1), StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
        ));

        // At the beginning of your end step, if you control four or more artifacts, return target creature card from your graveyard to your hand. Then if you control eight or more artifacts, each opponent loses life equal to that card's power.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect()).withInterveningIf(condition);
        ability.addEffect(new ConditionalOneShotEffect(
                new LoseLifeOpponentsEffect(GolbezCrystalCollectorValue.instance), condition2,
                "Then if you control eight or more artifacts, each opponent loses life equal to that card's power"
        ));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability.addHint(ArtifactYouControlHint.instance));
    }

    private GolbezCrystalCollector(final GolbezCrystalCollector card) {
        super(card);
    }

    @Override
    public GolbezCrystalCollector copy() {
        return new GolbezCrystalCollector(this);
    }
}

enum GolbezCrystalCollectorValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(effect.getTargetPointer().getFirst(game, sourceAbility))
                .map(game::getCard)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
    }

    @Override
    public GolbezCrystalCollectorValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
