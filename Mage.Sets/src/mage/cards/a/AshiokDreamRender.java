package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AshiokDreamRender extends CardImpl {

    public AshiokDreamRender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U/B}{U/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ASHIOK);
        this.setStartingLoyalty(5);

        // Spells and abilities your opponents control can't cause their controller to search their library.
        this.addAbility(new SimpleStaticAbility(new AshiokDreamRenderEffect()));

        // -1: Target player puts the top four cards of their library into their graveyard. Then exile each opponent's graveyard.
        Ability ability = new LoyaltyAbility(new MillCardsTargetEffect(4), -1);
        ability.addEffect(new ExileGraveyardAllPlayersEffect(StaticFilters.FILTER_CARD, TargetController.OPPONENT).setText("Then exile each opponent's graveyard."));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private AshiokDreamRender(final AshiokDreamRender card) {
        super(card);
    }

    @Override
    public AshiokDreamRender copy() {
        return new AshiokDreamRender(this);
    }
}

class AshiokDreamRenderEffect extends ContinuousRuleModifyingEffectImpl {

    AshiokDreamRenderEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, true, false);
        staticText = "Spells and abilities your opponents control can't cause their controller to search their library.";
    }

    private AshiokDreamRenderEffect(final AshiokDreamRenderEffect effect) {
        super(effect);
    }

    @Override
    public AshiokDreamRenderEffect copy() {
        return new AshiokDreamRenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't search libraries (" + mageObject.getLogName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return GameEvent.EventType.SEARCH_LIBRARY == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null
                && event.getPlayerId().equals(game.getControllerId(event.getSourceId()))
                && event.getTargetId().equals(game.getControllerId(event.getSourceId()))
                && controller.hasOpponent(game.getControllerId(event.getSourceId()), game);
    }
}
