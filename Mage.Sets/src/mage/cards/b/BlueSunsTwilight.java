package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlueSunsTwilight extends CardImpl {

    public BlueSunsTwilight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Gain control of target creature with mana value X or less. If X is 5 or more, create a token that's a copy of that creature.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom, true)
                .setText("gain control of target creature with mana value X or less"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenCopyTargetEffect(), BlueSunsTwilightCondition.instance,
                "If X is 5 or more, create a token that's a copy of that creature"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(new XManaValueTargetAdjuster(ComparisonType.OR_LESS));
    }

    private BlueSunsTwilight(final BlueSunsTwilight card) {
        super(card);
    }

    @Override
    public BlueSunsTwilight copy() {
        return new BlueSunsTwilight(this);
    }
}

enum BlueSunsTwilightCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source.getManaCostsToPay().getX() >= 5;
    }
}
