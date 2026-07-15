package mage.cards.a;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;

/**
 *
 * @author muz
 */
public final class AmokTime extends CardImpl {

    public AmokTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Target creature you control fights up to one target creature an opponent controls.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
            ability -> {
                ability.addEffect(new FightTargetsEffect().setText(
                    "Target creature you control fights up to one target creature an opponent controls"
                ));
                ability.addTarget(new TargetControlledCreaturePermanent());
                ability.addTarget(new TargetPermanent(
                    0, 1, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
                ));
            }
        );

        // II -- You gain 3 life.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II, new GainLifeEffect(3)
        );

        // III -- Return target creature card from your graveyard to your hand.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
            new ReturnFromGraveyardToHandTargetEffect(),
            new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)
        );

        this.addAbility(sagaAbility);
    }

    private AmokTime(final AmokTime card) {
        super(card);
    }

    @Override
    public AmokTime copy() {
        return new AmokTime(this);
    }
}
