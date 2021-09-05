package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AngelToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DevoutInvocation extends CardImpl {

    static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public DevoutInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}");

        // Tap any number of untapped creatures you control. Create a 4/4 white Angel creature token with flying for each creature tapped this way.
        this.getSpellAbility().addEffect(new DevoutInvocationEffect());
        this.getSpellAbility().addHint(new ValueHint(filter.getMessage(), new PermanentsOnBattlefieldCount(filter)));
    }

    private DevoutInvocation(final DevoutInvocation card) {
        super(card);
    }

    @Override
    public DevoutInvocation copy() {
        return new DevoutInvocation(this);
    }
}

class DevoutInvocationEffect extends OneShotEffect {

    public DevoutInvocationEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Tap any number of untapped creatures you control. Create a 4/4 white Angel creature token with flying for each creature tapped this way";
    }

    public DevoutInvocationEffect(DevoutInvocationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TargetPermanent target = new TargetControlledPermanent(0, Integer.MAX_VALUE, DevoutInvocation.filter, true);
        controller.choose(outcome, target, source.getSourceId(), game);
        if (target.getTargets().isEmpty()) {
            return false;
        }

        int tappedAmount = 0;
        for (UUID permanentId : target.getTargets()) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null && permanent.tap(source, game)) {
                tappedAmount++;
            }
        }

        if (tappedAmount > 0) {
            AngelToken angelToken = new AngelToken();
            angelToken.putOntoBattlefield(tappedAmount, game, source, source.getControllerId());
        }

        return true;
    }

    @Override
    public DevoutInvocationEffect copy() {
        return new DevoutInvocationEffect(this);
    }

}
