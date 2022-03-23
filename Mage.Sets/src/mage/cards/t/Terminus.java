package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class Terminus extends CardImpl {

    public Terminus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Put all creatures on the bottom of their owners' libraries.
        this.getSpellAbility().addEffect(new TerminusEffect());
        // Miracle {W}
        this.addAbility(new MiracleAbility(this, new ManaCostsImpl("{W}")));
    }

    private Terminus(final Terminus card) {
        super(card);
    }

    @Override
    public Terminus copy() {
        return new Terminus(this);
    }
}

class TerminusEffect extends OneShotEffect {

    public TerminusEffect() {
        super(Outcome.Removal);
        this.staticText = "Put all creatures on the bottom of their owners' libraries";
    }

    public TerminusEffect(final TerminusEffect effect) {
        super(effect);
    }

    @Override
    public TerminusEffect copy() {
        return new TerminusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent();
                filter.add(new OwnerIdPredicate(player.getId()));
                Cards toLib = new CardsImpl();
                for (Permanent permanent : game.getBattlefield()
                        .getActivePermanents(filter, source.getControllerId(), source, game)) {
                    toLib.add(permanent);
                }
                player.putCardsOnBottomOfLibrary(toLib, game, source, true);
            }
        }
        return true;
    }
}
