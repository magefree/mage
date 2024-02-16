package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Typhoon extends CardImpl {

    public Typhoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Typhoon deals damage to each opponent equal to the number of Islands that player controls.
        this.getSpellAbility().addEffect(new TyphoonEffect());
    }

    private Typhoon(final Typhoon card) {
        super(card);
    }

    @Override
    public Typhoon copy() {
        return new Typhoon(this);
    }
}

class TyphoonEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    TyphoonEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage to each opponent equal to the number of Islands that player controls";
    }

    private TyphoonEffect(final TyphoonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (!playerId.equals(source.getControllerId())) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int amount = 0;
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                        amount++;
                    }
                    if (amount > 0) {
                        player.damage(amount, source.getSourceId(), source, game);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public TyphoonEffect copy() {
        return new TyphoonEffect(this);
    }
}
