package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class WallOfSouls extends CardImpl {

    public WallOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Wall of Souls is dealt combat damage, it deals that much damage to target opponent or planeswalker.
        Ability ability = new WallOfSoulsTriggeredAbility();
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private WallOfSouls(final WallOfSouls card) {
        super(card);
    }

    @Override
    public WallOfSouls copy() {
        return new WallOfSouls(this);
    }
}

class WallOfSoulsTriggeredAbility extends TriggeredAbilityImpl {

    public WallOfSoulsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(SavedDamageValue.MUCH, "it"));
        setTriggerPhrase("Whenever {this} is dealt combat damage, ");
    }

    public WallOfSoulsTriggeredAbility(final WallOfSoulsTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public WallOfSoulsTriggeredAbility copy() {
        return new WallOfSoulsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId) && ((DamagedEvent) event).isCombatDamage()) {
            this.getEffects().setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }
}
