package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.AddCounterNextSpellDelayedTriggeredAbility;
import mage.abilities.condition.common.ControlsCreatureGreatestPowerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonFenrir extends CardImpl {

    private static final Hint hint = new ConditionHint(
            ControlsCreatureGreatestPowerCondition.instance,
            "You control a creature with the greatest power"
    );

    public SummonFenrir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Crescent Fang -- Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(new SearchLibraryPutInPlayEffect(
                    new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
            ));
            ability.withFlavorWord("Crescent Fang");
        });

        // II -- Heavenward Howl -- When you next cast a creature spell this turn, that creature enters with an additional +1/+1 counter on it.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, ability -> {
            ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new AddCounterNextSpellDelayedTriggeredAbility()));
            ability.withFlavorWord("Heavenward Howl");
        });

        // III -- Ecliptic Growl -- Draw a card if you control the creature with the greatest power or tied for the greatest power.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new ConditionalOneShotEffect(
                    new DrawCardSourceControllerEffect(1), ControlsCreatureGreatestPowerCondition.instance,
                    "draw a card if you control the creature with the greatest power or tied for the greatest power"
            ));
            ability.withFlavorWord("Ecliptic Growl");
        });
        this.addAbility(sagaAbility.addHint(hint));
    }

    private SummonFenrir(final SummonFenrir card) {
        super(card);
    }

    @Override
    public SummonFenrir copy() {
        return new SummonFenrir(this);
    }
}
