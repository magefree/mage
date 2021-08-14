package mage.cards.k;

import java.util.UUID;

import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class KickInTheDoor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.WALL, "Walls");

    public KickInTheDoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Put a +1/+1 counter on target creature. That creature gains haste until end of turn and can't be blocked by Walls this turn. Venture into the dungeon.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(),
                Duration.EndOfTurn,
                "That creature gains haste until end of turn"
        ));
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect(
                filter, Duration.EndOfTurn)
                .setText("and can't be blocked by Walls this turn")
        );
        this.getSpellAbility().addEffect(new VentureIntoTheDungeonEffect());
    }

    private KickInTheDoor(final KickInTheDoor card) {
        super(card);
    }

    @Override
    public KickInTheDoor copy() {
        return new KickInTheDoor(this);
    }
}
