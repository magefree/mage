package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfErgamon extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("a land or battle card");

    static {
        filter.add(Predicates.or(CardType.LAND.getPredicate(), CardType.BATTLE.getPredicate()));
    }

    public InvasionOfErgamon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{R}{G}",
                "Truga Cliffcharger",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.RHINO}, "RG"
        );

        // Invasion of Ergamon
        this.getLeftHalfCard().setStartingDefense(5);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Ergamon enters the battlefield, create a Treasure token. Then you may discard a card. If you do, draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken()));
        ability.addEffect(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()).concatBy("Then"));
        this.getLeftHalfCard().addAbility(ability);

        // Truga Cliffcharger
        this.getRightHalfCard().setPT(3, 4);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // When Truga Cliffcharger enters the battlefield, you may discard a card. If you do, search your library for a land or battle card, reveal it, put it into your hand, then shuffle.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(filter), true
                ), new DiscardCardCost())
        ));
    }

    private InvasionOfErgamon(final InvasionOfErgamon card) {
        super(card);
    }

    @Override
    public InvasionOfErgamon copy() {
        return new InvasionOfErgamon(this);
    }
}
