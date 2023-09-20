package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.ExileTopCreatureCardOfGraveyardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 * @author arcox
 */
public final class CirclingVultures extends CardImpl {

    public CirclingVultures(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may discard Circling Vultures any time you could cast an instant.
        this.addAbility(new CirclingVulturesSpecialAction());

        // At the beginning of your upkeep, sacrifice Circling Vultures unless you exile the top creature card of your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ExileTopCreatureCardOfGraveyardCost(1)), TargetController.YOU, false));
    }

    private CirclingVultures(final CirclingVultures card) {
        super(card);
    }

    @Override
    public CirclingVultures copy() {
        return new CirclingVultures(this);
    }
}

class CirclingVulturesSpecialAction extends SpecialAction {

    public CirclingVulturesSpecialAction() {
        super(Zone.HAND);
        this.setMayActivate(TargetController.YOU);
        this.addEffect(new CirclingVulturesDiscardEffect());
    }

    private CirclingVulturesSpecialAction(final CirclingVulturesSpecialAction ability) {
        super(ability);
    }

    @Override
    public CirclingVulturesSpecialAction copy() {
        return new CirclingVulturesSpecialAction(this);
    }

    @Override
    public String getRule() {
        return "You may discard {this} any time you could cast an instant.";
    }
}

class CirclingVulturesDiscardEffect extends OneShotEffect {
    public CirclingVulturesDiscardEffect() {
        super(Outcome.Neutral);
        this.staticText = "discard {this}";
    }

    private CirclingVulturesDiscardEffect(final CirclingVulturesDiscardEffect effect) {
        super(effect);
    }

    @Override
    public CirclingVulturesDiscardEffect copy() {
        return new CirclingVulturesDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Card card = player.getHand().get(source.getSourceId(), game);
        if (card == null) {
            return false;
        }

        return player.discard(card, false, source, game);
    }
}
