package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StartledAwake extends CardImpl {

    public StartledAwake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        this.secondSideCardClazz = mage.cards.p.PersistentNightmare.class;

        // Target opponent puts the top thirteen cards of their library into their graveyard.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(13));

        // {3}{U}{U}: Put Startled Awake from your graveyard onto the battlefield transformed. Activate this ability only any time you could cast a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new StartledAwakeReturnTransformedEffect(), new ManaCostsImpl<>("{3}{U}{U}")
        ));
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

    public StartledAwakeReturnTransformedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Put {this} from your graveyard onto the battlefield transformed";
    }

    public StartledAwakeReturnTransformedEffect(final StartledAwakeReturnTransformedEffect effect) {
        super(effect);
    }

    @Override
    public StartledAwakeReturnTransformedEffect copy() {
        return new StartledAwakeReturnTransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (controller == null || !(sourceObject instanceof Card)) {
            return false;
        }
        if (game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return true;
        }
        Card card = (Card) sourceObject;
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), true);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        return true;
    }
}
