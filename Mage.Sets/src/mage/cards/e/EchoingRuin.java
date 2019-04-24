
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class EchoingRuin extends CardImpl {
        private static final FilterPermanent filter = new FilterPermanent("artifact");

    
    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public EchoingRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Destroy target artifact and all other artifacts with the same name as that artifact.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new EchoingRuinEffect());
    }

    public EchoingRuin(final EchoingRuin card) {
        super(card);
    }

    @Override
    public EchoingRuin copy() {
        return new EchoingRuin(this);
    }
}

class EchoingRuinEffect extends OneShotEffect {
    EchoingRuinEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target artifact and all other artifacts with the same name as that artifact";
    }

    EchoingRuinEffect(final EchoingRuinEffect effect) {
        super(effect);
    }

    @Override
    public EchoingRuinEffect copy() {
        return new EchoingRuinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            permanent.destroy(source.getSourceId(), game, false);
            if (!permanent.getName().isEmpty()) { // in case of face down artifact creature
                for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                    if (!perm.getId().equals(permanent.getId()) && perm.getName().equals(permanent.getName()) && perm.isArtifact()) {
                        perm.destroy(source.getSourceId(), game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
