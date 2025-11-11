package mage.cards.u;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnagisSpray extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(
                SubType.FISH.getPredicate(),
                SubType.OCTOPUS.getPredicate(),
                SubType.OTTER.getPredicate(),
                SubType.SEAL.getPredicate(),
                SubType.SERPENT.getPredicate(),
                SubType.WHALE.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a Fish, Octopus, Otter, Seal, Serpent, or Whale");

    public UnagisSpray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Target creature gets -4/-0 until end of turn. If you control a Fish, Octopus, Otter, Seal, Serpent, or Whale, draw a card.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4, 0));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), condition,
                "If you control a Fish, Octopus, Otter, Seal, Serpent, or Whale, draw a card"
        ));
        this.getSpellAbility().addHint(hint);
    }

    private UnagisSpray(final UnagisSpray card) {
        super(card);
    }

    @Override
    public UnagisSpray copy() {
        return new UnagisSpray(this);
    }
}
