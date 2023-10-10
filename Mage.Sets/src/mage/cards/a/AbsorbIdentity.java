package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.EmptyCopyApplier;

/**
 *
 * @author weirddan455
 */
public final class AbsorbIdentity extends CardImpl {

    public AbsorbIdentity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());

        // You may have Shapeshifters you control become copies of that creature until end of turn.
        this.getSpellAbility().addEffect(new AbsorbIdentityEffect());

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AbsorbIdentity(final AbsorbIdentity card) {
        super(card);
    }

    @Override
    public AbsorbIdentity copy() {
        return new AbsorbIdentity(this);
    }
}

class AbsorbIdentityEffect extends OneShotEffect {

    public AbsorbIdentityEffect() {
        super(Outcome.Copy);
        this.staticText = "You may have Shapeshifters you control become copies of that creature until end of turn.";
    }

    private AbsorbIdentityEffect(final AbsorbIdentityEffect effect) {
        super(effect);
    }

    @Override
    public AbsorbIdentityEffect copy() {
        return new AbsorbIdentityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent copyFrom = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
        if (controller == null || copyFrom == null) {
            return false;
        }
        if (controller.chooseUse(outcome, "Have Shapeshifters you control become copies of "
                + copyFrom.getLogName() + " until end of turn?", source, game)) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("shapeshifter");
            filter.add(SubType.SHAPESHIFTER.getPredicate());
            for (Permanent copyTo : game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
                game.copyPermanent(Duration.EndOfTurn, copyFrom, copyTo.getId(), source, new EmptyCopyApplier());
            }
        }
        return true;
    }
}
