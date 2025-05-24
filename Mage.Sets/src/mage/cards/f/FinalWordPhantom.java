package mage.cards.f;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandCard;

/**
 * @author Cguy7777
 */
public final class FinalWordPhantom extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("spells");

    public FinalWordPhantom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // During each opponent's end step, you may cast spells as though they had flash.
        Condition condition = new CompoundCondition(OnOpponentsTurnCondition.instance, new IsStepCondition(PhaseStep.END_TURN, false));
        this.addAbility(new SimpleStaticAbility(
                new ConditionalAsThoughEffect(new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter), condition)
                        .setText("during each opponent's end step, you may cast spells as though they had flash")));
    }

    private FinalWordPhantom(final FinalWordPhantom card) {
        super(card);
    }

    @Override
    public FinalWordPhantom copy() {
        return new FinalWordPhantom(this);
    }
}
