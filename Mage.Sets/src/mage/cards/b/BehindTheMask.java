package mage.cards.b;

import mage.abilities.condition.common.CollectedEvidenceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.CollectEvidenceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class BehindTheMask extends CardImpl {

    public BehindTheMask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // As an additional cost to cast this spell, you may collect evidence 6.
        this.addAbility(new CollectEvidenceAbility(6));

        // Until end of turn, target artifact or creature becomes an artifact creature with base power and toughness 4/3.
        // If evidence was collected, it has base power and toughness 1/1 until end of turn instead.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BecomesCreatureTargetEffect(new CreatureToken(1, 1).withType(CardType.ARTIFACT),
                        false, false, Duration.EndOfTurn
                ),
                new BecomesCreatureTargetEffect(new CreatureToken(4, 3).withType(CardType.ARTIFACT),
                        false, false, Duration.EndOfTurn
                ),
                CollectedEvidenceCondition.instance,
                "Until end of turn, target artifact or creature becomes an artifact creature with base power and toughness 4/3. " +
                        "If evidence was collected, it has base power and toughness 1/1 until end of turn instead."
        ));

    }

    private BehindTheMask(final BehindTheMask card) {
        super(card);
    }

    @Override
    public BehindTheMask copy() {
        return new BehindTheMask(this);
    }
}
