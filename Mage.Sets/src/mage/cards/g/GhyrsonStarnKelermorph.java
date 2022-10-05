package mage.cards.g;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhyrsonStarnKelermorph extends CardImpl {

    public GhyrsonStarnKelermorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TYRANID);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Three Autostubs -- Whenever another source you control deals exactly 1 damage to a permanent or player, Ghyrson Starn, Kelermorph deals 2 damage to that permanent or player.
        this.addAbility(new GhyrsonStarnKelermorphTriggeredAbility());
    }

    private GhyrsonStarnKelermorph(final GhyrsonStarnKelermorph card) {
        super(card);
    }

    @Override
    public GhyrsonStarnKelermorph copy() {
        return new GhyrsonStarnKelermorph(this);
    }
}

class GhyrsonStarnKelermorphTriggeredAbility extends TriggeredAbilityImpl {

    GhyrsonStarnKelermorphTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2).setText("{this} deals 2 damage to that permanent or player"));
        this.withFlavorWord("Three Autostubs");
        this.setTriggerPhrase("Whenever another source you control deals exactly 1 damage to a permanent or player, ");
    }

    private GhyrsonStarnKelermorphTriggeredAbility(final GhyrsonStarnKelermorphTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GhyrsonStarnKelermorphTriggeredAbility copy() {
        return new GhyrsonStarnKelermorphTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getAmount() == 1
                && isControlledBy(game.getControllerId(event.getSourceId()))
                && event.getSourceId().equals(getSourceId())) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            return true;
        }
        return false;
    }
}
