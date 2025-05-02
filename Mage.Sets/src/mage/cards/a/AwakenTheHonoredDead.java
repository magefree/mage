package mage.cards.a;

import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AwakenTheHonoredDead extends CardImpl {

    public AwakenTheHonoredDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{G}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Destroy target nonland permanent.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new DestroyTargetEffect(), new TargetNonlandPermanent()
        );

        // II -- Mill three cards.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new MillCardsControllerEffect(3)
        );

        // III -- You may discard a card. When you do, return target creature or land card from your graveyard to your hand.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_OR_LAND));
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new DoWhenCostPaid(ability, new DiscardCardCost(), "Discard a card?")
        );
        this.addAbility(sagaAbility);
    }

    private AwakenTheHonoredDead(final AwakenTheHonoredDead card) {
        super(card);
    }

    @Override
    public AwakenTheHonoredDead copy() {
        return new AwakenTheHonoredDead(this);
    }
}
