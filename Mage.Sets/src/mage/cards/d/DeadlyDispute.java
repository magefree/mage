package mage.cards.d;

import java.util.UUID;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author weirddan455
 */
public final class DeadlyDispute extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact or creature");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
    }

    public DeadlyDispute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // As an additional cost to cast this spell, sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));

        // Draw two cards and create a Treasure token.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("and"));
    }

    private DeadlyDispute(final DeadlyDispute card) {
        super(card);
    }

    @Override
    public DeadlyDispute copy() {
        return new DeadlyDispute(this);
    }
}
