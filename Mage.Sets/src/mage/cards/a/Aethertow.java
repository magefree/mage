
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Aethertow extends CardImpl {

    private static final FilterAttackingOrBlockingCreature filter = new FilterAttackingOrBlockingCreature();

    public Aethertow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W/U}");

        // Put target attacking or blocking creature on top of its owner's library.
        this.getSpellAbility().addEffect(new AethertowEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Conspire
        this.addAbility(new ConspireAbility(ConspireAbility.ConspireTargets.ONE));
    }

    private Aethertow(final Aethertow card) {
        super(card);
    }

    @Override
    public Aethertow copy() {
        return new Aethertow(this);
    }
}

class AethertowEffect extends OneShotEffect {

    AethertowEffect() {
        super(Outcome.Removal);
        staticText = "Put target attacking or blocking creature on top of its owner's library";
    }

    AethertowEffect(final AethertowEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (targetCreature != null) {
            return controller.putCardsOnTopOfLibrary(targetCreature, game, source, true);
        }
        return false;
    }

    @Override
    public AethertowEffect copy() {
        return new AethertowEffect(this);
    }
}
