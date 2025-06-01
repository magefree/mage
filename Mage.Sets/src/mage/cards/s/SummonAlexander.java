package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonAlexander extends CardImpl {

    public SummonAlexander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.nightCard = true;
        this.color.setWhite(true);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Prevent all damage that would be dealt to creatures you control this turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new PreventAllDamageToAllEffect(
                        Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED
                )
        );

        // III -- Tap all creatures your opponents control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new TapAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES)
        );
        this.addAbility(sagaAbility.withShowSacText(true));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private SummonAlexander(final SummonAlexander card) {
        super(card);
    }

    @Override
    public SummonAlexander copy() {
        return new SummonAlexander(this);
    }
}
