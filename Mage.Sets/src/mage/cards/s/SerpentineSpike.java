package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DamagedByWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SerpentineSpike extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(new AnotherTargetPredicate(3));
    }

    public SerpentineSpike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Serpentine Spike deals 2 damage to target creature, 3 damage to another target creature, and 4 damage to a third target creature. If a creature dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new SerpentineSpikeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("2 damage").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).withChooseHint("3 damage").setTargetTag(2));
        this.getSpellAbility().addTarget(new TargetPermanent(filter).withChooseHint("4 damage").setTargetTag(3));

        this.getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn)
                .setText("If a creature dealt damage this way would die this turn, exile it instead"));
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private SerpentineSpike(final SerpentineSpike card) {
        super(card);
    }

    @Override
    public SerpentineSpike copy() {
        return new SerpentineSpike(this);
    }
}

class SerpentineSpikeEffect extends OneShotEffect {

    SerpentineSpikeEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to target creature, 3 damage to another target creature, and 4 damage to a third target creature";
    }

    private SerpentineSpikeEffect(final SerpentineSpikeEffect effect) {
        super(effect);
    }

    @Override
    public SerpentineSpikeEffect copy() {
        return new SerpentineSpikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        if (permanent != null) {
            permanent.damage(2, source.getSourceId(), source, game, false, true);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.damage(3, source.getSourceId(), source, game, false, true);
        }
        permanent = game.getPermanent(source.getTargets().get(2).getFirstTarget());
        if (permanent != null) {
            permanent.damage(4, source.getSourceId(), source, game, false, true);
        }
        return true;
    }
}
