package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;
import java.util.stream.Collectors;

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

    public TezzeretCruelMachinistEffect() {
        super(Outcome.Benefit);
        this.staticText = "put any number of cards from your hand onto the battlefield face down. They're 5/5 artifact creatures";
    }

    public TezzeretCruelMachinistEffect(final TezzeretCruelMachinistEffect effect) {
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
        game.addEffect(new TezzeretCruelMachinistCardTypeEffect().setTargetPointer(new FixedTargets(
                cardsToMove
                        .getCards(game)
                        .stream()
                        .map(card -> new MageObjectReference(card, game, 1))
                        .collect(Collectors.toSet()), game
        )), source);
        player.moveCards(
                cardsToMove.getCards(game), Zone.BATTLEFIELD, source, game,
                false, true, true, null
        );
        return true;
    }
}

class TezzeretCruelMachinistCardTypeEffect extends ContinuousEffectImpl {

    public TezzeretCruelMachinistCardTypeEffect() {
        super(Duration.Custom, Layer.CopyEffects_1, SubLayer.FaceDownEffects_1b, Outcome.Neutral);
    }

    public TezzeretCruelMachinistCardTypeEffect(final TezzeretCruelMachinistCardTypeEffect effect) {
        super(effect);
    }

    @Override
    public TezzeretCruelMachinistCardTypeEffect copy() {
        return new TezzeretCruelMachinistCardTypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean flag = false;
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            Permanent target = game.getPermanent(targetId);
            if (target == null || !target.isFaceDown(game)) {
                continue;
            }
            flag = true;
            target.removeAllSuperTypes(game);
            target.removeAllCardTypes(game);
            target.removeAllSubTypes(game);
            target.addCardType(game, CardType.ARTIFACT, CardType.CREATURE);
            target.getPower().setModifiedBaseValue(5);
            target.getToughness().setModifiedBaseValue(5);
        }
        if (!flag) {
            discard();
            return false;
        }
        return true;
    }
}
