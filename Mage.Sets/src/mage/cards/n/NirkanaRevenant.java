package mage.cards.n;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NirkanaRevenant extends CardImpl {

    public NirkanaRevenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHADE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you tap a Swamp for mana, add {B}.
        this.addAbility(new NirkanaRevenantTriggeredAbility());

        // {B}: Nirkana Revenant gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{B}")
        ));
    }

    private NirkanaRevenant(final NirkanaRevenant card) {
        super(card);
    }

    @Override
    public NirkanaRevenant copy() {
        return new NirkanaRevenant(this);
    }
}

class NirkanaRevenantTriggeredAbility extends TriggeredManaAbility {

    NirkanaRevenantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.BlackMana(1)), false);
    }

    private NirkanaRevenantTriggeredAbility(final NirkanaRevenantTriggeredAbility ability) {
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
        return permanent != null && permanent.hasSubtype(SubType.SWAMP, game);
    }

    @Override
    public NirkanaRevenantTriggeredAbility copy() {
        return new NirkanaRevenantTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a Swamp for mana, add an additional {B}.";
    }
}
