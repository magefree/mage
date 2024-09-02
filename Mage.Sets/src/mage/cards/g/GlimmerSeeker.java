package mage.cards.g;

import mage.MageInt;
import mage.abilities.abilityword.SurvivalAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.GlimmerToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlimmerSeeker extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledCreaturePermanent(SubType.GLIMMER));
    private static final Hint hint = new ConditionHint(condition, "You control a Glimmer creature");

    public GlimmerSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Survival -- At the beginning of your second main phase, if Glimmer Seeker is tapped, draw a card if you control a Glimmer creature. If you don't control a Glimmer creature, create a 1/1 white Glimmer enchantment creature token.
        this.addAbility(new SurvivalAbility(new ConditionalOneShotEffect(
                new CreateTokenEffect(new GlimmerToken()), new DrawCardSourceControllerEffect(1),
                condition, "draw a card if you control a Glimmer creature. " +
                "If you don't control a Glimmer creature, create a 1/1 white Glimmer enchantment creature token"
        )).addHint(hint));
    }

    private GlimmerSeeker(final GlimmerSeeker card) {
        super(card);
    }

    @Override
    public GlimmerSeeker copy() {
        return new GlimmerSeeker(this);
    }
}
