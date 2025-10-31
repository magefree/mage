package mage.cards.o;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OriginOfMetalbending extends CardImpl {

    public OriginOfMetalbending(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        this.subtype.add(SubType.LESSON);

        // Choose one --
        // * Destroy target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        // * Put a +1/+1 counter on target creature you control. It gains indestructible until end of turn.
        this.getSpellAbility().addMode(new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance()))
                .addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                        .setText("It gains indestructible until end of turn"))
                .addTarget(new TargetControlledCreaturePermanent()));
    }

    private OriginOfMetalbending(final OriginOfMetalbending card) {
        super(card);
    }

    @Override
    public OriginOfMetalbending copy() {
        return new OriginOfMetalbending(this);
    }
}
