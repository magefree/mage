package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfKaldheim extends TransformingDoubleFacedCard {

    public InvasionOfKaldheim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{3}{R}",
                "Pyre of the World Tree",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "R"
        );

        // Invasion of Kaldheim
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Kaldheim enters the battlefield, exile all cards from your hand, then draw that many cards. Until the end of your next turn, you may play cards exiled this way.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new InvasionOfKaldheimEffect()));

        // Pyre of the World Tree
        // Discard a land card: Pyre of the World Tree deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(2),
                new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_LAND_A))
        );
        ability.addTarget(new TargetAnyTarget());
        this.getRightHalfCard().addAbility(ability);

        // Whenever you discard a land card, exile the top card of your library. You may play that card this turn.
        this.getRightHalfCard().addAbility(new DiscardCardControllerTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn),
                false, StaticFilters.FILTER_CARD_LAND_A
        ));
    }

    private InvasionOfKaldheim(final InvasionOfKaldheim card) {
        super(card);
    }

    @Override
    public InvasionOfKaldheim copy() {
        return new InvasionOfKaldheim(this);
    }
}

class InvasionOfKaldheimEffect extends OneShotEffect {

    InvasionOfKaldheimEffect() {
        super(Outcome.Benefit);
        staticText = "exile all cards from your hand, then draw that many cards. Until the end of your next turn, you may play cards exiled this way";
    }

    private InvasionOfKaldheimEffect(final InvasionOfKaldheimEffect effect) {
        super(effect);
    }

    @Override
    public InvasionOfKaldheimEffect copy() {
        return new InvasionOfKaldheimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        Cards cards = new CardsImpl(player.getHand());
        player.moveCards(cards, Zone.EXILED, source, game);
        player.drawCards(cards.size(), source, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, false, Duration.UntilEndOfYourNextTurn, false);
        }
        return true;
    }
}
