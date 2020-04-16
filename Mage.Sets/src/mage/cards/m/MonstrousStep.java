package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.BlockedAttackerWatcher;

/**
 *
 * @author drowinternet
 */
public final class MonstrousStep extends CardImpl {

    private static final FilterCreaturePermanent filterMustBlock = new FilterCreaturePermanent("Target creature that must block");
    private static final FilterCreaturePermanent filterToBeBlocked = new FilterCreaturePermanent("Creature that will be block");

    public MonstrousStep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");


        // Target creature gets +7/+7 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(7,7, Duration.EndOfTurn)
                .setText("Target creature gets +7/+7 until end of turn."));

        //Up to one target creature blocks it this turn if able.
        this.getSpellAbility().addEffect(new MonstrousStepEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterMustBlock));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterToBeBlocked));
        this.getSpellAbility().addWatcher(new BlockedAttackerWatcher());


        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));

    }

    private MonstrousStep(final MonstrousStep card) {
        super(card);
    }

    @Override
    public MonstrousStep copy() {
        return new MonstrousStep(this);
    }
}

class MonstrousStepEffect extends RequirementEffect {

    public MonstrousStepEffect() {
        this(Duration.EndOfTurn);
    }

    public MonstrousStepEffect(Duration duration) {
        super(duration);
        staticText = "Up to one target creature blocks it this turn if able.";
    }

    public MonstrousStepEffect(final MonstrousStepEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getTargets().get(0).getFirstTarget())) {
            Permanent blocker = game.getPermanent(source.getTargets().get(0).getFirstTarget());
            if (blocker != null
                    && blocker.canBlock(source.getTargets().get(1).getFirstTarget(), game)) {
                Permanent attacker = game.getPermanent(source.getTargets().get(1).getFirstTarget());
                if (attacker != null) {
                    BlockedAttackerWatcher blockedAttackerWatcher = game.getState().getWatcher(BlockedAttackerWatcher.class);
                    if (blockedAttackerWatcher != null
                            && blockedAttackerWatcher.creatureHasBlockedAttacker(attacker, blocker, game)) {
                        // has already blocked this turn, so no need to do again
                        return false;
                    }
                    return true;
                } else {
                    discard();
                }
            }
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        return source.getTargets().get(1).getFirstTarget();
    }

    @Override
    public MonstrousStepEffect copy() {
        return new MonstrousStepEffect(this);
    }

}