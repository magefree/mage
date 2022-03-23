package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Blustersquall extends CardImpl {

    public Blustersquall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Tap target creature you don't control.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addEffect(new TapTargetEffect());

        // Overload {3}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        this.addAbility(new OverloadAbility(this, new BlustersqallTapAllEffect(), new ManaCostsImpl("{3}{U}")));
    }

    private Blustersquall(final Blustersquall card) {
        super(card);
    }

    @Override
    public Blustersquall copy() {
        return new Blustersquall(this);
    }
}

class BlustersqallTapAllEffect extends OneShotEffect {

    BlustersqallTapAllEffect() {
        super(Outcome.Tap);
        staticText = "Tap each creature you don't control";
    }

    private BlustersqallTapAllEffect(final BlustersqallTapAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, source.getControllerId(), source, game)) {
            creature.tap(source, game);
        }
        return true;
    }

    @Override
    public BlustersqallTapAllEffect copy() {
        return new BlustersqallTapAllEffect(this);
    }
}
