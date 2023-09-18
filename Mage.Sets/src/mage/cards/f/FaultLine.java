package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class FaultLine extends CardImpl {

    public FaultLine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");

        // Fault Line deals X damage to each creature without flying and each player.
        this.getSpellAbility().addEffect(new FaultLineEffect());
    }

    private FaultLine(final FaultLine card) {
        super(card);
    }

    @Override
    public FaultLine copy() {
        return new FaultLine(this);
    }
}

class FaultLineEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public FaultLineEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to each creature without flying and each player";
    }

    private FaultLineEffect(final FaultLineEffect effect) {
        super(effect);
    }

    @Override
    public FaultLineEffect copy() {
        return new FaultLineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            permanent.damage(amount, source.getSourceId(), source, game, false, true);
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null)
                player.damage(amount, source.getSourceId(), source, game);
        }
        return true;
    }

}