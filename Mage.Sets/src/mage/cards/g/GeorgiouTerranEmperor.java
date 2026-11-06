package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.OfficerToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class GeorgiouTerranEmperor extends CardImpl {

    public GeorgiouTerranEmperor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // You don't lose the game for having 0 or less life.
        this.addAbility(new SimpleStaticAbility(new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield)));

        // At the beginning of your end step, you lose 3 life and draw a card. Then if your life total is 0 or less, create ten 1/1 red Officer creature tokens.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new LoseLifeSourceControllerEffect(3));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        ability.addEffect(new ConditionalOneShotEffect(new CreateTokenEffect(new OfficerToken(), 10),
            new LifeCompareCondition(TargetController.YOU, ComparisonType.FEWER_THAN, 1),
            "then if your life total is 0 or less, create ten 1/1 red Officer creature tokens"));
        this.addAbility(ability);
    }

    private GeorgiouTerranEmperor(final GeorgiouTerranEmperor card) {
        super(card);
    }

    @Override
    public GeorgiouTerranEmperor copy() {
        return new GeorgiouTerranEmperor(this);
    }
}
