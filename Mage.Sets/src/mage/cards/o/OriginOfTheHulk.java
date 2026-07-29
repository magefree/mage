package mage.cards.o;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;

/**
 * @author muz
 */
public final class OriginOfTheHulk extends CardImpl {

    public OriginOfTheHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Create a 1/1 green and white Citizen creature token.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
            new CreateTokenEffect(new CitizenGreenWhiteToken())
        );

        // II -- Put two +1/+1 counters on target creature you control.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)),
            new TargetControlledCreaturePermanent()
        );

        // III -- Target creature you control gets +3/+3 and gains trample until end of turn.
        Effects effects = new Effects();
        effects.add(new BoostTargetEffect(3, 3)
            .setText("Target creature you control gets +3/+3"));
        effects.add(new GainAbilityTargetEffect(TrampleAbility.getInstance())
            .setText("and gains trample until end of turn"));
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
            effects,
            new TargetControlledCreaturePermanent()
        );

        this.addAbility(sagaAbility);
    }

    private OriginOfTheHulk(final OriginOfTheHulk card) {
        super(card);
    }

    @Override
    public OriginOfTheHulk copy() {
        return new OriginOfTheHulk(this);
    }
}
