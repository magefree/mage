package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class FlameFusillade extends CardImpl {

    public FlameFusillade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Until end of turn, permanents you control gain "{tap}: This permanent deals 1 damage to any target."
        Ability gainedAbility = new SimpleActivatedAbility(new DamageTargetEffect(1, "this permanent"), new TapSourceCost());
        gainedAbility.addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(gainedAbility, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENTS).withDurationRuleAtStart(true));
    }

    private FlameFusillade(final FlameFusillade card) {
        super(card);
    }

    @Override
    public FlameFusillade copy() {
        return new FlameFusillade(this);
    }
}
