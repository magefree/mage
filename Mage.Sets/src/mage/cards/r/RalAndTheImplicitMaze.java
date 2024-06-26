package mage.cards.r;

import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.permanent.token.SpellgorgerWeirdToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RalAndTheImplicitMaze extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature and planeswalker your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public RalAndTheImplicitMaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        this.subtype.add(SubType.SAGA);

        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Ral and the Implicit Maze deals 2 damage to each creature and planeswalker your opponents control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new DamageAllEffect(2, filter));

        // II -- You may discard a card. If you do, exile the top two cards of your library. You may play them until the end of your next turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new DoIfCostPaid(new ExileTopXMayPlayUntilEffect(2, Duration.UntilEndOfYourNextTurn)
                        .withTextOptions("them", true), new DiscardCardCost())
        );

        // III -- Create a Spellgorger Weird token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new CreateTokenEffect(new SpellgorgerWeirdToken()));

        this.addAbility(sagaAbility);
    }

    private RalAndTheImplicitMaze(final RalAndTheImplicitMaze card) {
        super(card);
    }

    @Override
    public RalAndTheImplicitMaze copy() {
        return new RalAndTheImplicitMaze(this);
    }
}
