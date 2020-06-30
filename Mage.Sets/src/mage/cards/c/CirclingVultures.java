package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.common.ExileTopCreatureCardOfGraveyardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

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
        this.addAbility(new SimpleActivatedAbility(Zone.HAND, new CirclingVulturesEffect(), new CirclingVulturesCost()));

        // At the beginning of your upkeep, sacrifice Circling Vultures unless you exile the top creature card of your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ExileTopCreatureCardOfGraveyardCost(1)), TargetController.YOU, false));
    }

    public CirclingVultures(final CirclingVultures card) {
        super(card);
    }

    @Override
    public CirclingVultures copy() {
        return new CirclingVultures(this);
    }
}

class CirclingVulturesCost extends DiscardSourceCost {
    public CirclingVulturesCost() {
    }

    public CirclingVulturesCost(CirclingVulturesCost cost) {
        super(cost);
    }

    @Override
    public CirclingVulturesCost copy() {
        return new CirclingVulturesCost(this);
    }

    @Override
    public String getText() {
        return "";
    }
}

class CirclingVulturesEffect extends OneShotEffect {

    public CirclingVulturesEffect() {
        super(Outcome.Neutral);
        this.staticText = "You may discard {this} any time you could cast an instant";
    }

    public CirclingVulturesEffect(final CirclingVulturesEffect effect) {
        super(effect);
    }

    @Override
    public CirclingVulturesEffect copy() {
        return new CirclingVulturesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
