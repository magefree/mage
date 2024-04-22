package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class FlamingGambit extends CardImpl {

    public FlamingGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // Flaming Gambit deals X damage to target player or planeswalker. That player or that planeswalker's controller may choose a creature they control and have Flaming Gambit deal that damage to it instead.
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addEffect(new FlamingGambitEffect());

        // Flashback {X}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{X}{R}{R}")));

    }

    private FlamingGambit(final FlamingGambit card) {
        super(card);
    }

    @Override
    public FlamingGambit copy() {
        return new FlamingGambit(this);
    }
}

class FlamingGambitEffect extends OneShotEffect {

    FlamingGambitEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to target player or planeswalker. " +
                "That player or that planeswalker's controller may choose a creature they control and have {this} deal that damage to it instead";
    }

    private FlamingGambitEffect(final FlamingGambitEffect effect) {
        super(effect);
    }

    @Override
    public FlamingGambitEffect copy() {
        return new FlamingGambitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayerOrPlaneswalkerController(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        int damage = ManacostVariableValue.REGULAR.calculate(game, source, this);
        if (game.getBattlefield().count(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), source, game) > 0) {
            String message = "Choose a creature you control to deal " + damage + " damage to instead?";
            if (player.chooseUse(outcome, message, source, game)) {
                Target target = new TargetControlledCreaturePermanent().withNotTarget(true);
                player.choose(outcome, target, source, game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    return permanent.damage(damage, source, game) > 0;
                }
            }
        }
        // No creature chosen; damage player or planeswalker
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            return player.damage(damage, source, game) > 0;
        }
        Permanent planeswalker = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (planeswalker != null) {
            return planeswalker.damage(damage, source, game) > 0;
        }
        return false;
    }
}
