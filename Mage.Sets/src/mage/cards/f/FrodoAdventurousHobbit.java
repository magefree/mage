package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceIsRingBearerCondition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.watchers.common.TemptedByTheRingWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrodoAdventurousHobbit extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);

    public FrodoAdventurousHobbit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Partner with Sam, Loyal Attendant
        this.addAbility(new PartnerWithAbility("Sam, Loyal Attendant"));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Frodo, Adventurous Hobbit attacks, if you gained 3 or more life this turn, the Ring tempts you. Then if Frodo is your Ring-bearer and the Ring has tempted you two or more times this game, draw a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new TheRingTemptsYouEffect()),
                condition, "Whenever {this} attacks, if you gained 3 or more life this turn, " +
                "the Ring tempts you. Then if {this} is your Ring-bearer and the Ring " +
                "has tempted you two or more times this game, draw a card."
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                FrodoAdventurousHobbitCondition.instance
        ));
        this.addAbility(ability.addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private FrodoAdventurousHobbit(final FrodoAdventurousHobbit card) {
        super(card);
    }

    @Override
    public FrodoAdventurousHobbit copy() {
        return new FrodoAdventurousHobbit(this);
    }
}

enum FrodoAdventurousHobbitCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return SourceIsRingBearerCondition.instance.apply(game, source)
                && TemptedByTheRingWatcher.getCount(source.getControllerId(), game) >= 2;
    }
}
