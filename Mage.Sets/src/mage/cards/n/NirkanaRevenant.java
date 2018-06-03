
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
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
 * @author jeffwadsworth
 */
public final class NirkanaRevenant extends CardImpl {

    public NirkanaRevenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHADE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you tap a Swamp for mana, add {B}.
        this.addAbility(new NirkanaRevenantTriggeredAbility());

        // {B}: Nirkana Revenant gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl("{B}")));
    }

    public NirkanaRevenant(final NirkanaRevenant card) {
        super(card);
    }

    @Override
    public NirkanaRevenant copy() {
        return new NirkanaRevenant(this);
    }
}

class NirkanaRevenantTriggeredAbility extends TriggeredManaAbility {
    
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Swamp");
    static {
            filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public NirkanaRevenantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.BlackMana(1)), false);
        this.usesStack = false;
    }

    public NirkanaRevenantTriggeredAbility(NirkanaRevenantTriggeredAbility ability) {
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
    public NirkanaRevenantTriggeredAbility copy() {
        return new NirkanaRevenantTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a Swamp for mana, add {B}.";
    }
}
