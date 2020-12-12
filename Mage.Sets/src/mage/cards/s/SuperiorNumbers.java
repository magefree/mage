package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuperiorNumbers extends CardImpl {

    public SuperiorNumbers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}");

        // Superior Numbers deals damage to target creature equal to the number of creatures you control in excess of the number of creatures target opponent controls.
        this.getSpellAbility().addEffect(new SuperiorNumbersEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private SuperiorNumbers(final SuperiorNumbers card) {
        super(card);
    }

    @Override
    public SuperiorNumbers copy() {
        return new SuperiorNumbers(this);
    }
}

class SuperiorNumbersEffect extends OneShotEffect {

    SuperiorNumbersEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage to target creature equal to the number of creatures you control " +
                "in excess of the number of creatures target opponent controls.";
    }

    private SuperiorNumbersEffect(final SuperiorNumbersEffect effect) {
        super(effect);
    }

    @Override
    public SuperiorNumbersEffect copy() {
        return new SuperiorNumbersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        Player opponent = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (creature == null || opponent == null || controller == null) {
            return false;
        }
        int controllerCreatures = game.getBattlefield().countAll(
                StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game
        );
        int opponentCreatures = game.getBattlefield().countAll(
                StaticFilters.FILTER_PERMANENT_CREATURE, opponent.getId(), game
        );
        int damage = Math.max(controllerCreatures - opponentCreatures, 0);
        return creature.damage(damage, source.getSourceId(), source, game) > 0;
    }
}