package mage.cards.d;

import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisturbedSlumber extends CardImpl {

    public DisturbedSlumber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Until end of turn, target land you control becomes a 4/4 Dinosaur creature with reach and haste. It's still a land. It must be blocked this turn if able.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(4, 4, "4/4 Dinosaur creature with reach and haste")
                        .withSubType(SubType.DINOSAUR)
                        .withAbility(ReachAbility.getInstance())
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.EndOfTurn
        ).withDurationRuleAtStart(true));
        this.getSpellAbility().addEffect(new MustBeBlockedByAtLeastOneTargetEffect().setText("it must be blocked this turn if able"));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
    }

    private DisturbedSlumber(final DisturbedSlumber card) {
        super(card);
    }

    @Override
    public DisturbedSlumber copy() {
        return new DisturbedSlumber(this);
    }
}
