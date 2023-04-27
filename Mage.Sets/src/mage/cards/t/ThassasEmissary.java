
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class ThassasEmissary extends CardImpl {

    public ThassasEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.CRAB);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bestow {5}{U} (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        this.addAbility(new BestowAbility(this, "{5}{U}"));
        // Whenever Thassa's Emissary or enchanted creature deals combat damage to a player, draw a card.
        this.addAbility(new ThassasEmissaryTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
        // Enchanted creature gets +3/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3,3)));
    }

    private ThassasEmissary(final ThassasEmissary card) {
        super(card);
    }

    @Override
    public ThassasEmissary copy() {
        return new ThassasEmissary(this);
    }
}

class ThassasEmissaryTriggeredAbility extends TriggeredAbilityImpl {

    public ThassasEmissaryTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever {this} or enchanted creature deals combat damage to a player, ");
    }

    public ThassasEmissaryTriggeredAbility(final ThassasEmissaryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThassasEmissaryTriggeredAbility copy() {
        return new ThassasEmissaryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedEvent) event).isCombatDamage()) {
            if (event.getSourceId().equals(this.sourceId)) {
                return true;
            }
            Permanent p = game.getPermanent(event.getSourceId());
            if (p != null && p.getAttachments().contains(this.getSourceId())) {
                return true;
            }
        }
        return false;
    }
}
