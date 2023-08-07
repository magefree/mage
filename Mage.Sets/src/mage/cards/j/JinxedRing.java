
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;

import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class JinxedRing extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a nontoken permanent");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public JinxedRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever a nontoken permanent is put into your graveyard from the battlefield, Jinxed Ring deals 1 damage to you.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new DamageControllerEffect(1), false, filter, false, true));

        // Sacrifice a creature: Target opponent gains control of Jinxed Ring.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JinxedRingEffect(),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private JinxedRing(final JinxedRing card) {
        super(card);
    }

    @Override
    public JinxedRing copy() {
        return new JinxedRing(this);
    }
}

class JinxedRingEffect extends ContinuousEffectImpl {

    public JinxedRingEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "Target opponent gains control of {this}";
    }

    public JinxedRingEffect(final JinxedRingEffect effect) {
        super(effect);
    }

    @Override
    public JinxedRingEffect copy() {
        return new JinxedRingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            return permanent.changeControllerId(source.getFirstTarget(), game, source);
        } else {
            discard();
        }
        return false;
    }

}
