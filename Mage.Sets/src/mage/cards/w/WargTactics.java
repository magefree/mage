package mage.cards.w;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author muz
 */
public final class WargTactics extends CardImpl {

    public WargTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Choose one --
        // * Destroy target creature with flying.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));

        // * Put a +1/+1 counter on target creature you control. It gains trample and hexproof until end of turn.
        Mode mode = new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        mode.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText("it gains trample"));
        mode.addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance()).setText("and hexproof until end of turn"));
        mode.addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private WargTactics(final WargTactics card) {
        super(card);
    }

    @Override
    public WargTactics copy() {
        return new WargTactics(this);
    }
}
