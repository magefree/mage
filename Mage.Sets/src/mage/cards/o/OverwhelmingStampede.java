
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class OverwhelmingStampede extends CardImpl {

    public OverwhelmingStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Until end of turn, creatures you control gain trample and get +X/+X, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new OverwhelmingStampedeInitEffect());
    }

    private OverwhelmingStampede(final OverwhelmingStampede card) {
        super(card);
    }

    @Override
    public OverwhelmingStampede copy() {
        return new OverwhelmingStampede(this);
    }
}

class OverwhelmingStampedeInitEffect extends OneShotEffect {

    public OverwhelmingStampedeInitEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Until end of turn, creatures you control gain trample and get +X/+X, where X is the greatest power among creatures you control";
    }

    private OverwhelmingStampedeInitEffect(final OverwhelmingStampedeInitEffect effect) {
        super(effect);
    }

    @Override
    public OverwhelmingStampedeInitEffect copy() {
        return new OverwhelmingStampedeInitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxPower = 0;
        for (Permanent perm : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
            if (perm.getPower().getValue() > maxPower) {
                maxPower = perm.getPower().getValue();
            }
        }
        ContinuousEffect effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent());
        game.addEffect(effect, source);
        if (maxPower != 0) {
            effect = new BoostControlledEffect(maxPower, maxPower, Duration.EndOfTurn);
            game.addEffect(effect, source);
        }
        return true;
    }
}
