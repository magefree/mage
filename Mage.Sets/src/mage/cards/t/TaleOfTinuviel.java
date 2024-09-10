package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TaleOfTinuviel extends CardImpl {

    public TaleOfTinuviel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your drasv step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Target creature you control gains indestructible for as long as you control Tale of Tinuviel.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.WhileControlled),
                new TargetControlledCreaturePermanent()
        );

        // II -- Return target creature card from your graveyard to the battlefield.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)
        );

        // III -- Up to two target creatures you control each gain lifelink until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn),
                new TargetControlledCreaturePermanent(0, 2, StaticFilters.FILTER_CONTROLLED_CREATURES, false)
        );
        this.addAbility(sagaAbility);
    }

    private TaleOfTinuviel(final TaleOfTinuviel card) {
        super(card);
    }

    @Override
    public TaleOfTinuviel copy() {
        return new TaleOfTinuviel(this);
    }
}
