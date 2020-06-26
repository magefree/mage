package mage.cards.n;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NaturesWay extends CardImpl {

    public NaturesWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Target creature you control gains vigilance and trample until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn)
                .setText("Target creature you control gains vigilance"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("and trample until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // It deals damage equal to its power to target creature you don't control.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("It"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL)); // second target
    }

    private NaturesWay(final NaturesWay card) {
        super(card);
    }

    @Override
    public NaturesWay copy() {
        return new NaturesWay(this);
    }
}
