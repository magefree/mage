package mage.cards.c;

import mage.MageInt;
import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.ExtortAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class CryptGhast extends CardImpl {

    public CryptGhast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        this.addAbility(new ExtortAbility());

        // Whenever you tap a Swamp for mana, add {B} (in addition to the mana the land produces).
        this.addAbility(new CryptGhastTriggeredAbility());
    }

    private CryptGhast(final CryptGhast card) {
        super(card);
    }

    @Override
    public CryptGhast copy() {
        return new CryptGhast(this);
    }
}

class CryptGhastTriggeredAbility extends TriggeredManaAbility {

    CryptGhastTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.BlackMana(1)), false);
        this.usesStack = false;
    }

    private CryptGhastTriggeredAbility(CryptGhastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = ((TappedForManaEvent) event).getPermanent();
        return land != null && land.isControlledBy(getControllerId()) && land.hasSubtype(SubType.SWAMP, game);
    }

    @Override
    public CryptGhastTriggeredAbility copy() {
        return new CryptGhastTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a Swamp for mana, add an additional {B}.";
    }
}
