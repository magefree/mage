package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class SavageOffensive extends CardImpl {

    public SavageOffensive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Kicker {G}
        this.addAbility(new KickerAbility("{G}"));

        // Creatures you control gain first strike until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES));

        // If Savage Offensive was kicked, they get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostControlledEffect(1, 1, Duration.EndOfTurn)),
                new LockedInCondition(KickedCondition.ONCE),
                "if this spell was kicked, they get +1/+1 until end of turn."));
    }

    private SavageOffensive(final SavageOffensive card) {
        super(card);
    }

    @Override
    public SavageOffensive copy() {
        return new SavageOffensive(this);
    }
}
