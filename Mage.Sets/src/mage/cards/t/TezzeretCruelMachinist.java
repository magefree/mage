package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.BecomePermanentFacedownEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TezzeretCruelMachinist extends CardImpl {

    public TezzeretCruelMachinist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEZZERET);
        this.setStartingLoyalty(4);

        // +1: Draw a card.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1));

        // 0: Until your next turn, target artifact you control becomes a 5/5 creature in addition to its other types.
        Ability ability = new LoyaltyAbility(new AddCardTypeTargetEffect(
                Duration.UntilYourNextTurn, CardType.ARTIFACT, CardType.CREATURE
        ).setText("Until your next turn, target artifact you control becomes an artifact creature"), 0);
        ability.addEffect(new SetBasePowerToughnessTargetEffect(
                5, 5, Duration.UntilYourNextTurn
        ).setText("with base power and toughness 5/5"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        this.addAbility(ability);

        // −7: Put any number of cards from your hand onto the battlefield face down. They're 5/5 artifact creatures.
        this.addAbility(new LoyaltyAbility(new TezzeretCruelMachinistEffect(), -7));
    }

    private TezzeretCruelMachinist(final TezzeretCruelMachinist card) {
        super(card);
    }

    @Override
    public TezzeretCruelMachinist copy() {
        return new TezzeretCruelMachinist(this);
    }
}

class TezzeretCruelMachinistEffect extends OneShotEffect {

    private static final BecomePermanentFacedownEffect.PermanentApplier applier
            = (permanent, game, source) -> {
        permanent.addCardType(game, CardType.ARTIFACT, CardType.CREATURE);
        permanent.getPower().setModifiedBaseValue(5);
        permanent.getToughness().setModifiedBaseValue(5);
    };

    TezzeretCruelMachinistEffect() {
        super(Outcome.Benefit);
        this.staticText = "put any number of cards from your hand onto the battlefield face down. They're 5/5 artifact creatures";
    }

    private TezzeretCruelMachinistEffect(final TezzeretCruelMachinistEffect effect) {
        super(effect);
    }

    @Override
    public TezzeretCruelMachinistEffect copy() {
        return new TezzeretCruelMachinistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetCardInHand(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD);
        player.choose(outcome, target, source, game);
        Cards cardsToMove = new CardsImpl(target.getTargets());
        if (cardsToMove.isEmpty()) {
            return false;
        }
        game.addEffect(new BecomePermanentFacedownEffect(applier, cardsToMove, game), source);
        player.moveCards(
                cardsToMove.getCards(game), Zone.BATTLEFIELD, source, game,
                false, true, true, null
        );
        return true;
    }
}
