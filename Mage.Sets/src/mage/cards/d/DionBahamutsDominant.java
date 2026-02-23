package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.permanent.token.WaylayToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DionBahamutsDominant extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterPermanent(SubType.KNIGHT, "");

    public DionBahamutsDominant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.NOBLE, SubType.KNIGHT}, "{3}{W}",
                "Bahamut, Warden of Light",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SAGA, SubType.DRAGON}, "W");

        // Dion, Bahamut's Dominant
        this.getLeftHalfCard().setPT(3, 3);

        // Dragonfire Dive -- During your turn, Dion and other Knights you control have flying.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                MyTurnCondition.instance, "during your turn, {this}"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter),
                MyTurnCondition.instance, "and other Knights you control have flying"
        ));
        this.getLeftHalfCard().addAbility(ability.withFlavorWord("Dragonfire Dive"));

        // When Dion enters, create a 2/2 white Knight creature token.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WaylayToken())));

        // {4}{W}{W}, {T}: Exile Dion, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        Ability ability2 = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED), new ManaCostsImpl<>("{4}{W}{W}")
        );
        ability2.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability2);

        // Bahamut, Warden of Light
        this.getRightHalfCard().setPT(5, 5);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard());

        // I, II -- Wings of Light -- Put a +1/+1 counter on each other creature you control. Those creatures gain flying until end of turn.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, ability3 -> {
            ability3.addEffect(new AddCountersAllEffect(
                    CounterType.P1P1.createInstance(), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE
            ));
            ability3.addEffect(new GainAbilityControlledEffect(
                    FlyingAbility.getInstance(), Duration.EndOfTurn,
                    StaticFilters.FILTER_CONTROLLED_CREATURE
            ).setText("Those creatures gain flying until end of turn"));
            ability3.withFlavorWord("Wings of Light");
        });

        // III -- Gigaflare -- Destroy target permanent. Exile Bahamut, then return it to the battlefield.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_III, ability4 -> {
            ability4.addEffect(new DestroyTargetEffect());
            ability4.addEffect(new ExileSourceAndReturnFaceUpEffect());
            ability4.addTarget(new TargetPermanent());
            ability4.withFlavorWord("Gigaflare");
        });
        this.getRightHalfCard().addAbility(sagaAbility);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());
    }

    private DionBahamutsDominant(final DionBahamutsDominant card) {
        super(card);
    }

    @Override
    public DionBahamutsDominant copy() {
        return new DionBahamutsDominant(this);
    }
}
