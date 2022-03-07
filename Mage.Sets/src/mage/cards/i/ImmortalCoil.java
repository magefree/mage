package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ImmortalCoil extends CardImpl {

    public ImmortalCoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}{B}");

        // {tap}, Exile two cards from your graveyard: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARD_CARDS)));
        this.addAbility(ability);

        // If damage would be dealt to you, prevent that damage. Exile a card from your graveyard for each 1 damage prevented this way.
        this.addAbility(new SimpleStaticAbility(new ImmortalCoilPreventionEffect()));

        // When there are no cards in your graveyard, you lose the game.
        this.addAbility(new ImmortalCoilAbility());
    }

    private ImmortalCoil(final ImmortalCoil card) {
        super(card);
    }

    @Override
    public ImmortalCoil copy() {
        return new ImmortalCoil(this);
    }
}

class ImmortalCoilAbility extends StateTriggeredAbility {

    ImmortalCoilAbility() {
        super(Zone.BATTLEFIELD, new LoseGameSourceControllerEffect());
    }

    private ImmortalCoilAbility(final ImmortalCoilAbility ability) {
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
        return "When there are no cards in your graveyard, you lose the game.";
    }
}

class ImmortalCoilPreventionEffect extends PreventionEffectImpl {

    ImmortalCoilPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "If damage would be dealt to you, prevent that damage. " +
                "Exile a card from your graveyard for each 1 damage prevented this way";
    }

    private ImmortalCoilPreventionEffect(final ImmortalCoilPreventionEffect effect) {
        super(effect);
    }

    @Override
    public ImmortalCoilPreventionEffect copy() {
        return new ImmortalCoilPreventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (game.replaceEvent(new PreventDamageEvent(
                event.getTargetId(), source.getSourceId(), source, source.getControllerId(),
                event.getAmount(), ((DamageEvent) event).isCombatDamage()
        ))) {
            return false;
        }
        int damage = event.getAmount();
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(Math.min(damage, player.getGraveyard().size()), StaticFilters.FILTER_CARD);
            target.setNotTarget(true);
            player.choose(outcome, target, source.getSourceId(), game);
            player.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
        }
        event.setAmount(0);
        game.fireEvent(new PreventedDamageEvent(
                event.getTargetId(), source.getSourceId(), source, source.getControllerId(), damage
        ));
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game)
                && event.getTargetId().equals(source.getControllerId());
    }
}
