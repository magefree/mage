
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author spjspj
 */
public final class IkraShidiqiTheUsurper extends CardImpl {

    public IkraShidiqiTheUsurper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(7);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever a creature you control deals combat damage to a player, you gain life equal to that creature's toughness.
        this.addAbility(new IkraShidiqiTheUsurperTriggeredAbility());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private IkraShidiqiTheUsurper(final IkraShidiqiTheUsurper card) {
        super(card);
    }

    @Override
    public IkraShidiqiTheUsurper copy() {
        return new IkraShidiqiTheUsurper(this);
    }
}

class IkraShidiqiTheUsurperTriggeredAbility extends TriggeredAbilityImpl {

    public IkraShidiqiTheUsurperTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public IkraShidiqiTheUsurperTriggeredAbility(final IkraShidiqiTheUsurperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IkraShidiqiTheUsurperTriggeredAbility copy() {
        return new IkraShidiqiTheUsurperTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent damageEvent = (DamagedEvent) event;
        if (damageEvent.isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.isCreature(game) && permanent.isControlledBy(this.getControllerId())) {
                this.getEffects().clear();
                this.getEffects().add(new GainLifeEffect(permanent.getToughness().getValue()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control deals combat damage to a player, you gain life equal to that creature's toughness.";
    }
}
