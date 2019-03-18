
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Plopman
 */
public final class ImmortalCoil extends CardImpl {

    public ImmortalCoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}{B}{B}");

        // {tap}, Exile two cards from your graveyard: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, new FilterCard("cards from your graveyard"))));
        this.addAbility(ability);
        // If damage would be dealt to you, prevent that damage. Exile a card from your graveyard for each 1 damage prevented this way.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventAllDamageToControllerEffect()));
        // When there are no cards in your graveyard, you lose the game.
        this.addAbility(new ImmortalCoilAbility());
    }

    public ImmortalCoil(final ImmortalCoil card) {
        super(card);
    }

    @Override
    public ImmortalCoil copy() {
        return new ImmortalCoil(this);
    }
}

class ImmortalCoilAbility extends StateTriggeredAbility {

    public ImmortalCoilAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
        this.addEffect(new LoseGameEffect());
    }

    public ImmortalCoilAbility(final ImmortalCoilAbility ability) {
        super(ability);
    }

    @Override
    public ImmortalCoilAbility copy() {
        return new ImmortalCoilAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(this.getControllerId());
        return player != null && player.getGraveyard().isEmpty();
    }

    @Override
    public String getRule() {
        return "When there are no cards in your graveyard, you lose the game";
    }

}

class LoseGameEffect extends OneShotEffect {

    public LoseGameEffect() {
        super(Outcome.Neutral);
        staticText = "you lose the game";
    }

    public LoseGameEffect(final LoseGameEffect effect) {
        super(effect);
    }

    @Override
    public LoseGameEffect copy() {
        return new LoseGameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.lost(game);
            return true;
        }
        return false;
    }
}

class PreventAllDamageToControllerEffect extends PreventionEffectImpl {

    public PreventAllDamageToControllerEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "If damage would be dealt to you, prevent that damage. Exile a card from your graveyard for each 1 damage prevented this way";
    }

    public PreventAllDamageToControllerEffect(final PreventAllDamageToControllerEffect effect) {
        super(effect);
    }

    @Override
    public PreventAllDamageToControllerEffect copy() {
        return new PreventAllDamageToControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(Math.min(damage, player.getGraveyard().size()), new FilterCard());
                if (target.choose(Outcome.Exile, source.getControllerId(), source.getSourceId(), game)) {
                    for (UUID targetId : target.getTargets()) {
                        Card card = player.getGraveyard().get(targetId, game);
                        if (card != null) {
                            card.moveToZone(Zone.EXILED, source.getSourceId(), game, false);
                        }
                    }
                }
            }
            event.setAmount(0);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId())) {
                return true;
            }

        }
        return false;
    }

}
