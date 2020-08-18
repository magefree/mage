package mage.cards.g;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhostlyPilferer extends CardImpl {

    public GhostlyPilferer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Ghostly Pilferer becomes untapped, you may pay {2}. If you do, draw a card.
        this.addAbility(new InspiredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(2)
        ), false, false));

        // Whenever an opponent casts a spell from anywhere other than their hand, draw a card.
        this.addAbility(new GhostlyPilfererTriggeredAbility());
        /*TODO: I think this way is the optimal way(copied from counter balance):
         * this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD,
         * new CounterbalanceEffect(), StaticFilters.FILTER_SPELL, true, SetTargetPointer.SPELL));
         */

        // Discard a card: Ghostly Pilferer can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn), new DiscardCardCost()
        ));
    }

    private GhostlyPilferer(final GhostlyPilferer card) {
        super(card);
    }

    @Override
    public GhostlyPilferer copy() {
        return new GhostlyPilferer(this);
    }
}

class GhostlyPilfererTriggeredAbility extends TriggeredAbilityImpl {

    GhostlyPilfererTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    private GhostlyPilfererTriggeredAbility(final GhostlyPilfererTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GhostlyPilfererTriggeredAbility copy() {
        return new GhostlyPilfererTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return (game.getOpponents(getControllerId()).contains(spell.getControllerId())
                && event.getZone() != Zone.HAND);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell from anywhere other than their hand, draw a card.";
    }
}
