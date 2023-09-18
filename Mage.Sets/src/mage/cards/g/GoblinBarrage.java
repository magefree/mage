
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GoblinBarrage extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact or Goblin");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                SubType.GOBLIN.getPredicate()
        ));
    }

    public GoblinBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Kicker—Sacrifice an artifact or Goblin.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(new TargetControlledPermanent(filter))));

        // Goblin Barrage deals 4 damage to target creature. If this spell was kicked, it also deals 4 damage to target player or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4)
                .setText("{this} deals 4 damage to target creature. If this spell was kicked, "
                        + "it also deals 4 damage to target player or planeswalker")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(GoblinBarrageAdjuster.instance);
    }

    private GoblinBarrage(final GoblinBarrage card) {
        super(card);
    }

    @Override
    public GoblinBarrage copy() {
        return new GoblinBarrage(this);
    }
}

enum GoblinBarrageAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (KickedCondition.ONCE.apply(game, ability)) {
            ability.addTarget(new TargetPlayerOrPlaneswalker());
        }
    }
}