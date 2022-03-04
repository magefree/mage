package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsChoicePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Arena extends CardImpl {

    public Arena(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {3}, {tap}: Tap target creature you control and target creature of an opponent's choice they control. Those creatures fight each other.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ArenaEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetOpponentsChoicePermanent(1, 1, new FilterControlledCreaturePermanent(), false));
        this.addAbility(ability);
    }

    private Arena(final Arena card) {
        super(card);
    }

    @Override
    public Arena copy() {
        return new Arena(this);
    }
}

class ArenaEffect extends OneShotEffect {

    ArenaEffect() {
        super(Outcome.Benefit);
        this.staticText = "Tap target creature you control and target creature of an opponent's choice they control. " +
                "Those creatures fight each other. <i>(Each deals damage equal to its power to the other.)</i>";
    }

    ArenaEffect(final ArenaEffect effect) {
        super(effect);
    }

    @Override
    public ArenaEffect copy() {
        return new ArenaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            creature.tap(source, game);
        }
        creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null) {
            creature.tap(source, game);
        }
        return new FightTargetsEffect().apply(game, source);
    }
}
