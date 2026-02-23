package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLegendOfKyoshi extends TransformingDoubleFacedCard {

    public TheLegendOfKyoshi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{4}{G}{G}",
                "Avatar Kyoshi",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.AVATAR}, ""
        );

        // The Legend of Kyoshi
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I -- Draw cards equal to the greatest power among creatures you control.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I,
                new DrawCardSourceControllerEffect(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES)
                        .setText("draw cards equal to the greatest power among creatures you control")
        );

        // II -- Earthbend X, where X is the number of cards in your hand. That land becomes an Island in addition to its other types.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new Effects(
                        new EarthbendTargetEffect(CardsInControllerHandCount.ANY, true)
                                .setText("earthbend X, where X is the number of cards in your hand"),
                        new AddCardSubTypeTargetEffect(SubType.ISLAND, Duration.Custom)
                                .setText("That land becomes an Island in addition to its other types")
                ), new TargetControlledLandPermanent()
        );

        // III -- Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.getLeftHalfCard().addAbility(sagaAbility.addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint()));

        // Avatar Kyoshi
        this.getRightHalfCard().setPT(5, 4);

        // Lands you control have trample and hexproof.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_LANDS
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_LANDS
        ).setText("and hexproof"));
        this.getRightHalfCard().addAbility(ability);

        // {T}: Add X mana of any one color, where X is the greatest power among creatures you control.
        this.getRightHalfCard().addAbility(new DynamicManaAbility(
                Mana.AnyMana(1), GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES,
                new TapSourceCost(), "add X mana of any one color, " +
                "where X is the greatest power among creatures you control", true
        ).addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint()));
    }

    private TheLegendOfKyoshi(final TheLegendOfKyoshi card) {
        super(card);
    }

    @Override
    public TheLegendOfKyoshi copy() {
        return new TheLegendOfKyoshi(this);
    }
}
