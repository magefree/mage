package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GroundchuckAndDirtbag extends CardImpl {

    public GroundchuckAndDirtbag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OX);
        this.subtype.add(SubType.MOLE);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you tap a land for mana, add {G}.
        this.addAbility(new GroundchuckAndDirtbagTriggeredAbility());
    }

    private GroundchuckAndDirtbag(final GroundchuckAndDirtbag card) {
        super(card);
    }

    @Override
    public GroundchuckAndDirtbag copy() {
        return new GroundchuckAndDirtbag(this);
    }
}

class GroundchuckAndDirtbagTriggeredAbility extends TriggeredManaAbility {

    GroundchuckAndDirtbagTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.GreenMana(1)), false);
    }

    private GroundchuckAndDirtbagTriggeredAbility(final GroundchuckAndDirtbagTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        return permanent != null && permanent.isLand(game);
    }

    @Override
    public GroundchuckAndDirtbagTriggeredAbility copy() {
        return new GroundchuckAndDirtbagTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a land for mana, add {G}.";
    }
}
