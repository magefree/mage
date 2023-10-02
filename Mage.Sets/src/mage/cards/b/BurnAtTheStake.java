package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.costs.common.TapVariableTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author North
 */
public final class BurnAtTheStake extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public BurnAtTheStake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}{R}");


        // As an additional cost to cast Burn at the Stake, tap any number of untapped creatures you control.
        this.getSpellAbility().addCost(new TapVariableTargetCost(filter, true, "any number of"));
        // Burn at the Stake deals damage to any target equal to three times the number of creatures tapped this way.
        this.getSpellAbility().addEffect(new BurnAtTheStakeEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private BurnAtTheStake(final BurnAtTheStake card) {
        super(card);
    }

    @Override
    public BurnAtTheStake copy() {
        return new BurnAtTheStake(this);
    }
}

class BurnAtTheStakeEffect extends OneShotEffect {

    public BurnAtTheStakeEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage to any target equal to three times the number of creatures tapped this way";
    }

    private BurnAtTheStakeEffect(final BurnAtTheStakeEffect effect) {
        super(effect);
    }

    @Override
    public BurnAtTheStakeEffect copy() {
        return new BurnAtTheStakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (GetXValue.instance).calculate(game, source, this) * 3;

        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            permanent.damage(amount, source.getSourceId(), source, game, false, true);
            return true;
        }

        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.damage(amount, source.getSourceId(), source, game);
            return true;
        }

        return false;
    }
}
