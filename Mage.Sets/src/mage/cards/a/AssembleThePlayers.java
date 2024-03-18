package mage.cards.a;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.OnceEachTurnCastWatcher;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class AssembleThePlayers extends CardImpl {

    public AssembleThePlayers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // Once each turn, you may cast a creature spell with power 2 or less from the top of your library.
        this.addAbility(
                new SimpleStaticAbility(new AssembleThePlayersPlayTopEffect())
                        .setIdentifier(MageIdentifier.OnceEachTurnCastWatcher)
                        .addHint(OnceEachTurnCastWatcher.getHint()),
                new OnceEachTurnCastWatcher()
                // all based on Johann, Apprentice Sorcerer
        );

    }

    private AssembleThePlayers(final AssembleThePlayers card) {
        super(card);
    }

    @Override
    public AssembleThePlayers copy() {
        return new AssembleThePlayers(this);
    }
}

class AssembleThePlayersPlayTopEffect extends AsThoughEffectImpl {

    AssembleThePlayersPlayTopEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Once each turn, you may cast a creature spell with power 2 or less from the top of your library";
    }

    private AssembleThePlayersPlayTopEffect(final AssembleThePlayersPlayTopEffect effect) {
        super(effect);
    }

    @Override
    public AssembleThePlayersPlayTopEffect copy() {
        return new AssembleThePlayersPlayTopEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        // Only applies for the controller of the ability.
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }

        Player controller = game.getPlayer(source.getControllerId());
        OnceEachTurnCastWatcher watcher = game.getState().getWatcher(OnceEachTurnCastWatcher.class);
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (controller == null || watcher == null || sourceObject == null) {
            return false;
        }

        // Has the ability already been used this turn by the player?
        if (watcher.isAbilityUsed(controller.getId(), new MageObjectReference(sourceObject, game))) {
            return false;
        }

        Card card = game.getCard(objectId);
        Card topCard = controller.getLibrary().getFromTop(game);
        // Is the card attempted to be played the top card of the library?
        if (card == null || topCard == null || !topCard.getId().equals(card.getMainCard().getId())) {
            return false;
        }

        // Only works for creatures with power 2 or less
        return card.isCreature(game) && card.getPower().getValue() <=2;
    }
}
