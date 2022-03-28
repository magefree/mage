
package mage.cards.t;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class TheFirstEruption extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public TheFirstEruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — The First Eruption deals 1 damage to each creature without flying.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new DamageAllEffect(1, filter));

        // II — Add {R}{R}.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new BasicManaEffect(Mana.RedMana(2)));

        // III — Sacrifice a Mountain. If you do, The First Eruption deals 3 damage to each creature.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheFirstEruptionEffect());
        this.addAbility(sagaAbility);
    }

    private TheFirstEruption(final TheFirstEruption card) {
        super(card);
    }

    @Override
    public TheFirstEruption copy() {
        return new TheFirstEruption(this);
    }
}

class TheFirstEruptionEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Mountain");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    TheFirstEruptionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Sacrifice a Mountain. If you do, {this} deals 3 damage to each creature";
    }

    TheFirstEruptionEffect(final TheFirstEruptionEffect effect) {
        super(effect);
    }

    @Override
    public TheFirstEruptionEffect copy() {
        return new TheFirstEruptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller == null) {
            return false;
        }

        Target target = new TargetControlledPermanent(1, 1, filter, false);
        boolean sacrificed = false;
        if (target.canChoose(controller.getId(), source, game)) {
            while (controller.canRespond() && !target.isChosen() && target.canChoose(controller.getId(), source, game)) {
                controller.chooseTarget(Outcome.Sacrifice, target, source, game);
            }

            for (int idx = 0; idx < target.getTargets().size(); idx++) {
                Permanent permanent = game.getPermanent(target.getTargets().get(idx));
                if (permanent != null) {
                    sacrificed |= permanent.sacrifice(source, game);
                }
            }
        }

        if (sacrificed) {
            return new DamageAllEffect(3, StaticFilters.FILTER_PERMANENT_CREATURE).apply(game, source);
        }
        return false;
    }
}
