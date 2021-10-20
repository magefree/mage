
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class DevouringRage extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("any number of Spirits");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public DevouringRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}");
        this.subtype.add(SubType.ARCANE);


        // As an additional cost to cast Devouring Rage, you may sacrifice any number of Spirits.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true)));

        // Target creature gets +3/+0 until end of turn. For each Spirit sacrificed this way, that creature gets an additional +3/+0 until end of turn
        this.getSpellAbility().addEffect(new DevouringRageEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private DevouringRage(final DevouringRage card) {
        super(card);
    }

    @Override
    public DevouringRage copy() {
        return new DevouringRage(this);
    }
}

class DevouringRageEffect extends OneShotEffect {

    public DevouringRageEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Target creature gets +3/+0 until end of turn. For each Spirit sacrificed this way, that creature gets an additional +3/+0 until end of turn";
    }

    public DevouringRageEffect(final DevouringRageEffect effect) {
        super(effect);
    }

    @Override
    public DevouringRageEffect copy() {
        return new DevouringRageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numberSpirits = 0;
        for (Cost cost :source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                numberSpirits = ((SacrificeTargetCost) cost).getPermanents().size();
            }
        }
        int amount = 3 + (numberSpirits * 3);
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            ContinuousEffect effect = new BoostTargetEffect(amount, 0, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(targetCreature.getId(), game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
