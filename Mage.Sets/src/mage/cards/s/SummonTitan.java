package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonTitan extends CardImpl {

    public SummonTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I - Mill five cards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new MillCardsControllerEffect(5));

        // II - Return all land cards from your graveyard to the battlefield tapped.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new ReturnFromYourGraveyardToBattlefieldAllEffect(StaticFilters.FILTER_CARD_LANDS, true)
        );

        // III - Until end of turn, another target creature you control gains trample and gets +X/+X, where X is the number of lands you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new Effects(
                        new GainAbilityTargetEffect(TrampleAbility.getInstance())
                                .setText("until end of turn, another target creature you control gains trample"),
                        new BoostTargetEffect(LandsYouControlCount.instance, LandsYouControlCount.instance)
                                .setText("and gets +X/+X, where X is the number of lands you control")
                ), new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL)
        );
        this.addAbility(sagaAbility.addHint(LandsYouControlHint.instance));

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private SummonTitan(final SummonTitan card) {
        super(card);
    }

    @Override
    public SummonTitan copy() {
        return new SummonTitan(this);
    }
}
