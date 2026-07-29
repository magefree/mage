package mage.cards.o;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.game.permanent.token.SturdyShieldToken;

/**
 *
 * @author muz
 */
public final class OriginOfCaptainAmerica extends CardImpl {

    public OriginOfCaptainAmerica(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Put a +1/+1 counter on target creature you control. It gains first strike and vigilance until end of turn.
        Effects effects = new Effects();
        effects.add(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        effects.add(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
            .setText("It gains first strike"));
        effects.add(new GainAbilityTargetEffect(VigilanceAbility.getInstance())
            .setText("and vigilance until end of turn"));
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
            effects,
            new TargetControlledCreaturePermanent()
        );

        // II -- Create a colorless Equipment artifact token named Sturdy Shield with "Equipped creature gets +1/+2" and equip {2}.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
            new CreateTokenEffect(new SturdyShieldToken())
        );

        // III -- Tap up to one target creature and put a stun counter on it.
        effects = new Effects();
        effects.add(new TapTargetEffect());
        effects.add(new AddCountersTargetEffect(CounterType.STUN.createInstance())
            .setText("and put a stun counter on it"));
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
            effects,
            new TargetCreaturePermanent(0, 1)
        );

        this.addAbility(sagaAbility);
    }

    private OriginOfCaptainAmerica(final OriginOfCaptainAmerica card) {
        super(card);
    }

    @Override
    public OriginOfCaptainAmerica copy() {
        return new OriginOfCaptainAmerica(this);
    }
}
