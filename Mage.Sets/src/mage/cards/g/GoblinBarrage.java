
package mage.cards.g;

import java.util.UUID;
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
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class GoblinBarrage extends CardImpl {

    public GoblinBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Kicker—Sacrifice an artifact or Goblin.
        FilterControlledPermanent filter = new FilterControlledPermanent("an artifact or Goblin");
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new SubtypePredicate(SubType.GOBLIN)));
        this.addAbility(new KickerAbility(new SacrificeTargetCost(new TargetControlledPermanent(filter))));

        // Goblin Barrage deals 4 damage to target creature. If this spell was kicked, it also deals 4 damage to target player or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4)
                .setText("{this} deals 4 damage to target creature. If this spell was kicked, "
                        + "it also deals 4 damage to target player or planeswalker")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (KickedCondition.instance.apply(game, ability)) {
            ability.addTarget(new TargetPlayerOrPlaneswalker());
        }
    }

    public GoblinBarrage(final GoblinBarrage card) {
        super(card);
    }

    @Override
    public GoblinBarrage copy() {
        return new GoblinBarrage(this);
    }
}
