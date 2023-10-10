
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class PathbreakerIbex extends CardImpl {

    public PathbreakerIbex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.GOAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Pathbreaker Ibex attacks, creatures you control gain trample and get +X/+X until end of turn, where X is the greatest power among creatures you control.
        this.addAbility(new AttacksTriggeredAbility(new PathbreakerIbexEffect(), false));
    }

    private PathbreakerIbex(final PathbreakerIbex card) {
        super(card);
    }

    @Override
    public PathbreakerIbex copy() {
        return new PathbreakerIbex(this);
    }
}

class PathbreakerIbexEffect extends OneShotEffect {

    public PathbreakerIbexEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "creatures you control gain trample and get +X/+X until end of turn, where X is the greatest power among creatures you control";
    }

    private PathbreakerIbexEffect(final PathbreakerIbexEffect effect) {
        super(effect);
    }

    @Override
    public PathbreakerIbexEffect copy() {
        return new PathbreakerIbexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxPower = 0;
        for (Permanent perm : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
            if (perm.getPower().getValue() > maxPower) {
                maxPower = perm.getPower().getValue();
            }
        }
        ContinuousEffect effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        game.addEffect(effect, source);
        if (maxPower != 0) {
            effect = new BoostControlledEffect(maxPower, maxPower, Duration.EndOfTurn);
            game.addEffect(effect, source);
        }
        return true;
    }
}
