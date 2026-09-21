package mage.cards.f;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class FlourishingGrapple extends CardImpl {

    private static final FilterPermanent filter =
        new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls that's red or white");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.WHITE)
        ));
    }

    public FlourishingGrapple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature or planeswalker an opponent controls that's red or white loses all abilities until end of turn. Target creature you control deals damage equal to its power to that permanent.
        this.getSpellAbility().addEffect(new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetPermanent(filter).setTargetTag(1)
            .withChooseHint("to lose abilities and be damaged"));
        this.getSpellAbility().addEffect(new FlourishingGrappleEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(2)
            .withChooseHint("to deal damage"));
    }

    private FlourishingGrapple(final FlourishingGrapple card) {
        super(card);
    }

    @Override
    public FlourishingGrapple copy() {
        return new FlourishingGrapple(this);
    }
}

class FlourishingGrappleEffect extends OneShotEffect {

    FlourishingGrappleEffect() {
        super(Outcome.Damage);
        staticText = "target creature you control deals damage equal to its power to that permanent";
    }

    private FlourishingGrappleEffect(final FlourishingGrappleEffect effect) {
        super(effect);
    }

    @Override
    public FlourishingGrappleEffect copy() {
        return new FlourishingGrappleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getTargets().getByTag(2).getFirstTarget());
        Permanent targetPermanent = game.getPermanent(source.getTargets().getByTag(1).getFirstTarget());
        if (sourcePermanent == null || targetPermanent == null) {
            return false;
        }
        return targetPermanent.damage(sourcePermanent.getPower().getValue(), source.getSourceId(), source, game) > 0;
    }
}
