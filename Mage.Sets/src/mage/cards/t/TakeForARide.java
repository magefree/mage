package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CommittedCrimeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.CommittedCrimeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TakeForARide extends CardImpl {

    public TakeForARide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Take for a Ride has flash as long as you've committed a crime this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                        FlashAbility.getInstance(), Duration.WhileOnBattlefield, true
                ), CommittedCrimeCondition.instance, "{this} has flash as long as you've committed a crime this turn")
        ).setRuleAtTheTop(true).addHint(CommittedCrimeCondition.getHint()), new CommittedCrimeWatcher());

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TakeForARide(final TakeForARide card) {
        super(card);
    }

    @Override
    public TakeForARide copy() {
        return new TakeForARide(this);
    }
}
