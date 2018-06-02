
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Mirrorweave extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonlegendary creature");

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.LEGENDARY)));
    }

    public Mirrorweave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W/U}{W/U}");

        // Each other creature becomes a copy of target nonlegendary creature until end of turn.
        this.getSpellAbility().addEffect(new MirrorWeaveEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

    }

    public Mirrorweave(final Mirrorweave card) {
        super(card);
    }

    @Override
    public Mirrorweave copy() {
        return new Mirrorweave(this);
    }
}

class MirrorWeaveEffect extends OneShotEffect {

    public MirrorWeaveEffect() {
        super(Outcome.Copy);
        this.staticText = "Each other creature becomes a copy of target nonlegendary creature until end of turn";
    }

    public MirrorWeaveEffect(final MirrorWeaveEffect effect) {
        super(effect);
    }

    @Override
    public MirrorWeaveEffect copy() {
        return new MirrorWeaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        FilterCreaturePermanent filter = new FilterCreaturePermanent();

        if (controller != null) {
            Permanent copyFromCreature = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
            if (copyFromCreature != null) {
                filter.add(Predicates.not(new PermanentIdPredicate(copyFromCreature.getId())));
                for (Permanent copyToCreature : game.getBattlefield().getAllActivePermanents(filter, game)) {
                    if (copyToCreature != null) {
                        game.copyPermanent(Duration.EndOfTurn, copyFromCreature, copyToCreature.getId(), source, new EmptyApplyToPermanent());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
