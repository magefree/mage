package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetAmount;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetPermanentAmount;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvokeJustice extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public InvokeJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}{W}{W}");

        // Return target permanent card from your graveyard to the battlefield, then distribute four +1/+1 counters among any number of creatures and/or Vehicles target player controls.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new InvokeJusticeEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private InvokeJustice(final InvokeJustice card) {
        super(card);
    }

    @Override
    public InvokeJustice copy() {
        return new InvokeJustice(this);
    }
}

class InvokeJusticeEffect extends OneShotEffect {

    InvokeJusticeEffect() {
        super(Outcome.Benefit);
        staticText = ", then distribute four +1/+1 counters among " +
                "any number of creatures and/or Vehicles target player controls";
        this.setTargetPointer(new SecondTargetPointer());
    }

    private InvokeJusticeEffect(final InvokeJusticeEffect effect) {
        super(effect);
    }

    @Override
    public InvokeJusticeEffect copy() {
        return new InvokeJusticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent(
                "creatures and/or Vehicles controlled by " + player.getName()
        );
        filter.add(new ControllerIdPredicate(player.getId()));
        if (!game.getBattlefield().contains(filter, source, game, 1)) {
            return false;
        }
        TargetAmount target = new TargetPermanentAmount(4, filter);
        target.setNotTarget(true);
        controller.choose(outcome, target, source, game);
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(target.getTargetAmount(targetId)), source, game);
            }
        }
        return true;
    }
}
