package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.TreasureToken;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePermanentEvent;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;

/**
 *
 * @author muz
 */
public final class SmaugTheImpenetrable extends CardImpl {

    public SmaugTheImpenetrable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(8);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Smaug is dealt noncombat damage, create that many Treasure tokens.
        this.addAbility(new SmaugTheImpenetrableTriggeredAbility());
    }

    private SmaugTheImpenetrable(final SmaugTheImpenetrable card) {
        super(card);
    }

    @Override
    public SmaugTheImpenetrable copy() {
        return new SmaugTheImpenetrable(this);
    }
}

class SmaugTheImpenetrableTriggeredAbility extends DealtDamageToSourceTriggeredAbility {

    public SmaugTheImpenetrableTriggeredAbility() {
        super(
            new CreateTokenEffect(new TreasureToken(), SavedDamageValue.MANY)
                .setText("create that many Treasure tokens"),
            false
        );
        setTriggerPhrase("Whenever {this} is dealt noncombat damage, ");
    }

    private SmaugTheImpenetrableTriggeredAbility(final SmaugTheImpenetrableTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SmaugTheImpenetrableTriggeredAbility copy() {
        return new SmaugTheImpenetrableTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!getSourceId().equals(event.getTargetId())) {
            return false;
        }

        int nonCombatDamage = ((DamagedBatchForOnePermanentEvent) event).getEvents()
                .stream()
                .filter(damageEvent -> !damageEvent.isCombatDamage())
                .mapToInt(DamagedPermanentEvent::getAmount)
                .sum();

        if (nonCombatDamage < 1) {
            return false;
        }

        this.getEffects().setValue("damage", nonCombatDamage);
        return true;
    }
}
