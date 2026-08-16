package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author muz
 */
public final class TinybonesPocketNuisance extends CardImpl {

    public TinybonesPocketNuisance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Tinybones enters, each opponent discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT)));

        // Whenever a player discards one or more cards, Tinybones deals 1 damage to each opponent.
        this.addAbility(new TinybonesPocketNuisanceTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT)));
    }

    private TinybonesPocketNuisance(final TinybonesPocketNuisance card) {
        super(card);
    }

    @Override
    public TinybonesPocketNuisance copy() {
        return new TinybonesPocketNuisance(this);
    }
}

class TinybonesPocketNuisanceTriggeredAbility extends TriggeredAbilityImpl {

    TinybonesPocketNuisanceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever a player discards one or more cards, ");
    }

    private TinybonesPocketNuisanceTriggeredAbility(final TinybonesPocketNuisanceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TinybonesPocketNuisanceTriggeredAbility copy() {
        return new TinybonesPocketNuisanceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARDS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
}
