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
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlueSunsTwilight extends CardImpl {

    public BlueSunsTwilight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Gain control of target creature with mana value X or less. If X is 5 or more, create a token that's a copy of that creature.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom, true));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenCopyTargetEffect(), BlueSunsTwilightCondition.instance,
                "If X is 5 or more, create a token that's a copy of that creature"
        ));
        this.getSpellAbility().setTargetAdjuster(BlueSunsTwilightAdjuster.instance);
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

enum BlueSunsTwilightAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        FilterPermanent filter = new FilterCreaturePermanent("creature with mana value " + xValue + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(filter));
    }
}
