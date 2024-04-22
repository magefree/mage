package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2 & L_J
 */
public final class WingStorm extends CardImpl {

    public WingStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Wing Storm deals damage to each player equal to twice the number of creatures with flying that player controls.
        this.getSpellAbility().addEffect(new WingStormEffect());
    }

    private WingStorm(final WingStorm card) {
        super(card);
    }

    @Override
    public WingStorm copy() {
        return new WingStorm(this);
    }
}

class WingStormEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public WingStormEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals damage to each player equal to twice the number of creatures with flying that player controls";
    }

    private WingStormEffect(final WingStormEffect effect) {
        super(effect);
    }

    @Override
    public WingStormEffect copy() {
        return new WingStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                int amount = game.getBattlefield().countAll(filter, playerId, game);
                if (amount > 0) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.damage(amount * 2, source.getSourceId(), source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
