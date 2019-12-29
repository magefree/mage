package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysticForge extends CardImpl {

    public MysticForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast the top card of your library if it's an artifact card or a colorless nonland card.
        this.addAbility(new SimpleStaticAbility(new MysticForgeTopCardCastEffect()));

        // {T}, Pay 1 life: Exile the top card of your library.
        Ability ability = new SimpleActivatedAbility(new MysticForgeExileEffect(), new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private MysticForge(final MysticForge card) {
        super(card);
    }

    @Override
    public MysticForge copy() {
        return new MysticForge(this);
    }
}

class MysticForgeTopCardCastEffect extends AsThoughEffectImpl {

    MysticForgeTopCardCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast the top card of your library if it's an artifact card or a colorless nonland card.";
    }

    private MysticForgeTopCardCastEffect(final MysticForgeTopCardCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MysticForgeTopCardCastEffect copy() {
        return new MysticForgeTopCardCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }
        Card card = game.getCard(objectId);
        if (card == null) {
            return false;
        }
        Player controller = game.getPlayer(affectedControllerId);
        if (controller == null) {
            return false;
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        return game.getObject(source.getSourceId()) != null
                && topCard != null
                && topCard == card
                && (topCard.isArtifact() || (!topCard.isLand() && topCard.getColor(game).isColorless()))
                && topCard.getSpellAbility() != null
                && topCard.getSpellAbility().spellCanBeActivatedRegularlyNow(controller.getId(), game);
    }
}

class MysticForgeExileEffect extends OneShotEffect {

    MysticForgeExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library";
    }

    private MysticForgeExileEffect(final MysticForgeExileEffect effect) {
        super(effect);
    }

    @Override
    public MysticForgeExileEffect copy() {
        return new MysticForgeExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        return controller.moveCards(controller.getLibrary().getFromTop(game), Zone.EXILED, source, game);
    }
}
