package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.BlockedAttackerWatcher;

import java.util.UUID;

/**
 * @author drowinternet
 */
public final class MonstrousStep extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another creature (must block this turn)");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public MonstrousStep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Target creature gets +7/+7 until end of turn. Up to one target creature blocks it this turn if able.
        this.getSpellAbility().addEffect(new BoostTargetEffect(7, 7));
        this.getSpellAbility().addEffect(new MonstrousStepEffect());
        this.getSpellAbility().addWatcher(new BlockedAttackerWatcher());

        TargetPermanent target = new TargetCreaturePermanent();
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);
        target = new TargetPermanent(0, 1, filter, false);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
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

    MonstrousStepEffect() {
        super(Duration.EndOfTurn);
        staticText = "Up to one other target creature blocks it this turn if able";
    }

    private MonstrousStepEffect(final MonstrousStepEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        BlockedAttackerWatcher watcher = game.getState().getWatcher(BlockedAttackerWatcher.class);
        return permanent != null
                && watcher != null
                && !watcher.creatureHasBlockedAttacker(game.getPermanent(source.getFirstTarget()), permanent, game)
                && permanent.getId().equals(source.getTargets().get(1).getFirstTarget())
                && permanent.canBlock(source.getFirstTarget(), game);
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
        return source.getFirstTarget();
    }

    @Override
    public MonstrousStepEffect copy() {
        return new MonstrousStepEffect(this);
    }
}
