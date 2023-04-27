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
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetAmount;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetCreaturePermanentAmount;

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
        staticText = ", then distribute four +1/+1 counters among "
                + "any number of creatures and/or Vehicles target player controls";
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
        FilterCreaturePermanent filter = new FilterCreaturePermanent(
                "creatures and/or Vehicles controlled by " + player.getName()
        );
        filter.add(new ControllerIdPredicate(player.getId()));
        if (!game.getBattlefield().contains(filter, source, game, 1)) {
            return false;
        }
        // todo: the pop-up window for assigning the amount of counters uses the damage GUI.  it should have its own GUI for assigning counters
        TargetAmount target = new TargetCreaturePermanentAmount(4, filter);
        target.setNotTarget(true);
        // note, when using TargetAmount, the target must be used to embed the chosen creatures in this case
        target.chooseTarget(outcome, player.getId(), source, game);
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(target.getTargetAmount(targetId)), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}
