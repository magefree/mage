package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessAllEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MassDiminish extends CardImpl {

    public MassDiminish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Until your next turn, creatures target player controls have base power and toughness 1/1.
        this.getSpellAbility().addEffect(new MassDiminishEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Flashback {3}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{U}")));
    }

    private MassDiminish(final MassDiminish card) {
        super(card);
    }

    @Override
    public MassDiminish copy() {
        return new MassDiminish(this);
    }
}

class MassDiminishEffect extends OneShotEffect {

    MassDiminishEffect() {
        super(Outcome.Benefit);
        staticText = "until your next turn, creatures target player controls have base power and toughness 1/1";
    }

    private MassDiminishEffect(final MassDiminishEffect effect) {
        super(effect);
    }

    @Override
    public MassDiminishEffect copy() {
        return new MassDiminishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(source.getFirstTarget()));
        game.addEffect(new SetPowerToughnessAllEffect(
                1, 1, Duration.UntilYourNextTurn, filter, true
        ), source);
        return true;
    }
}