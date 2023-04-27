package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class PhyrexianPurge extends CardImpl {

    public PhyrexianPurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{R}");

        // Destroy any number of target creatures.
        // Phyrexian Purge costs 3 life more to cast for each target.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        this.getSpellAbility().addEffect(new InfoEffect("this spell costs 3 life more to cast for each target"));
        this.getSpellAbility().addEffect(new DestroyTargetEffect().concatBy("<br>").setText("destroy any number of target creatures"));
        this.getSpellAbility().setTargetAdjuster(PhyrexianPurgeTargetAdjuster.instance);
        this.getSpellAbility().setCostAdjuster(PhyrexianPurgeCostAdjuster.instance);
    }

    private PhyrexianPurge(final PhyrexianPurge card) {
        super(card);
    }

    @Override
    public PhyrexianPurge copy() {
        return new PhyrexianPurge(this);
    }
}

enum PhyrexianPurgeCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int numTargets = ability.getTargets().get(0).getTargets().size();
        if (numTargets > 0) {
            ability.getCosts().add(new PayLifeCost(numTargets * 3));
        }
    }
}

enum PhyrexianPurgeTargetAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        Player you = game.getPlayer(ability.getControllerId());
        int maxTargets = you.getLife() / 3;
        ability.addTarget(new TargetCreaturePermanent(0, maxTargets));
    }
}
