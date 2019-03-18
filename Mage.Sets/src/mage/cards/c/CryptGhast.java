package mage.cards.c;

import java.util.UUID;
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
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class CryptGhast extends CardImpl {

    public CryptGhast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        this.addAbility(new ExtortAbility());
        // Whenever you tap a Swamp for mana, add {B} (in addition to the mana the land produces).
        this.addAbility(new CryptGhastTriggeredAbility());
    }

    public CryptGhast(final CryptGhast card) {
        super(card);
    }

    @Override
    public CryptGhast copy() {
        return new CryptGhast(this);
    }
}

class CryptGhastTriggeredAbility extends TriggeredManaAbility {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Swamp");

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public CryptGhastTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.BlackMana(1)), false);
        this.usesStack = false;
    }

    public CryptGhastTriggeredAbility(CryptGhastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        return land != null && filter.match(land, this.getSourceId(), this.getControllerId(), game);
    }

    @Override
    public CryptGhastTriggeredAbility copy() {
        return new CryptGhastTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a Swamp for mana, add {B} <i>(in addition to the mana the land produces)</i>.";
    }
}
