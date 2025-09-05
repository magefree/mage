package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RokusMastery extends CardImpl {

    public RokusMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");

        // Roku's Mastery deals X damage to target creature. If X is 4 or greater, scry 2.
        this.getSpellAbility().addEffect(new DamageTargetEffect(GetXValue.instance));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ScryEffect(2), RokusMasteryCondition.instance, "If X is 4 or greater, scry 2"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RokusMastery(final RokusMastery card) {
        super(card);
    }

    @Override
    public RokusMastery copy() {
        return new RokusMastery(this);
    }
}

enum RokusMasteryCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return GetXValue.instance.calculate(game, source, null) >= 4;
    }
}
