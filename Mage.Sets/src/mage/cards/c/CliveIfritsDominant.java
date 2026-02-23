package mage.cards.c;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CliveIfritsDominant extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent("other target creature");
    private static final Condition condition = new SourceHasCounterCondition(CounterType.LORE, 3);

    public CliveIfritsDominant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.NOBLE, SubType.WARRIOR}, "{4}{R}{R}",
                "Ifrit, Warden of Inferno",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SAGA, SubType.DEMON}, "R");

        // Clive, Ifrit's Dominant
        this.getLeftHalfCard().setPT(5, 5);

        // When Clive enters, you may discard your hand, then draw cards equal to your devotion to red.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardHandControllerEffect(), true);
        ability.addEffect(new DrawCardSourceControllerEffect(DevotionCount.R)
                .setText(", then draw cards equal to your devotion to red"));
        this.getLeftHalfCard().addAbility(ability.addHint(DevotionCount.R.getHint()));

        // {4}{R}{R}, {T}: Exile Clive, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED), new ManaCostsImpl<>("{4}{R}{R}")
        );
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);


        // Ifrit, Warden of Inferno
        this.getRightHalfCard().setPT(9, 9);

        // Saga (chapters)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard());

        // I -- Lunge -- Ifrit fights up to one other target creature.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_I, sagaChapterAbility -> {
            sagaChapterAbility.addEffect(new FightTargetSourceEffect());
            sagaChapterAbility.addTarget(new TargetPermanent(0, 1, filter));
            sagaChapterAbility.withFlavorWord("Lunge");
        });

        // II, III -- Brimstone -- Add {R}{R}{R}{R}. If Ifrit has three or more lore counters on it, exile it, then return it to the battlefield (front face up.)
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III, sagaChapterAbility -> {
            sagaChapterAbility.addEffect(new BasicManaEffect(Mana.RedMana(4)));
            sagaChapterAbility.addEffect(new ConditionalOneShotEffect(
                    new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD), condition,
                    "If {this} has three or more lore counters on it, exile it, then return it to the battlefield. <i>(front face up.)</i>"
            ));
            sagaChapterAbility.withFlavorWord("Brimstone");
        });
        this.getRightHalfCard().addAbility(sagaAbility);
    }

    private CliveIfritsDominant(final CliveIfritsDominant card) {
        super(card);
    }

    @Override
    public CliveIfritsDominant copy() {
        return new CliveIfritsDominant(this);
    }
}
