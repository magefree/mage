package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BagOfHolding extends CardImpl {

    public BagOfHolding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Whenever you discard a card, exile that card from your graveyard.
        this.addAbility(new BagOfHoldingTriggeredAbility());

        // {2}, {T}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {4}, {T}, Sacrifice Bag of Holding: Return all cards exiled with Bag of Holding to their owner's hand.
        ability = new SimpleActivatedAbility(new BagOfHoldingEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BagOfHolding(final BagOfHolding card) {
        super(card);
    }

    @Override
    public BagOfHolding copy() {
        return new BagOfHolding(this);
    }
}

class BagOfHoldingTriggeredAbility extends TriggeredAbilityImpl {

    BagOfHoldingTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private BagOfHoldingTriggeredAbility(final BagOfHoldingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BagOfHoldingTriggeredAbility copy() {
        return new BagOfHoldingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getPlayerId())) {
            this.getEffects().clear();
            this.addEffect(
                    new ExileTargetForSourceEffect().setTargetPointer(new FixedTarget(event.getTargetId(), game))
            );
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you discard a card, exile that card from your graveyard.";
    }
}

class BagOfHoldingEffect extends OneShotEffect {

    BagOfHoldingEffect() {
        super(Outcome.DrawCard);
        this.staticText = "return all cards exiled with {this} to their owner's hand";
    }

    private BagOfHoldingEffect(final BagOfHoldingEffect effect) {
        super(effect);
    }

    @Override
    public BagOfHoldingEffect copy() {
        return new BagOfHoldingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(
                CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter())
        );
        if (exileZone == null) {
            return true;
        }
        return controller.moveCards(exileZone, Zone.HAND, source, game);
    }
}
