package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class MarketGnome extends CardImpl {

    public MarketGnome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}");
        
        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Market Gnome dies, you gain 1 life and draw a card.
        Ability ability = new DiesSourceTriggeredAbility(new GainLifeEffect(1));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // When Market Gnome is exiled from the battlefield while you're activating a craft ability, you gain 1 life and draw a card.
        this.addAbility(new MarketGnomeTriggeredAbility());

    }

    private MarketGnome(final MarketGnome card) {
        super(card);
    }

    @Override
    public MarketGnome copy() {
        return new MarketGnome(this);
    }
}

class MarketGnomeTriggeredAbility extends TriggeredAbilityImpl {

    MarketGnomeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1));
        this.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        setTriggerPhrase("When {this} is exiled from the battlefield while you're activating a craft ability, ");
        // setLeavesTheBattlefieldTrigger(true); // not needed as special event is fired prior to the actual exiling
    }

    private MarketGnomeTriggeredAbility(final MarketGnomeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MarketGnomeTriggeredAbility copy() {
        return new MarketGnomeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXILED_WHILE_CRAFTING;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(getSourceId());
        return permanent != null && sourcePermanent != null && permanent.getId().equals(sourcePermanent.getId());
    }
}
