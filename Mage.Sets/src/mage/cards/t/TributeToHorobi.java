package mage.cards.t;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.RatRogueToken;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class TributeToHorobi extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterPermanent(SubType.RAT, "Rat tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public TributeToHorobi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{1}{B}",
                "Echo of Death's Wail",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "B"
        );

        // Tribute to Horobi
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I, II — Each opponent creates a 1/1 black Rat Rouge creature token.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new CreateTokenAllEffect(new RatRogueToken(), TargetController.OPPONENT)
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Echo of Death's Wail
        this.getRightHalfCard().setPT(3, 3);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // When Echo of Death's Wail enters the battlefield, gain control of all Rat tokens.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new GainControlAllEffect(Duration.Custom, filter)));

        // Whenever Echo of Death's Wail attacks, you may sacrifice another creature. If you do, draw a card.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        )));
    }

    private TributeToHorobi(final TributeToHorobi card) {
        super(card);
    }

    @Override
    public TributeToHorobi copy() {
        return new TributeToHorobi(this);
    }
}
