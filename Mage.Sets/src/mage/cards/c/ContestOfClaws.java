package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jimga150
 */
public final class ContestOfClaws extends CardImpl {

    public ContestOfClaws(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");
        

        // Target creature you control deals damage equal to its power to another target creature. If excess damage was dealt this way, discover X, where X is that excess damage.
        this.getSpellAbility().addEffect(new ContestOfClawsDamageEffect());
        Target target = new TargetControlledCreaturePermanent().setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        Target target2 = new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    private ContestOfClaws(final ContestOfClaws card) {
        super(card);
    }

    @Override
    public ContestOfClaws copy() {
        return new ContestOfClaws(this);
    }
}

// Based on Fall of the Hammer
class ContestOfClawsDamageEffect extends OneShotEffect {

    public ContestOfClawsDamageEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target creature you control deals damage equal to its power to another target creature";
    }

    private ContestOfClawsDamageEffect(final ContestOfClawsDamageEffect effect) {
        super(effect);
    }

    @Override
    public ContestOfClawsDamageEffect copy() {
        return new ContestOfClawsDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent ownCreature = game.getPermanent(source.getFirstTarget());
        if (ownCreature != null) {
            int damage = ownCreature.getPower().getValue();
            Permanent targetCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (targetCreature != null) {
                targetCreature.damage(damage, ownCreature.getId(), source, game, false, true);
                return true;
            }
        }
        return false;
    }
}