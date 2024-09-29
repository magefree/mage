package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class EchoingRuin extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("artifact");


    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public EchoingRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Destroy target artifact and all other artifacts with the same name as that artifact.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new EchoingRuinEffect());
    }

    private EchoingRuin(final EchoingRuin card) {
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

    private EchoingRuinEffect(final EchoingRuinEffect effect) {
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
            permanent.destroy(source, game, false);
            if (!CardUtil.haveEmptyName(permanent)) { // in case of face down artifact creature
                for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                    if (!perm.getId().equals(permanent.getId()) && CardUtil.haveSameNames(perm, permanent) && perm.isArtifact(game)) {
                        perm.destroy(source, game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
