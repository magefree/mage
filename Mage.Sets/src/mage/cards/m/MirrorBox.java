package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.ruleModifying.LegendRuleDoesntApplyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrorBox extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public MirrorBox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // The "legend rule" doesn't apply to permanents you control.
        this.addAbility(new SimpleStaticAbility(new LegendRuleDoesntApplyEffect(
                StaticFilters.FILTER_CONTROLLED_PERMANENTS
        )));

        // Each legendary creature you control gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter
        )));

        // Each nontoken creature you control gets +1/+1 for each other creature you control with the same name as that creature.
        this.addAbility(new SimpleStaticAbility(new MirrorBoxBoostEffect()));
    }

    private MirrorBox(final MirrorBox card) {
        super(card);
    }

    @Override
    public MirrorBox copy() {
        return new MirrorBox(this);
    }
}

class MirrorBoxBoostEffect extends ContinuousEffectImpl {

    public MirrorBoxBoostEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "each nontoken creature you control gets +1/+1 for " +
                "each other creature you control with the same name as that creature";
    }

    public MirrorBoxBoostEffect(final MirrorBoxBoostEffect effect) {
        super(effect);
    }

    @Override
    public MirrorBoxBoostEffect copy() {
        return new MirrorBoxBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        );
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, source.getControllerId(), source, game
        )) {
            int amount = getAmount(permanents, permanent, game);
            permanent.addPower(amount);
            permanent.addToughness(amount);
        }
        return true;
    }

    private int getAmount(List<Permanent> permanents, Permanent target, Game game) {
        return permanents
                .stream()
                .filter(permanent -> !permanent.getId().equals(target.getId())
                        && CardUtil.haveSameNames(permanent, target))
                .mapToInt(x -> 1)
                .sum();
    }
}
