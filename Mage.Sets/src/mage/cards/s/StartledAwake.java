package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.cards.TransformingDoubleFacedCardHalf;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StartledAwake extends TransformingDoubleFacedCard {

    public StartledAwake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{2}{U}{U}",
                "Persistent Nightmare",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.NIGHTMARE}, "U");

        this.getRightHalfCard().setPT(1, 1);

        // Target opponent puts the top thirteen cards of their library into their graveyard.
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetOpponent());
        this.getLeftHalfCard().getSpellAbility().addEffect(new MillCardsTargetEffect(13));

        // {3}{U}{U}: Put Startled Awake from your graveyard onto the battlefield transformed. Activate this ability only any time you could cast a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new StartledAwakeReturnTransformedEffect(), new ManaCostsImpl<>("{3}{U}{U}")
        ));

        // Persistent Nightmare

        // Skulk
        this.getRightHalfCard().addAbility(new SkulkAbility());

        // When Persistent Nightmare deals combat damage to a player, return it to its owner's hand.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new ReturnToHandSourceEffect(), false
        ).setTriggerPhrase("When {this} deals combat damage to a player, "));
    }

    private StartledAwake(final StartledAwake card) {
        super(card);
    }

    @Override
    public StartledAwake copy() {
        return new StartledAwake(this);
    }
}

class StartledAwakeReturnTransformedEffect extends OneShotEffect {

    StartledAwakeReturnTransformedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Put {this} from your graveyard onto the battlefield transformed";
    }

    private StartledAwakeReturnTransformedEffect(final StartledAwakeReturnTransformedEffect effect) {
        super(effect);
    }

    @Override
    public StartledAwakeReturnTransformedEffect copy() {
        return new StartledAwakeReturnTransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = source.getSourceCardIfItStillExists(game);
        if (controller == null || card == null) {
            return false;
        }
        if (game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return true;
        }
        Card backSide = ((TransformingDoubleFacedCardHalf) card).getOtherSide();
        if (backSide == null) {
            return true;
        }
        game.getState().setValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + backSide.getId(), true);
        controller.moveCards(backSide, Zone.BATTLEFIELD, source, game);
        return true;
    }
}
