package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Firestorm extends CardImpl {

    public Firestorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // As an additional cost to cast Firestorm, discard X cards.
        this.getSpellAbility().addCost(new DiscardXTargetCost(new FilterCard("cards"), true));

        // Firestorm deals X damage to each of X target creatures and/or players.
        this.getSpellAbility().addEffect(new FirestormEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private Firestorm(final Firestorm card) {
        super(card);
    }

    @Override
    public Firestorm copy() {
        return new Firestorm(this);
    }
}

class FirestormEffect extends OneShotEffect {

    FirestormEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals X damage to each of X targets";
    }

    private FirestormEffect(final FirestormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        int amount = (GetXValue.instance).calculate(game, source, this);
        if (you != null) {
            if (!source.getTargets().isEmpty()) {
                for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                    Permanent creature = game.getPermanent(targetId);
                    if (creature != null) {
                        creature.damage(amount, source.getSourceId(), source, game, false, true);
                    } else {
                        Player player = game.getPlayer(targetId);
                        if (player != null) {
                            player.damage(amount, source.getSourceId(), source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public FirestormEffect copy() {
        return new FirestormEffect(this);
    }
}
