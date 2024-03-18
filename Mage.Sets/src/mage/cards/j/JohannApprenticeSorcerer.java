package mage.cards.j;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.OnceEachTurnCastWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class JohannApprenticeSorcerer extends CardImpl {

    public JohannApprenticeSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // Once each turn, you may cast an instant or sorcery spell from the top of your library.
        this.addAbility(
                new SimpleStaticAbility(new JohannApprenticeSorcererPlayTopEffect())
                        .setIdentifier(MageIdentifier.OnceEachTurnCastWatcher)
                        .addHint(OnceEachTurnCastWatcher.getHint()),
                new OnceEachTurnCastWatcher()
        );
    }

    private JohannApprenticeSorcerer(final JohannApprenticeSorcerer card) {
        super(card);
    }

    @Override
    public JohannApprenticeSorcerer copy() {
        return new JohannApprenticeSorcerer(this);
    }
}

class JohannApprenticeSorcererPlayTopEffect extends AsThoughEffectImpl {

    JohannApprenticeSorcererPlayTopEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Once each turn, you may cast an instant or sorcery spell from the top of your library";
    }

    private JohannApprenticeSorcererPlayTopEffect(final JohannApprenticeSorcererPlayTopEffect effect) {
        super(effect);
    }

    @Override
    public JohannApprenticeSorcererPlayTopEffect copy() {
        return new JohannApprenticeSorcererPlayTopEffect(this);
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

        // Only works for instant & sorcery.
        return card.isInstantOrSorcery(game);
    }
}
