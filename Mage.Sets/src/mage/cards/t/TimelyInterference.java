package mage.cards.t;

import java.util.UUID;

import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class TimelyInterference extends CardImpl {

    public TimelyInterference(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Kicker {1}{R}
        this.addAbility(new KickerAbility("{1}{R}"));

        // Target creature gets -1/-0 until end of turn. If this spell was kicked, that creature blocks this turn if able.
        // Draw a card.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(-1, 0));
        this.getSpellAbility().addEffect(new ConditionalRequirementEffect(
                new BlocksIfAbleTargetEffect(Duration.EndOfTurn),
                new LockedInCondition(KickedCondition.ONCE),
                "If this spell was kicked, that creature blocks this turn if able.<br>"
        ));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private TimelyInterference(final TimelyInterference card) {
        super(card);
    }

    @Override
    public TimelyInterference copy() {
        return new TimelyInterference(this);
    }
}
