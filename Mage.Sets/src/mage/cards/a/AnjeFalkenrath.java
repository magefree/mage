package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnjeFalkenrath extends CardImpl {

    public AnjeFalkenrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

        // Whenever you discard a card, if it has madness, untap Anje Falkenrath.
        this.addAbility(new AnjeFalkenrathTriggeredAbility());
    }

    private AnjeFalkenrath(final AnjeFalkenrath card) {
        super(card);
    }

    @Override
    public AnjeFalkenrath copy() {
        return new AnjeFalkenrath(this);
    }
}

class AnjeFalkenrathTriggeredAbility extends TriggeredAbilityImpl {

    AnjeFalkenrathTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UntapSourceEffect(), false);
    }

    private AnjeFalkenrathTriggeredAbility(final AnjeFalkenrathTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AnjeFalkenrathTriggeredAbility copy() {
        return new AnjeFalkenrathTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Card card = game.getCard(event.getTargetId());
        if (card == null) {
            return false;
        }
        return card.getAbilities(game).stream().anyMatch(ability -> ability instanceof MadnessAbility);
    }

    @Override
    public String getRule() {
        return "Whenever you discard a card, if it has madness, untap {this}.";
    }
}
