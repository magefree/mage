package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BahamutWardenOfLight extends CardImpl {

    public BahamutWardenOfLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.nightCard = true;
        this.color.setWhite(true);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Wings of Light -- Put a +1/+1 counter on each other creature you control. Those creatures gain flying until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, ability -> {
            ability.addEffect(new AddCountersAllEffect(
                    CounterType.P1P1.createInstance(), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE
            ));
            ability.addEffect(new GainAbilityControlledEffect(
                    FlyingAbility.getInstance(), Duration.EndOfTurn,
                    StaticFilters.FILTER_CONTROLLED_CREATURE
            ).setText("Those creatures gain flying until end of turn"));
            ability.withFlavorWord("Wings of Light");
        });

        // III -- Gigaflare -- Destroy target permanent. Exile Bahamut, then return it to the battlefield.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new DestroyTargetEffect());
            ability.addEffect(new ExileSourceAndReturnFaceUpEffect());
            ability.addTarget(new TargetPermanent());
            ability.withFlavorWord("Gigaflare");
        });
        this.addAbility(sagaAbility);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private BahamutWardenOfLight(final BahamutWardenOfLight card) {
        super(card);
    }

    @Override
    public BahamutWardenOfLight copy() {
        return new BahamutWardenOfLight(this);
    }
}
